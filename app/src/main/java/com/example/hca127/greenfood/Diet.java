package com.example.hca127.greenfood;

import java.io.Serializable;
import java.util.ArrayList;

public class Diet implements Serializable {
    private ArrayList<Food> mBasket;
    private ArrayList<Integer> mUserChoices;
    //private int NUMBER_OF_FOOD_TYPES = 8;
    private float mTotalUserCo2Emission = 0;

    Diet(){
        mBasket = new ArrayList<>();
        mUserChoices = new ArrayList<>();
    }

    public String getFoodName(int index){ return mBasket.get(index).getFoodName();}
    public float getIngCarbon(int index){ return mBasket.get(index).getCarbonCoefficient();}
    public float getAvgConsumption(int index){ return mBasket.get(index).getAverageConsumption();}
    public float getUserConsumption(int index){ return mBasket.get(index).getUserConsumption();}
    public float getIngUserCo2Emission(int index){ return mBasket.get(index).getUserCarbonEmission();}
    public int getSize(){ return mBasket.size();}

    public void addNewIngredient(String foodName, float carbonCoefficient, float averageConsumption, float userConsumption){
        Food i = new Food(foodName, carbonCoefficient, averageConsumption, userConsumption);
        mBasket.add(i);
        calculateTotalUserCo2Emission();
    }

    public void assignUserInput(String userChoiceAsString){
        String temp = userChoiceAsString.substring( userChoiceAsString.length()-1, userChoiceAsString.length());
        int option = Integer.parseInt(temp);
        mUserChoices.add(option);
    }

    public float getUserDietEmission(){
        return mTotalUserCo2Emission;
    }

    public float getSuggestedDietEmission(){
        return this.mTotalUserCo2Emission - this.getSuggestedDietSavingAmount();
    }

    public float getSuggestedDietSavingAmount() {
        float emissionIncrease = mBasket.get(getSuggestionMinIndex()).getEmissionIncrease();
        float emissionReduction = mBasket.get(getSuggestionMaxIndex()).getEmissionReduction();
        return emissionReduction - emissionIncrease;
    }

    public void calculateTotalUserCo2Emission(){
        mTotalUserCo2Emission = 0;
        for(int i = 0; i < mBasket.size() ; i++){
            mTotalUserCo2Emission += mBasket.get(i).getUserCarbonEmission();
        }
    }


    public int getSuggestionMinIndex() {
        int minIndex = 0;
        float minValue = mBasket.get(0).getCarbonCoefficient();

        float temp;
        for(int i = 1; i < mBasket.size() ; i++){
            temp = mBasket.get(i).getCarbonCoefficient();
            if (temp < minValue) {
                minValue = temp;
                minIndex = i;
            }
        }

        return minIndex; // everyone should eat veggies, lol
    }

    public int getSuggestionMaxIndex() {
        int maxIndex = 0;
        float maxValue = mBasket.get(0).getUserCarbonEmission();

        float temp;
        for(int i = 1; i < mBasket.size() ; i++){
            temp = mBasket.get(i).getUserCarbonEmission();
            if (maxValue < temp) {
                maxValue = temp;
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    public float getCarbonFromSpecificFood(int index) {
        return mBasket.get(index).getUserCarbonEmission();
    }
}
