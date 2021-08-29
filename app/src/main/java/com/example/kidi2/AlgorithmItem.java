package com.example.kidi2;
public class AlgorithmItem   {
    private String courseName;
    private String else_information;


    private int im;


    public AlgorithmItem()
    {
        this.courseName="";
        this.else_information="";
        this.im=R.drawable.gradgrey;
    }
    public AlgorithmItem(String countryName,String else_inf,int im)
    {
        courseName = countryName;
        else_information=else_inf;
        this.im=im;
    }

    public String getAlgorithmName()
    {
        return courseName;
    }
    public String getElse_information(){return else_information;}
    public void setIm(int im) {
        this.im=im;
    }
    public int getIm() {
        return im;
    }

}
