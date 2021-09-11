package com.example.kidi2;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;




public class Course {


    private String id;

    private String name;


    private String description;


    private double price;


    private Date startDateTime;

    private Date finishDateTime;


    private String categoryId;


    private String categoryName;


    private Status status;

private int clr ;

    public int getClr() {
        return clr;
    }

    public void setClr(int clr) {
        this.clr = clr;
    }

    private ArrayList<String> leadersIDs;

    private String zoomMeetingLink;


    private String urlLink;

    private ArrayList<String> kidsIDs;


    private String day;


    private double meetingDuration;


    private String startHour;

    private String endHour;



    private ArrayList<String> meetings;
    public Course() {
        super();
        this.leadersIDs= new ArrayList<String>();
        kidsIDs= new ArrayList<String>();
        meetings= new ArrayList<String>();
    }



    public Course(String name, Date startDateTime, Date finishDateTime, String categoryId, String zoomMeetingLink,
                  String day, String startHour,String urlLink, String endHour) {
        super();
        this.name = name;

        this.urlLink = urlLink;
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
        this.categoryId = categoryId;
        this.zoomMeetingLink = zoomMeetingLink;
        this.day = day;
        this.leadersIDs= new ArrayList<String>();
        kidsIDs= new ArrayList<String>();
        meetings= new ArrayList<String>();
        this.startHour = startHour;;
        this.status = Status.Active;
        //this.meetingDuration calculate the duration of the meeting
        this.endHour = endHour;
    }


    public Course(String name, Date startDate, Date finishDate, String day, String categoryId) {
        this.name = name;
        this.startDateTime = startDate;
        this.finishDateTime = finishDate;
        this.categoryId = categoryId;
        this.day = day;
        this.leadersIDs= new ArrayList<String>();
        this.kidsIDs= new ArrayList<String>();
        this.meetings= new ArrayList<String>();
        this.status = Status.Active;
    }



    public ArrayList<String> getMeetings() {
        return meetings;
    }

    public void setMeetings(ArrayList<String> meetings) {
        this.meetings = meetings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
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


    public String getCategoryName() {
        return categoryName;
    }



    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
        return id;
    }

    public void setID(String iD) {
        this.id = iD;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getUrlLink() {
        return urlLink;
    }



    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String
                               day) {
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
        return Objects.hash(id, categoryId, name);
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
        return Objects.equals(id, other.id) && Objects.equals(categoryId, other.categoryId)
                && Objects.equals(name, other.name);
    }




}