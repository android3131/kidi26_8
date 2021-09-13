package com.example.kidi2;




import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;




public class Parent {

    private String id;

    private String fullName;

    private String phoneNumber;

    private String email;

    private String password;

    private String paymentMethod; // ??

    private String paymentDetails;
    private Status activeStatus;
    private Date dateOfBirth;



    private Status status;

    private List <String> kids= new ArrayList<String>();;
    //@Field
    //private List <Bills> bill;

    private String activeDate; //first time login

    public Parent() {
        super();
        initMyDate(new Date());

    }

    public Parent(String fullName, String phoneNumber, String email, String password) {
        super();
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email.toLowerCase();
        this.password = password;
        this.status = Status.Active;

       initMyDate(new Date());

    }
    private void initMyDate(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");


        String strDate = dateFormat.format(date);
        this.activeDate=strDate;
    }
    public Parent(String fullName, String phoneNumber, String email) {
        super();
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email.toLowerCase();

       // status = Status.Active;
        initMyDate(new Date());
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

    public void setKids(List<String> kidsIds) {
        this.kids = kidsIds;
    }

    public void removeKid (String kidId){
        kids.remove(kidId);

    }
    public void addKid (String kidId) {
        kids.add(kidId);
    }
/*	public List<Bills> getBill() {
		return bill;
	}
	public void setBill(List<Bills> bill) {
		this.bill = bill;
	}*/

    public String getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(String activeDate) {
        this.activeDate = activeDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus (Status s) {
        status = s;
    }



    @Override
    public int hashCode() {
        return Objects.hash(email, phoneNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Parent other = (Parent) obj;
        return Objects.equals(email, other.email) && Objects.equals(phoneNumber, other.phoneNumber);
    }

    @Override
    public String toString() {
        return "Parent [fullName=" + fullName + ", phoneNumnber=" + phoneNumber + ", email=" + email +"Date"+activeDate +"]";
    }
    public Status getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Status activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}