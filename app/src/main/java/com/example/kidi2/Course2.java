package com.example.kidi2;

import java.util.ArrayList;

public class Course2 {

    //@Id
    //private String ID;

    private String name;
    private double price;
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
    private String urlLink;

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public Course2(String name, double price, String startDateTime, String finishDateTime, Category category, String zoomMeetingLink,
                   String day, String startOclock, String endOclock, int clr, String urlLink)
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
        this.urlLink = urlLink;
    }


    public Course2(Course2 c)
    {

        this.name = c.getName();
        this.price = c.getPrice();
        this.startDateTime = c.getStartDateTime();
        this.finishDateTime = c.getFinishDateTime();
        this.category = c.getCategory();
        this.leadersIDs = c.getLeadersIDs();
        this.zoomMeetingLink = c.getZoomMeetingLink();
        this.kidsIDs = c.getKidsIDs();
        this.day = c.getDay();
        this.startOclock = c.getStartOclock();
        this.endOclock = c.getEndOclock();
        this.clr=c.getClr();
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
        if(Integer.parseInt(endOclock) > Integer.parseInt(startOclock))
            this.endOclock = endOclock;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setName(String name) {
        if(name.matches("(?=^.{2,40}$)^[a-zA-Z]"))
            this.name=name;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<String> getLeadersIDs() {
        return leadersIDs;
    }

    public void setLeadersIDs(ArrayList<String> leadersIDs) {
        this.leadersIDs = leadersIDs;
    }

    public String getZoomMeetingLink() {
        return zoomMeetingLink;
    }

    public void setZoomMeetingLink(String zoomMeetingLink) {
        this.zoomMeetingLink = zoomMeetingLink;
    }

    public ArrayList<String> getKidsIDs() {
        return kidsIDs;
    }

    public void setKidsIDs(ArrayList<String> kidsIDs) {
        this.kidsIDs = kidsIDs;
    }
}