package com.example.hca127.greenfood;

import java.util.ArrayList;

public class IngredientList {
    private ArrayList<String> name;
    private ArrayList<Double> carbon_coefficient;
    private ArrayList<Integer> amount;
    private ArrayList<Double> carbonEmit;

    IngredientList() {
        name = new ArrayList<String>();
        carbon_coefficient = new ArrayList<Double>();
        amount = new ArrayList<Integer>();
        carbonEmit = new ArrayList<Double>();
        /*
        ArrayList<String> food = new ArrayList<>(
                Arrays.asList("Lamb", "Beef", "Cheese", "Pork", "Salmon",
                        "Turkey", "Chicken", "Tuna", "Eggs", "Potatoes",
                        "Rice", "Peanut Butter", "Nuts", "Yogurt", "Broccoli",
                        "Tofu", "Dry Beans", "Milk(2%)", "Tomatoes", "Lentils")
        );
        ArrayList<Double> carbon = new ArrayList<>(
                Arrays.asList(39.2, 12.0, 13.5, 12.1, 11.9,
                        10.9, 6.9, 6.1, 4.8, 2.9,
                        2.7, 2.5, 2.3, 2.2, 2.0,
                        2.0, 2.0, 1.9, 1.1, 0.9)
        );
        for (int i = 0; i < food.size(); i++){
            addIng(food.get(i), carbon.get(i));
        }
        */

    }

    String getName(int index) {
        return name.get(index);
    }

    double getCarbon(int index) {
        return carbon_coefficient.get(index);
    }

    int getAmout(int index) {
        return amount.get(index);
    }

    int getsize() {
        return name.size();
    }

    void addIng(String food_name, double Carbon) {
        name.add(food_name);
        carbon_coefficient.add(Carbon);
        amount.add(0);
        carbonEmit.add(0.0);
    }

    boolean setIngAmount(int index, int Amount) {
        if (index >= getsize()) {
            return false;
        }
        amount.set(index, Amount);
        carbonEmit.set(index, Amount * carbon_coefficient.get(index));
        return true;
    }

    double getTotalEmit() {
        int i;
        double sum = 0;
        for (i = 0; i < carbonEmit.size(); i++) {
            sum += carbonEmit.get(i);
        }
        return sum;
    }

    public int getSuggestionIndex() {  //later can be use for suggestion
        int index = 0;
        for (int i = 1; i <= carbonEmit.size(); i++) {
            if (carbonEmit.get(index)*carbon_coefficient.get(index) > carbonEmit.get(i)*carbon_coefficient.get(i)) {
                index = i;
            }
        }
        return index;
    }

}
