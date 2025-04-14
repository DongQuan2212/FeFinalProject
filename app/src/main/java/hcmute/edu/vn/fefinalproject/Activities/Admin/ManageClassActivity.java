package hcmute.edu.vn.fefinalproject.Activities.Admin;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.vn.fefinalproject.R;

public class ManageClassActivity extends AppCompatActivity {

    ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_class);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btnBack = findViewById(R.id.btn_Back);

        btnBack.setOnClickListener(v -> finish());
    }
}