package hcmute.edu.vn.fefinalproject.Activities.User;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.fefinalproject.Adapter.NotificationAdapter;
import hcmute.edu.vn.fefinalproject.Model.Notification;
import hcmute.edu.vn.fefinalproject.R;
import hcmute.edu.vn.fefinalproject.Adapter.SubjectAdapter;
import hcmute.edu.vn.fefinalproject.Model.Subject;


public class MainActivity extends AppCompatActivity {
    private RecyclerView ListSubject, ListNotification;
    private hcmute.edu.vn.fefinalproject.Adapter.SubjectAdapter SubjectAdapter ;

    private hcmute.edu.vn.fefinalproject.Adapter.NotificationAdapter NotificationAdapter;

    Animation  botAnim,topAnim;
    private ConstraintLayout btnMenu, btnProfile,btnSetting,btnNotification,constraintNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //ania
        botAnim = AnimationUtils.loadAnimation(this, R.anim.anim_up_btn);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.anim_down_btn);

        // anh xa
        btnMenu = findViewById(R.id.constraint_ic_menu);
        btnProfile = findViewById(R.id.constraint_ic_profile);
        btnSetting = findViewById(R.id.constraint_ic_setting);
        btnNotification = findViewById(R.id.constraint_ic_notification);
        constraintNotification = findViewById(R.id.constrain_notification);
        // Anh xa list subject
        ListSubject = findViewById(R.id.list_subject);
        ListSubject.setLayoutManager(new GridLayoutManager(this, 1));
        SubjectAdapter = new SubjectAdapter(getDummySubjects(), subject -> {
            Intent intent = new Intent(MainActivity.this, SubjectActivity.class);
            intent.putExtra("notification_id", subject.getId());
            intent.putExtra("name_subject", subject.getName());
            startActivity(intent);
        });
        ListSubject.setAdapter(SubjectAdapter);
        // Anh xa list  notification
        ListNotification = findViewById(R.id.list_notification);
        ListNotification.setLayoutManager(new GridLayoutManager(this, 1));


        NotificationAdapter = new NotificationAdapter(getDummyNotification(), notification -> {
            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
            intent.putExtra("notification_id", notification.getId());
            intent.putExtra("notification_title", notification.getTitle());
            intent.putExtra("notification_content", notification.getContent());
            startActivity(intent);
        });
        ListNotification.setAdapter(NotificationAdapter);





        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnProfile.getVisibility() == View.GONE && btnSetting.getVisibility() == View.GONE && btnNotification.getVisibility() == View.GONE) {
                    btnProfile.setVisibility(View.VISIBLE);
                    btnSetting.setVisibility(View.VISIBLE);
                    btnNotification.setVisibility(View.VISIBLE);

                    btnProfile.startAnimation(botAnim);
                    btnSetting.startAnimation(botAnim);
                    btnNotification.startAnimation(botAnim);
                } else {
                    btnProfile.setVisibility(View.GONE);
                    btnSetting.setVisibility(View.GONE);
                    btnNotification.setVisibility(View.GONE);
                    constraintNotification.setVisibility(View.GONE);

                }
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (constraintNotification.getVisibility() == View.GONE ){
                    constraintNotification.setVisibility(View.VISIBLE);
                    constraintNotification.startAnimation(botAnim);
                } else {
                    constraintNotification.setVisibility(View.GONE);
                }

            }
        });

    }
    private List<Subject> getDummySubjects() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject(1,"Cong nghe Phan mem HDT_ Nhom 01CLC", "Co Tho", "Thứ 2, Tiết 1-2"));
        subjects.add(new Subject(2,"Lap trinh di dong_ Nhom 04CLC", "Trần Thị B", "Thứ 2, Tiết 3-4"));
        subjects.add(new Subject(3,"Mau thiet ke phan mem_ Nhom 01CLC", "Lê Văn C", "Thứ 3, Tiết 1-2"));
        return subjects;
    }
    private List<Notification> getDummyNotification() {
        List<Notification> notificationItems = new ArrayList<>();
        notificationItems.add(new Notification(1, "V/v nghỉ học môn Mẫu TKPM sáng 26/03", "Chào các em,\n" +
                "\n" +
                "Hôm nay thầy không khỏe nên lớp nghỉ nha. Các em nhắn lại cho những bạn cùng nhóm mình giúp thầy.\n" +
                "\n" +
                "Thầy sẽ cấu hình các nội dung cần thực hiện của tuần này trên UTEXLMS, các em theo dõi và thực hiện.\n" +
                "\n" +
                "Tuần sau lớp học lại bình thường.\n" +
                "\n" +
                "Trân trọng,\n" +
                "\n" +
                "Thi Văn."));
        notificationItems.add(new Notification(2, "Yêu cầu bằng cấp", "Anh yeu em qua bae"));
        notificationItems.add(new Notification(3, "Xác thưc đrl", "Anh yeu em nhieu"));
        return notificationItems;
    }




}