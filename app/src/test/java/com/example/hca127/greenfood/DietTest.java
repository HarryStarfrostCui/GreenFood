package com.example.hca127.greenfood;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DietTest {

    Diet testDiet = new Diet();
    @Before
    public void setup()
    {
        float total_user_co2_emission = 0;
        testDiet.addNewIngredient("hello", 20, 20, 2);
        for(int i = 0; i < testDiet.getBasket().size() ; i++){
            total_user_co2_emission += testDiet.getBasket().get(i).getUser_co2_emission();
        }
    }

    @Test
    public void getBasket() {

        assertEquals("hello",testDiet.getBasket().get(0).getFoodName());

    }

    @Test
    public void addNewIngredient() {
       testDiet.addNewIngredient("testFood", 20, 20, 2);
       assertEquals("testFood", testDiet.getBasket().get(1).getFoodName());
    }

    @Test
    public void assignUserInput() {
        testDiet.assignUserInput("beefRadio1");
        assertEquals(1, testDiet.getBasket().get(0).getUser_consumption(), 0.001);
    }

    @Test
    public void get_total_user_co2_emission() {

        testDiet.calculate_total_user_co2_emission();

        assertEquals(400, testDiet.get_total_user_co2_emission(), 0.001);
    }

    @Test
    public void calculate_total_user_co2_emission(){
        testDiet.addNewIngredient("anotherTestFood", 30, 30, 1);
        float testFloat = 0;
        for(int i = 0; i < testDiet.getBasket().size(); i++) {
            testFloat += testDiet.getBasket().get(i).getCarbon_coefficient() *
                    testDiet.getBasket().get(i).getAverage_consumption()*
                    testDiet.getBasket().get(i).getUser_consumption();
        }

        testDiet.calculate_total_user_co2_emission();
        assertEquals(testFloat, testDiet.get_total_user_co2_emission(), 0.001);
    }
}
