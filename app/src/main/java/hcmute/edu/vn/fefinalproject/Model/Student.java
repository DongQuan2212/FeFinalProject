package hcmute.edu.vn.fefinalproject.Model;

import java.util.Date;
import java.util.List;

public class Student extends User {
    private String studentCode;
    private Date studyTime;
    private String major;

    // Constructor
    public Student(String userID, String fullName, String email, String password, EUserRole role,
                   String imageUrl, Date birthDate, Boolean isActive, Date createdAt, Date updatedAt,
                   String studentCode, Date studyTime, String major) {
        super(userID, fullName, email, password, role, imageUrl, birthDate, isActive, createdAt, updatedAt);
        this.studentCode = studentCode;
        this.studyTime = studyTime;
        this.major = major;
    }

    // Getters and Setters
    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }

    public Date getStudyTime() { return studyTime; }
    public void setStudyTime(Date studyTime) { this.studyTime = studyTime; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }


}