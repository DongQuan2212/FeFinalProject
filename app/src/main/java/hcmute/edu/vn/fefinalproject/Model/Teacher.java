package hcmute.edu.vn.fefinalproject.Model;

import java.util.Date;
import java.util.List;

public class Teacher extends User {
    private String degree;
    private List<Classroom> teachingClasses;

    // Constructor
    public Teacher(String userID, String fullName, String email, String password, EUserRole role,
                   String imageUrl, Date birthDate, Boolean isActive, Date createdAt, Date updatedAt,
                   String degree, List<Classroom> teachingClasses) {
        super(userID, fullName, email, password, role, imageUrl, birthDate, isActive, createdAt, updatedAt);
        this.degree = degree;
        this.teachingClasses = teachingClasses;
    }

    // Getters and Setters
    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }

    public List<Classroom> getTeachingClasses() { return teachingClasses; }
    public void setTeachingClasses(List<Classroom> teachingClasses) { this.teachingClasses = teachingClasses; }
}
