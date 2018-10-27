package com.example.hca127.greenfood;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Diet implements Serializable {
    private ArrayList<Food> mBasket;
    private ArrayList<Integer> mUserChoices;
    private int NUMBER_OF_FOOD_TYPES = 8;
    private float mTotalUserCo2Emission = 0;

    Diet(){
        mBasket = new ArrayList<Food>(NUMBER_OF_FOOD_TYPES);
        mUserChoices = new ArrayList<Integer>(NUMBER_OF_FOOD_TYPES);
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
        float loweredFoodDifference = mBasket.get(getSuggestionMaxIndex()).getLoweredEmission();
        float increasedFoodDifference = mBasket.get(getSuggestionMinIndex()).getIncreasedEmission();

        return this.mTotalUserCo2Emission - loweredFoodDifference + increasedFoodDifference;
    }

    public float getSuggestedDietSavingAmount() {
        float loweredFoodDifference = mBasket.get(getSuggestionMaxIndex()).getLoweredEmission();
        float increasedFoodDifference = mBasket.get(getSuggestionMinIndex()).getIncreasedEmission();

        return loweredFoodDifference - increasedFoodDifference;
    }

    public void calculateTotalUserCo2Emission(){
        mTotalUserCo2Emission = 0;
        for(int i = 0; i < mBasket.size() ; i++){
            mTotalUserCo2Emission += mBasket.get(i).getUserCarbonEmission();
        }
    }


    public int getSuggestionMinIndex()
    {
        int minIndex = 0;
        float minValue = 10000f;

        float temp;
        for (int i = 0; i < mBasket.size(); i++) {
            temp = mBasket.get(i).getCarbonCoefficient();
            if ( minValue < temp) {
                temp = minValue;
                minIndex = i;
            }
        }

        return minIndex;
    }

    public int getSuggestionMaxIndex()
    {
        int maxIndex = 0;
        float maxValue = 0;

        float temp;
        for (int i = 0; i < mBasket.size(); i++) {
            temp = mBasket.get(i).getUserCarbonEmission();
            if ( maxValue > temp) {
                temp = maxValue;
                maxIndex = i;
            }
        }

        return maxIndex;
    }
}
