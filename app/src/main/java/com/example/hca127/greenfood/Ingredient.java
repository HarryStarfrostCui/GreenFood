package com.example.hca127.greenfood;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private String foodName;
    private float carbon_coefficient;
    private float average_consumption;
    private float user_consumption;
    private float user_co2_emission;

    Ingredient(String foodName, float carbon_coefficient, float average_consumption, float user_consumption){
        this.foodName = foodName;
        this.carbon_coefficient = carbon_coefficient;
        this.average_consumption = average_consumption;
        setUser_consumption(user_consumption);
        this.user_co2_emission = calculate_user_co2_emission();
    }

    private float calculate_user_co2_emission(){
        return (carbon_coefficient)*(average_consumption)*(user_consumption);
    }

    public float getUser_co2_emission(){
        return user_co2_emission;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public float getCarbon_coefficient() {
        return carbon_coefficient;
    }

    public void setCarbon_coefficient(float carbon_coefficient) {
        this.carbon_coefficient = carbon_coefficient;
        this.user_co2_emission = calculate_user_co2_emission();
    }

    public float getAverage_consumption() {
        return average_consumption;
    }

    public void setAverage_consumption(float average_consumption) {
        this.average_consumption = average_consumption;
        this.user_co2_emission = calculate_user_co2_emission();
    }

    public float getUser_consumption() {
        return user_consumption;
    }

    public void setUser_consumption(float user_consumption) {
        if(user_consumption == 1)
                this.user_consumption = 1.5f;
        else if(user_consumption == 2)
                this.user_consumption = 1f;
        else if(user_consumption == 3)
                this.user_consumption = 0.5f;
        else if(user_consumption == 4)
                this.user_consumption = 0;

        this.user_co2_emission = calculate_user_co2_emission();
    }

}
