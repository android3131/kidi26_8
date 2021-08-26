package com.example.kidi2;



// POJO = plain old JAVA object

import android.provider.VoicemailContract;

import java.time.LocalDate;
import java.util.List;

public class DataModelParent {

    // string variables for our car
    private String id;

    private String fullName;

    private String phoneNumber;

    private String email;

    private String password;

    private String paymentMethod; // ??

    private String paymentDetails;

    private VoicemailContract.Status active;

    private List<String> kids;


    private LocalDate activeDate; //first time login

    public DataModelParent(String id, String fullName, String phoneNumber,
                           String email, String password, String paymentMethod,
                           String paymentDetails, VoicemailContract.Status active, List<String> kids,
                           LocalDate activeDate) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.paymentMethod = paymentMethod;
        this.paymentDetails = paymentDetails;
        this.active = active;
        this.kids = kids;
        this.activeDate = activeDate;
    }


    public DataModelParent() {
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

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public VoicemailContract.Status getActive() {
        return active;
    }

    public void setActive(VoicemailContract.Status active) {
        this.active = active;
    }

    public List<String> getKids() {
        return kids;
    }

    public void setKids(List<String> kids) {
        this.kids = kids;
    }

    public LocalDate getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(LocalDate activeDate) {
        this.activeDate = activeDate;
    }
    public String toString() {
        return "Parent [fullName=" + fullName + ", phoneNumnber=" + phoneNumber + ", email=" + email + "]";
    }
}

