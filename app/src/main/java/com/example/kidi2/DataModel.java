package com.example.kidi2;

import androidx.annotation.NonNull;

public class DataModel {

    // string variables for our car
    private String courseName;
    private String time;
    private String day;
    private String length;

    public DataModel(){

    }

    public DataModel( String courseName,
                      String time,
                      String day,
                      String length
    ) {
        this.courseName = courseName;
        this.time = time;
        this.day = day;
        this.length = length;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    @NonNull
    @Override
    public String toString() {
        return courseName+"\n"+day+", "+time+"+"+length;
    }
}