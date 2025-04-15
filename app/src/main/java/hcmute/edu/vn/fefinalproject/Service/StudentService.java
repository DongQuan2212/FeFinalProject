package hcmute.edu.vn.fefinalproject.Service;

import androidx.annotation.NonNull;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import hcmute.edu.vn.fefinalproject.Model.EUserRole;
import hcmute.edu.vn.fefinalproject.Model.Student;
import hcmute.edu.vn.fefinalproject.Model.StudyMaterial;
import hcmute.edu.vn.fefinalproject.Model.Subject;

public class StudentService {

    private final FirebaseFirestore db;

    public StudentService() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void loadStudents(@NonNull Consumer<List<Student>> onSuccess, @NonNull Consumer<String> onFailure) {
        List<Student> studentList = new ArrayList<>();
        db.collection("students")
                .whereEqualTo("role", EUserRole.STUDENT.toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> data = document.getData();
                            Date birthDate = data.get("birthDate") instanceof com.google.firebase.Timestamp ?
                                    ((com.google.firebase.Timestamp) data.get("birthDate")).toDate() : null;
                            Date createdAt = data.get("createdAt") instanceof com.google.firebase.Timestamp ?
                                    ((com.google.firebase.Timestamp) data.get("createdAt")).toDate() : null;
                            Date updatedAt = data.get("updatedAt") instanceof com.google.firebase.Timestamp ?
                                    ((com.google.firebase.Timestamp) data.get("updatedAt")).toDate() : null;
                            Date enrollmentDate = data.get("enrollmentDate") instanceof com.google.firebase.Timestamp ?
                                    ((com.google.firebase.Timestamp) data.get("enrollmentDate")).toDate() : null;

                            // Chuyển đổi joinedClasses từ List<Map> sang List<Subject>
                            List<Map<String, Object>> joinedClassesRaw = (List<Map<String, Object>>) data.get("joinedClasses");
                            List<Subject> joinedClasses = new ArrayList<>();
                            if (joinedClassesRaw != null) {
                                for (Map<String, Object> classData : joinedClassesRaw) {
                                    Subject subject = new Subject(
                                            (String) classData.get("classId"),
                                            (String) classData.get("className"),
                                            (String) classData.get("description"),
                                            (List<StudyMaterial>) classData.get("materials")
                                    );
                                    joinedClasses.add(subject);
                                }
                            }

                            Student student = new Student(
                                    (String) data.get("userID"),
                                    (String) data.get("fullName"),
                                    (String) data.get("email"),
                                    (String) data.get("password"),
                                    EUserRole.valueOf((String) data.get("role")),
                                    (String) data.get("imageUrl"),
                                    birthDate,
                                    (Boolean) data.get("isActive"),
                                    createdAt,
                                    updatedAt,
                                    (String) data.get("studentCode"),
                                    (String) data.get("major"),
                                    joinedClasses
                            );
                            studentList.add(student);
                        }
                        onSuccess.accept(studentList);
                    } else {
                        onFailure.accept("Lỗi khi tải danh sách sinh viên: " + task.getException().getMessage());
                    }
                });
    }

    public void deleteStudent(String studentId, @NonNull Consumer<Void> onSuccess, @NonNull Consumer<String> onFailure) {
        db.collection("students").document(studentId)
                .get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        Student student = document.toObject(Student.class);
                        if (student != null && student.getJoinedClasses() != null) {
                            // Xóa tham chiếu đến sinh viên trong các môn học
                            for (Subject subject : student.getJoinedClasses()) {
                                db.collection("subjects").document(subject.getClassId())
                                        .collection("students")
                                        .document(studentId)
                                        .delete();
                            }
                        }
                        // Xóa sinh viên
                        db.collection("students").document(studentId)
                                .delete()
                                .addOnSuccessListener(aVoid -> onSuccess.accept(null))
                                .addOnFailureListener(e -> onFailure.accept("Xóa sinh viên thất bại: " + e.getMessage()));
                    } else {
                        onFailure.accept("Sinh viên không tồn tại");
                    }
                })
                .addOnFailureListener(e -> onFailure.accept("Lỗi khi kiểm tra sinh viên: " + e.getMessage()));
    }
}
