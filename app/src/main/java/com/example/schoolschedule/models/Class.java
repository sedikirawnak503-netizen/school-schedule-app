package com.example.schoolschedule.models;

public class Class {
    private int id;
    private String name;
    private String level;
    private int studentCount;

    public Class() {}

    public Class(int id, String name, String level, int studentCount) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.studentCount = studentCount;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public int getStudentCount() { return studentCount; }
    public void setStudentCount(int studentCount) { this.studentCount = studentCount; }
}
