package hcmute.edu.vn.fefinalproject.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.fefinalproject.R;
import hcmute.edu.vn.fefinalproject.Model.Student;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> studentList;
    private List<Student> studentListFull;

    public StudentAdapter(List<Student> studentList) {
        this.studentList = new ArrayList<>(studentList); // Tạo bản sao
        this.studentListFull = new ArrayList<>(studentList);
        Log.d("StudentAdapter", "Khởi tạo adapter với " + this.studentList.size() + " sinh viên");
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.tvStudentName.setText(student.getFullName());
        holder.tvStudentCode.setText("Mã SV: " + student.getStudentCode());
        holder.tvStudentEmail.setText("Email: " + student.getEmail());
        Log.d("StudentAdapter", "Hiển thị sinh viên: " + student.getFullName() + " tại vị trí " + position);
    }

    @Override
    public int getItemCount() {
        Log.d("StudentAdapter", "Số lượng sinh viên hiển thị: " + studentList.size());
        return studentList.size();
    }

    public void filter(String text) {
        studentList.clear();
        if (text.isEmpty()) {
            studentList.addAll(studentListFull);
        } else {
            text = text.toLowerCase();
            for (Student student : studentListFull) {
                if (student.getFullName().toLowerCase().contains(text) ||
                        student.getStudentCode().toLowerCase().contains(text) ||
                        student.getEmail().toLowerCase().contains(text)) {
                    studentList.add(student);
                }
            }
        }
        notifyDataSetChanged();
        Log.d("StudentAdapter", "Lọc danh sách, số lượng sau lọc: " + studentList.size());
    }

    public void updateList(List<Student> newList) {
        studentList = new ArrayList<>(newList); // Tạo bản sao mới
        studentListFull = new ArrayList<>(newList);
        notifyDataSetChanged();
        Log.d("StudentAdapter", "Cập nhật danh sách, số lượng: " + studentList.size());
        for (Student student : studentList) {
            Log.d("StudentAdapter", "Sinh viên trong danh sách: " + student.getFullName());
        }
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName, tvStudentCode, tvStudentEmail;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            tvStudentCode = itemView.findViewById(R.id.tvStudentCode);
            tvStudentEmail = itemView.findViewById(R.id.tvStudentEmail);
        }
    }
}