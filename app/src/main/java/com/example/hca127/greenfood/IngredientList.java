package com.example.hca127.greenfood;

import java.util.ArrayList;

public class IngredientList {
    private ArrayList<String> name;
    private ArrayList<Double> carbon_coefficient;
    private ArrayList<Integer> amount;
    private ArrayList<Double> carbonEmit;

    public IngredientList() {
        name = new ArrayList<String>();
        carbon_coefficient = new ArrayList<Double>();
        amount = new ArrayList<Integer>();
        carbonEmit = new ArrayList<Double>();

    }

    public String getName(int index) {
        return name.get(index);
    }

    public double getCarbon(int index) {
        return carbon_coefficient.get(index);
    }

    public int getAmout(int index) {
        return amount.get(index);
    }

    public int getsize() {
        return name.size();
    }

    public void addIng(String food_name, double Carbon) {
        name.add(food_name);
        carbon_coefficient.add(Carbon);
        amount.add(0);
        carbonEmit.add(0.0);
    }

    public boolean setIngAmount(int index, int Amount) {
        if (index >= getsize()) {
            return false;
        }
        amount.set(index, Amount);
        carbonEmit.set(index, Amount * carbon_coefficient.get(index));
        return true;
    }

    public double getTotalEmit() {
        int i;
        double sum = 0;
        for (i = 1; i < carbonEmit.size(); i++)
            sum += carbonEmit.get(i);
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
