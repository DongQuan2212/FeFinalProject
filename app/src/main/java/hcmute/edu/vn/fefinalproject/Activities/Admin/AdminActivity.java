package hcmute.edu.vn.fefinalproject.Activities.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import hcmute.edu.vn.fefinalproject.Activities.LoginActivity;
import hcmute.edu.vn.fefinalproject.R;

public class AdminActivity extends AppCompatActivity {

    private TextView tvStudentCount, tvTeacherCount, tvSubjectCount, tvUserName;
    private ConstraintLayout btnStudent, btnTeacher, btnClass; // Thay LinearLayout thành ConstraintLayout
    private CardView btnLogout;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Khởi tạo Firebase
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Ánh xạ các view
        tvStudentCount = findViewById(R.id.tvStudentCount);
        tvTeacherCount = findViewById(R.id.tvTeacherCount);
        tvSubjectCount = findViewById(R.id.tvSubjectCount);
        tvUserName = findViewById(R.id.tvUserName);
        btnStudent = findViewById(R.id.btn_student);
        btnTeacher = findViewById(R.id.btn_teacher);
        btnClass = findViewById(R.id.btn_class);
        btnLogout = findViewById(R.id.btnLogout);

        // Hiển thị tên người dùng
        if (auth.getCurrentUser() != null) {
            String email = auth.getCurrentUser().getEmail();
            tvUserName.setText(email != null ? email.split("@")[0] : "Admin");
        } else {
            tvUserName.setText("Admin");
        }

        // Tải dữ liệu tổng số tài khoản
        loadCounts();

        // Xử lý sự kiện nhấn vào các nút
        btnStudent.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, ManageStudentActivity.class);
            startActivity(intent);
        });

        btnTeacher.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, ManageTeacherActivity.class);
            startActivity(intent);
        });

        btnClass.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, ManageSubjectActivity.class);
            startActivity(intent);
        });

        // Xử lý sự kiện nhấn nút Logout
        btnLogout.setOnClickListener(v -> logout());
    }

    private void loadCounts() {
        // Tải tổng số sinh viên
        db.collection("students")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot snapshot = task.getResult();
                        tvStudentCount.setText(String.valueOf(snapshot.size()));
                    } else {
                        Toast.makeText(AdminActivity.this, "Lỗi khi tải số sinh viên: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        // Tải tổng số giảng viên
        db.collection("teachers")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot snapshot = task.getResult();
                        tvTeacherCount.setText(String.valueOf(snapshot.size()));
                    } else {
                        Toast.makeText(AdminActivity.this, "Lỗi khi tải số giảng viên: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        // Tải tổng số lớp học
        db.collection("subjects")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot snapshot = task.getResult();
                        tvSubjectCount.setText(String.valueOf(snapshot.size()));
                    } else {
                        Toast.makeText(AdminActivity.this, "Lỗi khi tải số lớp học: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void logout() {
        auth.signOut();
        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa toàn bộ activity stack
        startActivity(intent);
        finish();
        Toast.makeText(this, "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
    }



}
