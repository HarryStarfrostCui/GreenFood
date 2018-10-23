package com.example.hca127.greenfood;

import java.util.ArrayList;

public class IngredientList {
    private ArrayList<Ingredient> plate;

    IngredientList() {
        plate = new ArrayList<Ingredient>();
    }

    String getName(int index) {        return plate.get(index).getName();    }

    double getCarbon(int index) {
        return plate.get(index).getCarbon_coefficient();
    }

    int getsize() {
        return plate.size();
    }

    void addIng(String food_name, double carbon) {
        Ingredient newcomer = new Ingredient(food_name, carbon);
        plate.add(newcomer);
    }

    /* TODO: FIX
    double getTotalEmit() {
        int i;
        double sum = 0;
        for (i = 0; i < getsize(); i++) {
            sum += plate.get(i).getEmit();
        }
        return sum;
    }
    */

    /* TODO: FIX
    private ArrayList<Integer> getFavList(){
        //stage 1: 7 food the user like most
        ArrayList<Integer> favourite = new ArrayList<>();
        ArrayList<Integer> Index = new ArrayList<>();
        for(int i = 0; i<favourite.size(); i++){
            favourite.add(plate.get(i).getAmount());
            Index.add(i);
        }

        int temp;
        for(int i = 1; i<getsize(); i++){
            for(int j = i-1; j>=0; j--){
                if(favourite.get(j)<favourite.get(j+1)){
                    temp = favourite.get(j);
                    favourite.set(j, favourite.get(j+1));
                    favourite.set(j+1, temp);
                    temp = Index.get(j);
                    Index.set(j, Index.get(j+1));
                    Index.set(j+1,temp);
                }else{
                    break;
                }
            }
        }
        while((favourite.size()>6 || favourite.get(favourite.size()-1)==0) && Index.size()>0){
            favourite.remove(favourite.size()-1);
            Index.remove(Index.size()-1);
        }
        if(Index.size()==0){
            Index.add(plate.size()-1); //empty list case handle
        }
        return Index;
    }
    */


    /* TODO: FIX
    public int getSuggestionIndex() {  //later can be use for suggestion
        ArrayList<Integer> favourite = getFavList();
        int index = favourite.get(0);
        int initual = index;

        double current, temp;
        for(int i = 1; i<favourite.size();i++){
            current = plate.get(index).getCarbon_coefficient() * plate.get(index).getEmit();
            temp = plate.get(favourite.get(i)).getCarbon_coefficient() * plate.get(favourite.get(i)).getEmit();
            if(temp < current){
                index = i;
            }
        }

        if(index==initual){
            return index+1;
        }
        return index;
    }
    */

}
