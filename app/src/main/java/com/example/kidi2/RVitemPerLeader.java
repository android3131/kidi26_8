package com.example.kidi2;



public class RVitemPerLeader {
    private String email;
    private String phone;
    private String name;
    private int clr;

    public int getClr() {
        return clr;
    }

    public void setClr(int clr) {
        this.clr = clr;
    }

    public RVitemPerLeader(String email, String phone, String name) {
        this.email = email;
        this.phone = phone;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
