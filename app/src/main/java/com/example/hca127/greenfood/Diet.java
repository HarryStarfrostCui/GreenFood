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

    public String getIngName(int index){        return mBasket.get(index).getFoodName();    }
    public float getIngCarbon(int index){   return mBasket.get(index).getCarbonCoefficient();}
    public float getAvgConsumption(int index){  return mBasket.get(index).getAverageConsumption();}
    public float getUserConsumption(int index){     return mBasket.get(index).getUserConsumption();}
    public float getIngUserCo2Emission(int index) {        return mBasket.get(index).getUserCarbonEmission();    }
    public int getSize(){        return mBasket.size();    }

    public void addNewIngredient(String foodName, float carbonCoefficient, float averageConsumption, float userConsumption){
        Food i = new Food(foodName, carbonCoefficient, averageConsumption, userConsumption);
        mBasket.add(i);
    }

    public void assignUserInput(String userChoiceAsString){
        String temp = userChoiceAsString.substring( userChoiceAsString.length()-1, userChoiceAsString.length());
        int option = Integer.parseInt(temp);
        mUserChoices.add(option);
    }


    public void populateBasket(Context context){
        ArrayList<String> names = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.ingredient_name)));
        ArrayList<String> carbon_coefficient = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.carbon_coefficient)));
        ArrayList<String> average_consumption = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.annual_average_consumption)));

        for(int i = 0; i < NUMBER_OF_FOOD_TYPES; i++){
             addNewIngredient(      names.get(i),
                                    Float.parseFloat(carbon_coefficient.get(i)),
                                    Float.parseFloat(average_consumption.get(i)),
                                    mUserChoices.get(i));
        }
        calculateTotalUserCo2Emission();
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
        for(int i = 0; i < mBasket.size() ; i++){
            mTotalUserCo2Emission += mBasket.get(i).getUserCarbonEmission();
        }
    }

    private ArrayList<Integer> getFavList()
    {
        ArrayList<Double> favourite = new ArrayList<>();
        ArrayList<Integer> index = new ArrayList<>();
        for (int i = 0; i < mBasket.size(); i++)
        {
            if(mBasket.get(i).getUserConsumption()>0.01){
                double temp = Math.round(mBasket.get(i).getUserConsumption()*100)/100;
                favourite.add(temp);
                index.add(i);
            }
        }

        double temp;
        for (int i = 1; i < favourite.size()-1; i++)
        {
            for (int j = i-1; j >= 0; j--)
            {
                if (favourite.get(j) < favourite.get(j+1))
                {
                    temp = favourite.get(j);
                    favourite.set(j, favourite.get(j+1));
                    favourite.set(j+1, temp);
                    temp = index.get(j);
                    index.set(j, index.get(j+1));
                    index.set(j+1, (int)temp);
                }
                else
                {
                    break;
                }
            }
        }
        return index;
    }

    public int getSuggestionMinIndex()
    {
        if(getFavList().size()==0){
            return 0;
        }
        ArrayList<Integer> favourite = getFavList();
        int index = favourite.get(0);
        double current, temp;

        for(int i = 1; i < favourite.size(); i++)
        {
            current = mBasket.get(index).getUserCarbonEmission();
            temp = mBasket.get(favourite.get(i)).getUserCarbonEmission();
            if (temp < current)
            {
                index = favourite.get(i);
            }
        }
        if(index == getSuggestionMaxIndex()){
            return 7; //veggie defult
        }

        return index;
    }

    public int getSuggestionMaxIndex()
    {
        if(getFavList().size()==0){
            return 0;
        }

        ArrayList<Integer> favourite = getFavList();
        int index = favourite.get(0);
        double current, temp;

        for(int i = 1; i < favourite.size(); i++)
        {
            current = mBasket.get(index).getUserCarbonEmission();
            temp = mBasket.get(favourite.get(i)).getUserCarbonEmission();
            if (temp > current)
            {
                index = favourite.get(i);
            }
        }
        return index;
    }


}
