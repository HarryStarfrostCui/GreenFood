package com.example.hca127.greenfood;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private String foodName;
    private float mCarbonCoefficient;
    private float mAverageConsumption;
    private float mUserConsumption;
    private float mUserCarbonEmission;

    Ingredient(String foodName, float mCarbonCoefficient, float mAverageConsumption, float mUserConsumption){
        this.foodName = foodName;
        this.mCarbonCoefficient = mCarbonCoefficient;
        this.mAverageConsumption = mAverageConsumption;
        setUserConsumption(mUserConsumption);
        this.mUserCarbonEmission = calculate_user_co2_emission();
    }

    private float calculate_user_co2_emission(){
        return (mCarbonCoefficient)*(mAverageConsumption)*(mUserConsumption);
    }

    public float getUserCarbonEmission(){
        return mUserCarbonEmission;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public float getCarbonCoefficient() {
        return mCarbonCoefficient;
    }

    public void setCarbonCoefficient(float carbonCoefficient) {
        this.mCarbonCoefficient = carbonCoefficient;
        this.mUserCarbonEmission = calculate_user_co2_emission();
    }

    public float getAverageConsumption() {
        return mAverageConsumption;
    }

    public void setAverageConsumption(float averageConsumption) {
        this.mAverageConsumption = averageConsumption;
        this.mUserCarbonEmission = calculate_user_co2_emission();
    }

    public float getUserConsumption() {
        return mUserConsumption;
    }

    public void setUserConsumption(float userConsumption) {
        if(userConsumption == 0)
                this.mUserConsumption = 1.8f;
        else if(userConsumption == 1)
                this.mUserConsumption = 1.5f;
        else if(userConsumption == 2)
                this.mUserConsumption = 1f;
        else if(userConsumption == 3)
                this.mUserConsumption = 0.5f;
        else if(userConsumption == 4)
                this.mUserConsumption = 0;

        this.mUserCarbonEmission = calculate_user_co2_emission();
    }

}
