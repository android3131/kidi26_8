package com.example.kidi2;

import java.util.Objects;

public class Course_Addactivity {
    private int courseId;
    private String courseName;
    private String day;
    private String startTime;
    private String duration;


    public Course_Addactivity() {

    }

    public Course_Addactivity(int courseId, String courseName, String day, String startTime, String duration) {
        super();
        this.courseId = courseId;
        this.courseName = courseName;
        this.day = day;
        this.startTime = startTime;
        this.duration = duration;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Course_Addactivity other = (Course_Addactivity) obj;
        return courseId == other.courseId;
    }

}
