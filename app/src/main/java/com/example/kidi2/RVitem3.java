package com.example.kidi2;

public class RVitem3 {
    private String algorithmName;

    public String getItemName() {
        return algorithmName;
    }

    private int color;

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public RVitem3(String algorithmName, int color) {
        this.algorithmName = algorithmName;
        this.color = color;
    }


}