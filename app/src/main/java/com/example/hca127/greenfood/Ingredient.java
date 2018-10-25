package com.example.hca127.greenfood;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private String foodName;
    private double carbon_coefficient;
    private double average_consumption;
    private double user_consumption;
    private double user_co2_emission;
    private double standard_co2_emission;

    Ingredient(String foodName, double carbon_coefficient, double average_consumption, int user_consumption){
        this.foodName = foodName;
        this.carbon_coefficient = carbon_coefficient;
        this.average_consumption = average_consumption;
        setUser_consumption(user_consumption);
        this.user_co2_emission = calculate_user_co2_emission();
    }

    private double calculate_user_co2_emission(){
        return (carbon_coefficient)*(average_consumption)*(user_consumption);
    }

    public double getUser_co2_emission(){
        return user_co2_emission;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getCarbon_coefficient() {
        return carbon_coefficient;
    }

    public void setCarbon_coefficient(double carbon_coefficient) {
        this.carbon_coefficient = carbon_coefficient;
        this.user_co2_emission = calculate_user_co2_emission();
    }

    public double getAverage_consumption() {
        return average_consumption;
    }

    public void setAverage_consumption(double average_consumption) {
        this.average_consumption = average_consumption;
        this.user_co2_emission = calculate_user_co2_emission();
    }

    public double getUser_consumption() {
        return user_consumption;
    }

    public void setUser_consumption(int user_consumption) {
        if(user_consumption == 1)
                this.user_consumption = 1.5;
        else if(user_consumption == 2)
                this.user_consumption = 1;
        else if(user_consumption == 3)
                this.user_consumption = 0.5;
        else if(user_consumption == 4)
                this.user_consumption = 0;

        this.user_co2_emission = calculate_user_co2_emission();
    }

    public double getStandard_co2_emission() {
        return standard_co2_emission;
    }

    public void setStandard_co2_emission(double standard_co2_emmsion) {
        this.standard_co2_emission = standard_co2_emmsion;
    }

    public void setupStandard() {
        standard_co2_emission = 0.5*carbon_coefficient*average_consumption;
    }

    public void setUser_co2_emission(double user_co2_emission) {
        this.user_co2_emission = user_co2_emission;
    }
}
