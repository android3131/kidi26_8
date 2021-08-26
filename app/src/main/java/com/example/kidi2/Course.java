package com.example.myapplication19;

import java.util.ArrayList;

import retrofit2.http.Field;

public class Course {

    //@Id
    //private String ID;

    private String name;
    private int price;
    private String startDateTime;
    private String finishDateTime;
    private Category category;
    private ArrayList<String> leadersIDs;
    private String zoomMeetingLink;
    private ArrayList<String> kidsIDs;
    private  String day;
    private  String startOclock;
    private  String endOclock;
    private  int clr;


    public Course(String name, int price, String startDateTime, String finishDateTime, Category category,  String zoomMeetingLink,
                   String day, String startOclock, String endOclock,int clr)
    {
        this.name = name;
        this.price = price;
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
        this.category = category;
        this.leadersIDs = leadersIDs;
        this.zoomMeetingLink = zoomMeetingLink;
        this.kidsIDs = kidsIDs;
        this.day = day;
        this.startOclock = startOclock;
        this.endOclock = endOclock;
        this.clr=clr;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getZoomMeetingLink() {
        return zoomMeetingLink;
    }

    public void setZoomMeetingLink(String zoomMeetingLink) {
        this.zoomMeetingLink = zoomMeetingLink;
    }

    public int getClr() {
        return clr;
    }

    public void setClr(int clr) {
        this.clr = clr;
    }

    public String getName() {
        return name;
    }

    public String getDay() {
        return day;
    }

    public String getStartOclock() {
        return startOclock;
    }

    public void setStartOclock(String startOclock) {
        this.startOclock = startOclock;
    }

    public String getEndOclock() {
        return endOclock;
    }

    public void setEndOclock(String endOclock) {
        this.endOclock = endOclock;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getFinishDateTime() {
        return finishDateTime;
    }

    public void setFinishDateTime(String finishDateTime) {
        this.finishDateTime = finishDateTime;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


}
