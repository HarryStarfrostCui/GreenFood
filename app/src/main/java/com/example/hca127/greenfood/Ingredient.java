package com.example.hca127.greenfood;



public class Ingredient {
    private String name;
    private double carbon_coefficient;

    Ingredient(String foodName, double carbonC){
        name = foodName;
        carbon_coefficient = carbonC;
    }

    public String getName() {
        return name;
    }

    public void setName(String foodName){
        name = foodName;
    }

    public double getCarbon_coefficient() {
        return carbon_coefficient;
    }

    public void setCarbon_coefficient(double carbonC){
        carbon_coefficient = carbonC;
    }
}
