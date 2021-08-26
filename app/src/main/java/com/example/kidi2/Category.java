package com.example.myapplication19;

import retrofit2.http.Field;


public class Category {
   // @Id
   // private String id;
    private String name;
    private String image;
    public Category() {

    }
    public Category(String name, String image) {
        super();
        this.name = name;
        this.image = image;
    }
   // public String getId() {
   //     return id;
    //}
   // public void setId(String id) {
    //    this.id = id;
    //}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImage() {
        return image;
    }
    public void setCategoryImage(String image) {
        this.image = image;
    }
    @Override
    public String toString() {
        return "Category [name=" + name + ", image=" + image + "]";
    }

}