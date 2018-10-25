package com.example.hca127.greenfood;

import org.junit.Test;

import static org.junit.Assert.*;

public class IngredientTest {

    Ingredient test = new Ingredient("beef", 20, 20,2);

    @Test
    public void ingredient(){
        Ingredient testing = new Ingredient("lamb",20,20,2);
        assertEquals("lamb", testing.getFoodName());
        assertEquals(20, testing.getCarbon_coefficient(),0.001);
        assertEquals(20, testing.getAverage_consumption(), 0.001);
        assertEquals(1.0, testing.getUser_consumption(),0.001);
    }

    @Test
    public void getUser_co2_emission() {
        double carbon_test_double = test.getCarbon_coefficient();
        double avg_consumption_test_double = test.getAverage_consumption();
        double user_consumption_test_double = test.getUser_consumption();
        assertEquals(carbon_test_double*avg_consumption_test_double*user_consumption_test_double, test.getUser_co2_emission(),0.001);
    }

    @Test
    public void getFoodName() {
        assertEquals("beef",test.getFoodName());
    }

    @Test
    public void setFoodName() {
        test.setFoodName("lamb");
        assertEquals("lamb",test.getFoodName());
    }

    @Test
    public void getCarbon_coefficient() {
        assertEquals(20,test.getCarbon_coefficient(), 0.001);
    }

    @Test
    public void setCarbon_coefficient() {
        test.setCarbon_coefficient(50);
        assertEquals(50, test.getCarbon_coefficient(), 0.001);
    }

    @Test
    public void getAverage_consumption() {
        assertEquals(20, test.getAverage_consumption(), 0.001);
    }

    @Test
    public void setAverage_consumption() {
        test.setAverage_consumption(50);
        assertNotEquals(20, test.getUser_consumption(), 0.001);
        assertEquals(50, test.getAverage_consumption(), 0.001);
    }

    @Test
    public void getUser_consumption() {
        assertEquals(1, test.getUser_consumption(), 0.001);
    }

    @Test
    public void setUser_consumption() {
        test.setUser_consumption(1);
        assertEquals(1.5, test.getUser_consumption(),0.001);
        test.setUser_consumption(2);
        assertEquals(1, test.getUser_consumption(),0.001);
        test.setUser_consumption(3);
        assertEquals(0.5, test.getUser_consumption(),0.001);
        test.setUser_consumption(4);
        assertEquals(0, test.getUser_consumption(),0.001);

    }
}