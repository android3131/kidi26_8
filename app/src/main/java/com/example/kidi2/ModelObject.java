package com.example.kidi2;

public enum ModelObject {
    RED(R.string.red,R.layout.view_red),
    BLUE(R.string.blue,R.layout.view_blue),
    GREEN(R.string.green,R.layout.view_green);

    private int id1;
    private int id2;
    ModelObject(int title,int resid){id1=title; id2=resid;}


    public int getId2() {
        return id2;
    }


    public int getId1() {
        return id1;
    }
}
