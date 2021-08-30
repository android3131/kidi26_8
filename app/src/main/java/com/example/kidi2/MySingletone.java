package com.example.kidi2;

import java.util.ArrayList;
import java.util.List;

public class MySingletone {
    private List<Course_Addactivity> coursesArr = new ArrayList<Course_Addactivity>();
    static MySingletone theOnlyInstance;

    // static method to create instance of Singleton class
    public static MySingletone getInstance()
    {
        if (theOnlyInstance == null)
            theOnlyInstance = new MySingletone();

        return theOnlyInstance;
    }

    public void setDataAtPosition(int position, Course_Addactivity course) {
        coursesArr.set(position, course);
    }
}
