package com.example.kidi2;



public class RVitem1 {
    private String algorithmName;

    public String getAlgorithmName() {
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

    public RVitem1(String algorithmName, int color) {
        this.algorithmName = algorithmName;
        this.color = color;
    }
}
