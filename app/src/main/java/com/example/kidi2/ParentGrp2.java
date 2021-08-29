package com.example.kidi2;

import android.util.Patterns;

public class ParentGrp2 {
    private String fullName;
    private String email;
    private String password;
    //private String reTypePassword;
    private String phoneNumber;
    private String id;
    private boolean flag = true;
    //private List<kid> myKids;

    /**
     * Default constructor
     */
    public ParentGrp2() {

    }

    /**
     * full constructor (with empty kids set).
     *
     * @param fullName - the parent full name
     * @param email    - the parent email
     * @param password - the parent password
     * @param phoneNum - the parent phone number (without the area code).
     */
    public ParentGrp2(String fullName, String phoneNum, String email, String password) {
        setFullName(fullName);
        setPhoneNumber(phoneNum);
        setEmail(email);
        setPassword(password);
        //this.myKids=new ArrayList<>();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    /**
     * default set, with a regex of full name that checks, the name is only letters and white spaces.
     *
     * @param fullName
     */
    public void setFullName(String fullName) {
        if (fullName.matches("(?=^.{6,40}$)^[a-zA-Z-]+\\s[a-zA-Z-]+$"))
            this.fullName = fullName;
        else
            flag = false;
    }


    public String getEmail() {
        return email;
    }

    /**
     * default set, uses patterns to check the given email is a valid email (with @domain.com etc)
     *
     * @param email
     */
    public void setEmail(String email) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches())
            this.email = email;
        else
            flag = false;
    }

    public String getPassword() {
        return password;
    }

    /**
     * sets password, with checking that the password and the reType are the same.
     *
     * @param password
     */
    public void setPassword(String password) {

        this.password = password;

    }

//    public String getReTypePassword() {
//        return reTypePassword;
//    }

   // public void setReTypePassword(String reTypePassword) {
//        this.reTypePassword = reTypePassword;
//    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * default set, checks that the phone number is valid.
     *
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        if (Patterns.PHONE.matcher(phoneNumber).matches())
            this.phoneNumber = phoneNumber;
        else
            this.phoneNumber = "123456789";
    }

    @Override
    public String toString() {
        return "Parent{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNum='" + phoneNumber + '\'' +
                '}';
    }
}
