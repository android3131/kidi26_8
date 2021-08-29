package com.example.kidi2;



public enum Status {

    Active ("active"),
    InActive ("inactive"),
    Pending ("pending");

    private final String position;
    Status(String position) {
        this.position = position;
    }


}