package com.example.kidi2;

import android.location.Address;

import java.util.Date;

public class Admin {
    private String ID;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Address address;
    private Date dateOfBirth;
    private String profilePic;
    private Status activeStatus;
    private Date activeDate;

    public Admin() {
        super();
    }

    public Admin(String fullName, String email, String phoneNumber, Address address, Date dateOfBirth,
                 String profilePic) {
        super();
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.profilePic = profilePic;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }


    public Status getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Status activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    @Override
    public String toString() {
        return "Leader [ID=" + ID + ", fullName=" + fullName + ", email=" + email + ", phoneNumber=" + phoneNumber
                + ", address=" + address + ", dateOfBirth=" + dateOfBirth + ", profilePic=" + profilePic
                +  ", activeStatus=" + activeStatus
                + ", activeDate=" + activeDate + "]";
    }
}
