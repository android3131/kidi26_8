package com.example.kidi2;




public class ViewPagerItem {

    int imageID;
    String name,date,profile, description;


    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ViewPagerItem(int imageID, String date, String name, String profile, String description) {
        this.imageID = imageID;

        this.name=name;
        this.profile=profile;
        this.date=date;
        this.description = description;
    }
}