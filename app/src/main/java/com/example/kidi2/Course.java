package com.example.kidi2;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class Course {


    private String ID;


    private String name;


    private String description;
    private int price;


    private Date startDateTime;


    private Date finishDateTime;

    private String categoryId;


    private Status status;

    private ArrayList<String> leadersIDs= new ArrayList<String>();;


    private String zoomMeetingLink;


    private ArrayList<String> kidsIDs= new ArrayList<String>();;

    private Day day;

    private double meetingDuration;

    public Course() {
        super();
    }

    public Course(String name, Date startDateTime,Date finishDateTime, Day day, String categoryId) {
        super();
        this.categoryId = categoryId;
        this.name = name;
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
        this.day = day;
    }

    public Course( String name, Date startDateTime, String categoryId,
                   ArrayList<String> kidsIDs) {
        super();
        this.name = name;
        this.startDateTime = startDateTime;
        this.categoryId = categoryId;
        this.status = Status.Active;
        this.kidsIDs = kidsIDs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public double getMeetingDuration() {
        return meetingDuration;
    }

    public void setMeetingDuration(double meetingDuration) {
        this.meetingDuration = meetingDuration;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        this.ID = iD;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, categoryId, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Course other = (Course) obj;
        return Objects.equals(ID, other.ID) && Objects.equals(categoryId, other.categoryId)
                && Objects.equals(name, other.name);
    }




}