package com.example.kidi2;



import android.location.Address;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Leader {
    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM!@#$%^&*";
    private static final int PASS_SIZE = 8;

    private String id;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private com.example.kidi2.Address address;
    private Date dateOfBirth;
    private String profilePic;
    private ArrayList<String> categoriesIDs = new ArrayList<>();
    private ArrayList<String> coursesIDs = new ArrayList<>();
    private Status activeStatus;
    private Date activeDate;
    private Boolean generatedPassowrd;
    private int clr;



    public int getClr() {
        return clr;
    }



    public void setClr(int clr) {
        this.clr = clr;
    }

    public Leader() {
        super();
    }

    public Leader(String fullName, String email) {
        super();
        this.fullName = fullName;
        this.email = email;
        this.generatedPassowrd = true;
        this.password = generatePassword();

    }

    public Leader(String fullName, String email, String password) {
        super();
        this.fullName = fullName;
        this.email = email;
        this.generatedPassowrd = false;
        this.password = password;

    }

    public Leader(String fullName, String email, String phoneNumber, com.example.kidi2.Address address, Date dateOfBirth,
                  String profilePic) {
        super();
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.profilePic = profilePic;
        this.generatedPassowrd = true;
        this.password = generatePassword();

    }



    private String generatePassword() {
        Random random=new Random();
        StringBuilder sb=new StringBuilder(PASS_SIZE);

        for(int i=0;i<PASS_SIZE;i++){
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        }
        return sb.toString();
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
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

    public com.example.kidi2.Address getAddress() {
        return address;
    }

    public void setAddress(com.example.kidi2.Address address) {
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

    public ArrayList<String> getCategoriesIDs() {
        return categoriesIDs;
    }

    public void setCategoriesIDs(ArrayList<String> categoriesIDs) {
        this.categoriesIDs = categoriesIDs;
    }

    public ArrayList<String> getCoursesIDs() {
        return coursesIDs;
    }

    public void setCoursesIDs(ArrayList<String> coursesIDs) {
        this.coursesIDs = coursesIDs;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getGeneratedPassword() {
        return generatedPassowrd;
    }

    public void setGeneratedPassword(Boolean b) {
        this.generatedPassowrd = b;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Leader [ID=" + id + ", fullName=" + fullName + ", email=" + email + ", phoneNumber=" + phoneNumber
                + ", address=" + address + ", dateOfBirth=" + dateOfBirth + ", profilePic=" + profilePic
                + ", categoriesIDs=" + categoriesIDs + ", coursesIDs=" + coursesIDs + ", activeStatus=" + activeStatus
                + ", activeDate=" + activeDate + "]";
    }
}
