package com.example.kidi2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.http.Field;

public class DataKid {
    private String id;
    private String fullName;
    private String dateOfBirth;
    private String gender;
    private ArrayList<String> activeCourses;
    private ArrayList<String> completedCourses;
    private String parentId;
    private Status status;
    private Date activeDate;
    private String image;
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public DataKid() {

    }
    public DataKid(String fullName, String dateOfBirth, String gender, String parentId) {
        super();
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.parentId = parentId;
        activeCourses = new ArrayList<String>();
        completedCourses = new ArrayList<String>();
        status = Status.Active;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<String> addCourse(String courseID){
        if(activeCourses.contains(courseID)) {
            System.out.println("Couldn't add, the course already enrolled");
            return null;
        }
        activeCourses.add(courseID);
        return activeCourses;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public ArrayList<String> getActiveCourses() {
        return activeCourses;
    }
    public void setActiveCourses(ArrayList<String> activeCourses) {
        this.activeCourses = activeCourses;
    }
    public ArrayList<String> getCompletedCourses() {
        return completedCourses;
    }
    public void setCompletedCourses(ArrayList<String> completedCourses) {
        this.completedCourses = completedCourses;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Date getActiveDate() {
        return activeDate;
    }
    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }
    @Override
    public String toString() {
        return "Kid [fullName=" + fullName + ", dateOfBirth=" + dateOfBirth + ", parentId=" + parentId + "]";
    }


}
