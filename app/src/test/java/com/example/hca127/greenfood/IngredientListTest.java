package com.example.hca127.greenfood;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class IngredientListTest {
    @Test
    public void getName() {
        IngredientList plate = new IngredientList();
        plate.addIng("Lamb",39.2,75);
        assertEquals("Lamb", plate.getName(0));
    }

    @Test
    public void getCarbon() {
        IngredientList plate = new IngredientList();
        plate.addIng("Lamb",39.2);
        assertEquals(39.2, plate.getCarbon(0),0.0001);
    }

    @Test
    public void getAmout() {
        IngredientList plate = new IngredientList();
        plate.addIng("Lamb",39.2);
        plate.setIngAmount(0,10);
        assertEquals(10,plate.getAmout(0));
    }

    @Test
    public void getsize() {
        IngredientList plate = new IngredientList();
        plate.addIng("Lamb",39.2);
        assertEquals(1, plate.getsize());
        plate.addIng("Beef",27.0);
        assertEquals(2, plate.getsize());
    }

    @Test
    public void addIng() {
        IngredientList plate = new IngredientList();
        plate.addIng("Lamb",39.2);
        assertEquals(1, plate.getsize());
        assertEquals("Lamb", plate.getName(0));
        assertEquals(39.2, plate.getCarbon(0),0.0001);
    }

    @Test
    public void setIngAmount() {
        IngredientList plate = new IngredientList();
        plate.addIng("Lamb",39.2);
        assertEquals(true, plate.setIngAmount(0,1));
        assertEquals(false, plate.setIngAmount(1,2));
    }

    @Test
    public void getTotalEmit() {
        ArrayList<String> food = new ArrayList<>(
                Arrays.asList("Lamb", "Beef", "Cheese", "Pork", "Salmon",
                        "Turkey", "Chicken", "Tuna", "Eggs", "Potatoes",
                        "Rice", "Peanut Butter", "Nuts", "Yogurt", "Broccoli",
                        "Tofu", "Dry Beans", "Milk(2%)", "Tomatoes", "Lentils")
        );
        ArrayList<Double> carbon = new ArrayList<>(
                Arrays.asList(39.2, 27.0, 13.5, 12.1, 11.9,
                        10.9, 6.9, 6.1, 4.8, 2.9,
                        2.7, 2.5, 2.3, 2.2, 2.0,
                        2.0, 2.0, 1.9, 1.1, 0.9)
        );
        IngredientList plate = new IngredientList();
        double total = 0;
        Random rand = new Random();
        int amount;
        for (int i = 0; i < food.size(); i++){
            plate.addIng(food.get(i), carbon.get(i));
            amount = rand.nextInt(10);
            plate.setIngAmount(i, amount);
            total += amount*carbon.get(i);
        }
        assertEquals(20, plate.getsize());
        assertEquals(total, plate.getTotalEmit(),0.0001);
    }

    @Test
    public void getSuggestionIndex() {  //TBD
        //IngredientList plate = new IngredientList();
        //plate.addIng("Lamb",39.2);
    }
}
