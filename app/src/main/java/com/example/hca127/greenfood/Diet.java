package com.example.hca127.greenfood;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Diet implements Serializable {
    private ArrayList<Ingredient> basket;
    private ArrayList<Integer> userChoices;
    private int NUMBER_OF_FOOD_TYPES = 8;
    private float total_user_co2_emission = 0;

    Diet(){
        basket = new ArrayList<Ingredient>(NUMBER_OF_FOOD_TYPES);
        userChoices = new ArrayList<Integer>(NUMBER_OF_FOOD_TYPES);
    }

    public String getIngName(int index){        return basket.get(index).getFoodName();    }
    public float getIngCarbon(int index){   return basket.get(index).getCarbonCoefficient();}
    public float getAvgConsumption(int index){  return basket.get(index).getAverageConsumption();}
    public float getUserConsumption(int index){     return basket.get(index).getUserConsumption();}
    public float getIngUserCo2Emission(int index) {        return basket.get(index).getUserCarbonEmission();    }
    public int getSize(){        return basket.size();    }

    public void addNewIngredient(String foodName, float carbon_coefficient, float average_consumption, float user_consumption){
        Ingredient i = new Ingredient(foodName, carbon_coefficient, average_consumption, user_consumption);
        basket.add(i);
    }

    public void assignUserInput(String userChoiceAsString){
        String temp = userChoiceAsString.substring( userChoiceAsString.length()-1, userChoiceAsString.length());
        int option = Integer.parseInt(temp);
        userChoices.add(option);
    }


    public void populateBasket(Context context){
        ArrayList<String> names = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.ingredient_name)));
        ArrayList<String> carbon_coefficient = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.carbon_coefficient)));
        ArrayList<String> average_consumption = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.annual_average_consumption)));

        for(int i = 0; i < NUMBER_OF_FOOD_TYPES; i++){
             addNewIngredient(      names.get(i),
                                    Float.parseFloat(carbon_coefficient.get(i)),
                                    Float.parseFloat(average_consumption.get(i)),
                                    userChoices.get(i));
        }
        calculateTotalUserCo2Emission();
    }

    public float getUserDietEmission(){
        return total_user_co2_emission;
    }

    public float getSuggestedDietEmission(){
        return 1200f;
    }


    public void calculateTotalUserCo2Emission(){
        for(int i = 0; i < basket.size() ; i++){
            total_user_co2_emission += basket.get(i).getUserCarbonEmission();
        }
    }

    private ArrayList<Integer> getFavList()
    {
        ArrayList<Double> favourite = new ArrayList<>();
        ArrayList<Integer> index = new ArrayList<>();
        for (int i = 0; i < basket.size(); i++)
        {
            if(basket.get(i).getUserConsumption()>0.01){
                double temp = Math.round(basket.get(i).getUserConsumption()*100)/100;
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
            current = basket.get(index).getCarbonCoefficient();
            temp = basket.get(favourite.get(i)).getCarbonCoefficient();
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
            current = basket.get(index).getUserCarbonEmission();
            temp = basket.get(favourite.get(i)).getUserCarbonEmission();
            if (temp > current)
            {
                index = favourite.get(i);
            }
        }
        return index;
    }

}
