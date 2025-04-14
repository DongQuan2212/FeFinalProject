package hcmute.edu.vn.fefinalproject.Model;

import java.io.Serializable;

public class Subject implements Serializable {
    private int id;
    private String name;
    private String teacher;
    private String schedule;

    public Subject(int id, String name, String teacher, String schedule) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.schedule = schedule;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setId(int id) {
        this.id = id;
    }
}
