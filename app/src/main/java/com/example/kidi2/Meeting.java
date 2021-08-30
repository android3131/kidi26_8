package com.example.kidi2;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Meeting {

    private String id;

    private String courseId;

    private ArrayList<String> participants;

    private Date meetingDateTime;
    private double actualMeetingDuration;

    private boolean cancelled;

    public Meeting() {
        this.cancelled = false;
    }
    public Meeting( String courseId, Date meetingDateTime) {
        super();
        this.courseId = courseId;
        this.meetingDateTime = meetingDateTime;
        this.cancelled = false;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    public ArrayList<String> getParticipants() {
        return participants;
    }
    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }
    public Date getMeetingDateTime() {
        return meetingDateTime;
    }
    public void setMeetingDateTime(Date meetingDateTime) {
        this.meetingDateTime = meetingDateTime;
    }


    public double getActualMeetingDuration() {
        return actualMeetingDuration;
    }
    public void setActualMeetingDuration(double actualMeetingDuration) {
        this.actualMeetingDuration = actualMeetingDuration;
    }
    public boolean isCancelled() {
        return cancelled;
    }
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }


    @Override
    public int hashCode() {
        return Objects.hash(courseId, id);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Meeting other = (Meeting) obj;
        return Objects.equals(courseId, other.courseId) && Objects.equals(id, other.id);
    }
    @Override
    public String toString() {
        return "Meeting [id=" + id + ", courseId=" + courseId + ", meetingDateTime=" + meetingDateTime + "]";
    }



}