package com.example.kidi2;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Parent {
    private String id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String password;
    private String paymentMethod; // ??
    private String paymentDetails;
    private Status active;
    private List<String> kids = new ArrayList<>();
    private Date activeDate; //first time login

    public Parent() {
        super();
        activeDate = new Date();
    }

    public Parent(String fullName, String phoneNumber, String email, String password,Date d) {
        super();
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        activeDate = d;
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


    public List<String> getKids() {
        return kids;
    }

    public void setKids(List<String> kids) {
        this.kids = kids;
    }

    public void removeKid (String id){
        kids.remove(id);

    }
    public void addKid (String id) {
        kids.add(id);
    }
/*	public List<Bills> getBill() {
		return bill;
	}

	public void setBill(List<Bills> bill) {
		this.bill = bill;
	}*/

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }
    public Status getActive () {
        return active;
    }

    public void setActive (Status s) {
        active = s;
    }

    @Override
    public String toString() {
        return "Parent [fullName=" + fullName + ", phoneNumnber=" + phoneNumber + ", email=" + email + "]";
    }


}