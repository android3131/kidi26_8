package com.example.kidi2;
public class AlgorithmItem_grp6 {
    private String courseName;
    private String else_information;
    private boolean check;


    public AlgorithmItem_grp6()
    {
        this.courseName="";
        this.else_information="";
        this.check=false;
    }
    public AlgorithmItem_grp6(String countryName, String else_inf, boolean check)
    {
        courseName = countryName;
        else_information=else_inf;
        this.check=check;
    }

    public String getAlgorithmName()
    {
        return courseName;
    }
    public String getElse_information(){return else_information;}
    public void setCheck(Boolean check) {
        this.check=check;
    }
    public Boolean getCheck() {
        return check;
    }

}
