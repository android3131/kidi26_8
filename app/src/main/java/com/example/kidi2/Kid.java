package com.example.kidi2;




import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class Kid {

    private String id;
    private String fullName;

    private Date dateOfBirth;

    private Gender gender;

    private ArrayList<String> activeCourses;

    private ArrayList<String> completedCourses;

    private String parentId;

    private Status status;

    private String activeDate;

    private String image;

    private ArrayList<String> meetings;
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Kid() {

    }
    public Kid(String fullName, Date dateOfBirth, Gender gender,String parentId) {
        super();
        this.fullName = fullName.toLowerCase();
        initMyDate(new Date());
        this.gender = gender;
        this.parentId=parentId;

        this.activeCourses = new ArrayList<String>();
        this.completedCourses = new ArrayList<String>();
        this.meetings = new ArrayList<String>();
    }
    private void initMyDate(Date activeDate){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");




        String strDate = dateFormat.format(activeDate);
        this.activeDate=strDate;

    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public ArrayList<String> getMeetings() {
        return meetings;
    }
    public void setMeetings(ArrayList<String> meetings) {
        this.meetings = meetings;
    }
//    public Date getDateOfBirth() {
//        return dateOfBirth;
//    }
//    public void setDateOfBirth(Date dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
//    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public List<String> addCourse(String courseID){
        if(activeCourses.contains(courseID)) {
            System.out.println("Couldn’t add, the course already enrolled");
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
    public List<String> getActiveCourses() {
        return activeCourses;
    }
    public void setActiveCourses(ArrayList<String> activeCourses) {
        this.activeCourses = activeCourses;
    }
    public List<String> getCompletedCourses() {
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
//    public Date getActiveDate() {
//        return activeDate;
//    }
//    public void setActiveDate(Date activeDate) {
//        this.activeDate = activeDate;
//    }

    public boolean deleteCourse(String courseId) {
        String newcourseId = courseId.replace(String.valueOf('"'),"");
        for(String listCourseId: activeCourses) {
            if(listCourseId.equals(newcourseId)) {
                if(activeCourses.remove(newcourseId)) {
                    completedCourses.add(newcourseId);
                    return true;
                }
            }
        }

        return false;
    }

    public List<String> addMeeting(String meetingID){
        if(meetings.contains(meetingID)) {
            System.out.println("Couldn’t add, the meeting already enrolled");
            return null;
        }
        meetings.add(meetingID);
        return meetings;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateOfBirth, fullName, gender, id, parentId);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Kid other = (Kid) obj;
        return Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(fullName, other.fullName)
                && gender == other.gender && Objects.equals(id, other.id) && Objects.equals(parentId, other.parentId);
    }
    @Override
    public String toString() {
        return "Kid [fullName=" + fullName + ", dateOfBirth=" + dateOfBirth + ", parentId=" + parentId + "]";
    }


}