package com.example.hca127.greenfood;

import org.junit.Test;

import static org.junit.Assert.*;

public class IngredientTest {

    Ingredient test = new Ingredient("beef", 20, 20,2);

    @Test
    public void ingredient(){
        Ingredient testing = new Ingredient("lamb",20,20,2);
        assertEquals("lamb", testing.getFoodName());
        assertEquals(20, testing.getCarbonCoefficient(),0.001);
        assertEquals(20, testing.getAverageConsumption(), 0.001);
        assertEquals(1.0, testing.getUserConsumption(),0.001);
    }

    @Test
    public void getUser_co2_emission() {
        double carbon_test_double = test.getCarbonCoefficient();
        double avg_consumption_test_double = test.getAverageConsumption();
        double user_consumption_test_double = test.getUserConsumption();
        assertEquals(carbon_test_double*avg_consumption_test_double*user_consumption_test_double, test.getUserCarbonEmission(),0.001);
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
        assertEquals(20,test.getCarbonCoefficient(), 0.001);
    }

    @Test
    public void setCarbon_coefficient() {
        test.setCarbonCoefficient(50);
        assertEquals(50, test.getCarbonCoefficient(), 0.001);
    }

    @Test
    public void getAverage_consumption() {
        assertEquals(20, test.getAverageConsumption(), 0.001);
    }

    @Test
    public void setAverage_consumption() {
        test.setAverageConsumption(50);
        assertNotEquals(20, test.getUserConsumption(), 0.001);
        assertEquals(50, test.getAverageConsumption(), 0.001);
    }

    @Test
    public void getUser_consumption() {
        assertEquals(1, test.getUserConsumption(), 0.001);
    }

    @Test
    public void setUser_consumption() {
        test.setUserConsumption(1);
        assertEquals(1.5, test.getUserConsumption(),0.001);
        test.setUserConsumption(2);
        assertEquals(1, test.getUserConsumption(),0.001);
        test.setUserConsumption(3);
        assertEquals(0.5, test.getUserConsumption(),0.001);
        test.setUserConsumption(4);
        assertEquals(0, test.getUserConsumption(),0.001);

    }
}