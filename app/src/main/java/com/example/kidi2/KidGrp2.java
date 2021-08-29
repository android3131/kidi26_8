package com.example.kidi2;

public class KidGrp2 {

    // string variables for our kid
    private String fullName;
    private String dayBirth;
    private String monthBirth;
    private String yearBirth;
    private String gender;

    public KidGrp2() {

    }

    public KidGrp2(String fullName, String dayBirth, String monthBirth, String yearBirth, String gender) {
        super();
        this.fullName = fullName;
        this.dayBirth = dayBirth;
        this.monthBirth = monthBirth;
        this.yearBirth = yearBirth;
        this.gender = gender;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDayBirth() {
        return dayBirth;
    }

    public void setDayBirth(String dayBirth) {
        this.dayBirth = dayBirth;
    }

    public String getMonthBirth() {
        return monthBirth;
    }

    public void setMonthbirth(String monthBirth) {
        this.monthBirth = monthBirth;
    }

    public String getYearBirth() {
        return yearBirth;
    }

    public void setYearBirth(String yearBirth) {
        this.yearBirth = yearBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
