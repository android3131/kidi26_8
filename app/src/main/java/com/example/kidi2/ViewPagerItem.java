package com.example.kidi2;


public class ViewPagerItem {

    int imageID;
    String name,date,profile, description;

    public ViewPagerItem(int imageID,String date,String name,String profile, String description) {
        this.imageID = imageID;

        this.name=name;
        this.profile=profile;
        this.date=date;
        this.description = description;
    }
}