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
    public ArrayList<Ingredient> getBasket(){   return basket;  }
    public float getIngCarbon(int index){   return basket.get(index).getCarbon_coefficient();}
    public float getIngUserCo2Emission(int index) {        return basket.get(index).getUser_co2_emission();    }
    public int getSize(){        return basket.size();    }

    public void addNewIngredient(String foodName, float carbon_coefficient, float average_consumption, float user_consumption){
        Ingredient i = new Ingredient(foodName, carbon_coefficient, average_consumption, user_consumption);
        basket.add(i);
    }

    public void assignUserInput(String userChoiceAsString){
        String temp = userChoiceAsString.substring( userChoiceAsString.length()-1, userChoiceAsString.length());
        switch(userChoiceAsString){
            /*case "1":
                userChoices.add(1);
                break;
            case "2":
                userChoices.add(2);
                break;
            case "3":
                userChoices.add(3);
                break;
            case "4":
                userChoices.add(4);
                break;*/
            case "beefRadio1": case "lambRadio1" : case "chickenRadio1" : case "fishRadio1" : case "porkRadio1" : case "eggRadio1" : case "veggieRadio1" : case "breadRadio1":
                userChoices.add(1);
                break;
            case "beefRadio2": case "lambRadio2" : case "chickenRadio2" : case "fishRadio2" : case "porkRadio2" : case "eggRadio2" : case "veggieRadio2" : case "breadRadio2":
                userChoices.add(2);
                break;
            case "beefRadio3": case "lambRadio3" : case "chickenRadio3" : case "fishRadio3" : case "porkRadio3" : case "eggRadio3" : case "veggieRadio3" : case "breadRadio3":
                userChoices.add(3);
                break;
            case "beefRadio4": case "lambRadio4" : case "chickenRadio4" : case "fishRadio4" : case "porkRadio4" : case "eggRadio4" : case "veggieRadio4" : case "breadRadio4":
                userChoices.add(4);
                break;
        }
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
        calculate_total_user_co2_emission();
    }

    public float get_total_user_co2_emission(){
        return total_user_co2_emission;
    }

    private void calculate_total_user_co2_emission(){
        total_user_co2_emission = 0;
        for(int i = 0; i < basket.size() ; i++){
            total_user_co2_emission += basket.get(i).getUser_co2_emission();
        }
    }

    private ArrayList<Integer> getFavList()
    {
        ArrayList<Double> favourite = new ArrayList<>();
        ArrayList<Integer> index = new ArrayList<>();
        for (int i = 0; i < basket.size(); i++)
        {
            if(basket.get(i).getUser_consumption()>0.01){
                double temp = Math.round(basket.get(i).getUser_consumption()*100)/100;
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
        ArrayList<Integer> favourite = getFavList();
        int index = favourite.get(0);
        double current, temp;

        for(int i = 1; i < favourite.size(); i++)
        {
            current = basket.get(index).getCarbon_coefficient();
            temp = basket.get(favourite.get(i)).getCarbon_coefficient();
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
        ArrayList<Integer> favourite = getFavList();
        int index = favourite.get(0);
        double current, temp;

        for(int i = 1; i < favourite.size(); i++)
        {
            current = basket.get(index).getCarbon_coefficient();
            temp = basket.get(favourite.get(i)).getCarbon_coefficient();
            if (temp > current)
            {
                index = favourite.get(i);
            }
        }
        return index;
    }

}
