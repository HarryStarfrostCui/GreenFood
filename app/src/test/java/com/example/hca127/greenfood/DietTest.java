package com.example.hca127.greenfood;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DietTest {

    Diet testDiet = new Diet();

    @Before
    public void setup() {
        float total_user_co2_emission = 0;
        testDiet.addNewIngredient("testFood", 20, 20, 2);
        for (int i = 0; i < testDiet.getSize(); i++) {
            total_user_co2_emission += testDiet.getIngUserCo2Emission(i);
        }
    }

    @Test
    public void getIngName() {
        assertEquals("testFood", testDiet.getIngName(0));
    }

    @Test
    public void getIngCarbon() {
        assertEquals(20, testDiet.getIngCarbon(0), 0.001);
    }


    @Test
    public void getUserConsumption() {
        assertEquals(1, testDiet.getUserConsumption(0), 0.001);
    }

    @Test
    public void getAveConsumption() {
        assertEquals(20, testDiet.getAvgConsumption(0), 0.001);
    }

    @Test
    public void getIngUserCo2Emission() {
        testDiet.calculate_total_user_co2_emission();
        assertEquals(400, testDiet.get_total_user_co2_emission(), 0.001);
    }


    @Test
    public void addNewIngredient() {
        testDiet.addNewIngredient("testFood2", 20, 20, 2);
        assertEquals("testFood2", testDiet.getIngName(1));
    }

    @Test
    public void assignUserInput() {
        testDiet.assignUserInput("beefRadio1");
        assertEquals(1, testDiet.getUserConsumption(0), 0.001);
    }

    @Test
    public void get_total_user_co2_emission() {
        testDiet.calculate_total_user_co2_emission();
        float testFloat = testDiet.get_total_user_co2_emission();
        assertEquals(400, testFloat, 0.001);
    }

    @Test
    public void calculate_total_user_co2_emission() {
        testDiet.addNewIngredient("anotherTestFood", 30, 30, 1);
        float testFloat = 0;
        for (int i = 0; i < testDiet.getSize(); i++) {
            testFloat += testDiet.getIngCarbon(i) *
                    testDiet.getUserConsumption(i) *
                    testDiet.getAvgConsumption(i);
        }

        testDiet.calculate_total_user_co2_emission();
        assertEquals(testFloat, testDiet.get_total_user_co2_emission(), 0.001);
    }

    @Test
    public void getSuggestionMinIndex()
    {
        testDiet.addNewIngredient("secondTest", 30, 30 ,2);
        int min = testDiet.getSuggestionMinIndex();
        assertEquals(0, min);
    }

    @Test
    public void getSuggestionMaxIndex(){
        testDiet.addNewIngredient("secondTest", 30, 30, 2);
        int max = testDiet.getSuggestionMaxIndex();
        assertEquals(1,max);
    }
}
