package com.example.myapplication19;

import java.util.ArrayList;
import java.util.Date;

public class Course1 {

    //@Id
    private String ID;

    private String name;
    private int price;
    private Date startDateTime;
    private Date finishDateTime;
    private Category category;
    private ArrayList<String> leadersIDs;
    private String zoomMeetingLink;
    private ArrayList<String> kidsIDs;
    private  String day;
    private  String startOclock;
    private  String endOclock;
    private  int clr;

    public Course1(String ID, String name, int price, Date startDateTime, Date finishDateTime, Category category,  String zoomMeetingLink,
                  String day, String startOclock, String endOclock,int clr)
    {
        this.ID = ID;
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

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getFinishDateTime() {
        return finishDateTime;
    }

    public void setFinishDateTime(Date finishDateTime) {
        this.finishDateTime = finishDateTime;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCourseName() {
        // TODO Auto-generated method stub

        return name;
    }

    public String getId() {
        return ID;
    }


}