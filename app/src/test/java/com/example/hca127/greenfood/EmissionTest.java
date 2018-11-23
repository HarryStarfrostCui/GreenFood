package com.example.hca127.greenfood;

import com.example.hca127.greenfood.objects.Emission;


import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;


public class EmissionTest {

    @Test
    public void testConstructor() {
        Emission testEmission = new Emission(100f);
        Date testDate = new Date();
        assertEquals(100d, testEmission.getAmount(), 0.01d);

    }

    @Test
    public void testStringDate() {
        assertEquals("me", );

    }



}
