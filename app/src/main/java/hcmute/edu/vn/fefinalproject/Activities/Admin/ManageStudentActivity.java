package hcmute.edu.vn.fefinalproject.Activities.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import hcmute.edu.vn.fefinalproject.Adapter.StudentAdapter;
import hcmute.edu.vn.fefinalproject.R;
import hcmute.edu.vn.fefinalproject.Model.EUserRole;
import hcmute.edu.vn.fefinalproject.Model.Student;

public class ManageStudentActivity extends AppCompatActivity {

    private ImageView fabAddStudent, btnBack;
    private RecyclerView rvStudents;
    private TextView tvStudentCount;
    private EditText etSearch;
    private StudentAdapter studentAdapter;
    private List<Student> studentList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_student);

        // Khởi tạo Firestore
        db = FirebaseFirestore.getInstance();

        // Ánh xạ các view từ layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        fabAddStudent = findViewById(R.id.fabAddStudent);
        btnBack = findViewById(R.id.btn_Back);
        rvStudents = findViewById(R.id.rvStudents);
        tvStudentCount = findViewById(R.id.tvStudentCount);
        etSearch = findViewById(R.id.etSearch);

        // Thiết lập Toolbar
        setSupportActionBar(toolbar);

        // Khởi tạo danh sách sinh viên và adapter
        studentList = new ArrayList<>();
        studentAdapter = new StudentAdapter(studentList);
        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        rvStudents.setAdapter(studentAdapter);

        // Tải danh sách sinh viên từ Firestore
        loadStudents();

        // Xử lý nút Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Xử lý sự kiện khi nhấn nút Thêm Sinh Viên
        fabAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageStudentActivity.this, AddStudentActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý tìm kiếm
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                studentAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // Tải danh sách sinh viên từ Firestore
    private void loadStudents() {
        Log.d("ManageStudentActivity", "Bắt đầu tải danh sách sinh viên...");
        db.collection("students")
                .whereEqualTo("role", EUserRole.STUDENT.toString())
                .addSnapshotListener((snapshot, e) -> {
                    if (e != null) {
                        Log.e("ManageStudentActivity", "Lỗi khi lắng nghe dữ liệu: " + e.getMessage());
                        Toast.makeText(ManageStudentActivity.this, "Lỗi khi tải danh sách: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        tvStudentCount.setText("(0)");
                        return;
                    }

                    if (snapshot != null) {
                        // Tạo danh sách tạm thời
                        List<Student> tempList = new ArrayList<>();
                        Log.d("ManageStudentActivity", "Tải dữ liệu thành công, số lượng tài liệu: " + snapshot.size());
                        for (QueryDocumentSnapshot document : snapshot) {
                            Map<String, Object> data = document.getData();
                            Log.d("ManageStudentActivity", "Dữ liệu sinh viên: " + data.toString());

                            // Chuyển đổi Timestamp thành Date
                            Date birthDate = data.get("birthDate") instanceof Timestamp ?
                                    ((Timestamp) data.get("birthDate")).toDate() : null;
                            Date createdAt = data.get("createdAt") instanceof Timestamp ?
                                    ((Timestamp) data.get("createdAt")).toDate() : null;
                            Date updatedAt = data.get("updatedAt") instanceof Timestamp ?
                                    ((Timestamp) data.get("updatedAt")).toDate() : null;
                            Date studyTime = data.get("studyTime") instanceof Timestamp ?
                                    ((Timestamp) data.get("studyTime")).toDate() : null;

                            try {
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
                                        studyTime,
                                        (String) data.get("major")
                                );
                                tempList.add(student);
                                Log.d("ManageStudentActivity", "Đã thêm sinh viên vào danh sách tạm: " + student.getFullName());
                            } catch (Exception ex) {
                                Log.e("ManageStudentActivity", "Lỗi khi ánh xạ sinh viên: " + ex.getMessage());
                            }
                        }

                        // Log danh sách tạm để kiểm tra
                        Log.d("ManageStudentActivity", "Danh sách tạm có " + tempList.size() + " sinh viên");
                        for (Student student : tempList) {
                            Log.d("ManageStudentActivity", "Sinh viên trong danh sách tạm: " + student.getFullName());
                        }

                        // Cập nhật studentList và adapter
                        studentList = new ArrayList<>(tempList); // Tạo bản sao mới
                        studentAdapter.updateList(studentList);
                        tvStudentCount.setText("(" + studentList.size() + ")");
                        Log.d("ManageStudentActivity", "Cập nhật danh sách hoàn tất, số sinh viên: " + studentList.size());
                    } else {
                        Log.e("ManageStudentActivity", "Snapshot null");
                        tvStudentCount.setText("(0)");
                    }
                });
    }
}