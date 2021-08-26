package com.example.kidi2;

import android.icu.util.ULocale;
import android.provider.VoicemailContract;

import java.util.ArrayList;
import java.util.Date;

public class DataModelCourse {
    private String ID;

    private String name;


    private String description;


    private int price;


    private Date startDateTime;


    private Date finishDateTime;


    private ULocale.Category category;

    private VoicemailContract.Status status;


    private ArrayList<String> leadersIDs;


    private String zoomMeetingLink;


    private ArrayList<String> kidsIDs;


    private Day day;

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public DataModelCourse() {
        super();
    }

    public DataModelCourse(String name, int price, Date startDateTime, Date finishDateTime, ULocale.Category category, String zoomMeetingLink) {
        super();
        this.name = name;
        this.price = price;
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
        this.category = category;
        this.leadersIDs = new ArrayList<String>();
        this.zoomMeetingLink = zoomMeetingLink;
        this.kidsIDs = new ArrayList<String>();
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


    public ULocale.Category getCategory() {
        return category;
    }

    public void setCategory(ULocale.Category category) {
        this.category = category;
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

    public void setStatus(VoicemailContract.Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VoicemailContract.Status getStatus() {
        return status;
    }
}
