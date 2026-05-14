package com.example.schoolschedule.models;

public class Schedule {
    private int id;
    private String className;
    private String teacherName;
    private String subjectName;
    private String day;
    private String startTime;
    private String endTime;
    private String location;

    public Schedule() {}

    public Schedule(int id, String className, String teacherName, String subjectName, 
                   String day, String startTime, String endTime, String location) {
        this.id = id;
        this.className = className;
        this.teacherName = teacherName;
        this.subjectName = subjectName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
