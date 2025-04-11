package hcmute.edu.vn.fefinalproject.Activities.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import hcmute.edu.vn.fefinalproject.R;

public class AdminActivity extends AppCompatActivity {

    ConstraintLayout btnStudent,btnTeacher,btnClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);



        btnStudent = findViewById(R.id.btn_student);
        btnTeacher = findViewById(R.id.btn_teacher);
        btnClass = findViewById(R.id.btn_class);
        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ManageStudentActivity.class);
                startActivity(intent);
            }
        });
        btnTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ManageTeacherActivity.class);
                startActivity(intent);
            }
        });
        btnClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ManageClassActivity.class);
                startActivity(intent);
            }
        });

    }
}