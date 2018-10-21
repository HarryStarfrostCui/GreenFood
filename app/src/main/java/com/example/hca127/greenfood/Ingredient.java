package com.example.hca127.greenfood;

import java.util.ArrayList;

public class Ingredient {
    private String name;
    private double carbon_coefficient;
    private double serving_weight;
    private int amount;

    Ingredient(String FoodName, double CarbonC, double serving_co){
        name = FoodName;
        carbon_coefficient = CarbonC;
        serving_weight = serving_co;
        amount = 0;
    }

    public String getName() {
        return name;
    }

    public double getCarbon_coefficient() {
        return carbon_coefficient;
    }

    public double getServing_weight() {
        return serving_weight;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getEmit(){
        return amount*serving_weight*carbon_coefficient/1000.0;
    }
}
