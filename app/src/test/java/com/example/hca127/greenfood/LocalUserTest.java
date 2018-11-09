package com.example.hca127.greenfood;

import com.example.hca127.greenfood.objects.Diet;
import com.example.hca127.greenfood.objects.LocalUser;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LocalUserTest {
    private LocalUser testUser;
    private Diet testDiet = new Diet();
    private Diet testDiet2 = new Diet();
    ArrayList<Diet> testLocalUserDiet;

    @Before
    public void setUp()
    {
        testUser = new LocalUser();
        testUser.setUserId("123");
        testUser.setCity(0);
        testUser.setFirstName("patrick");
        testUser.setPledge(10.23);
        testUser.setUserEmail("patrick@gmail.com");
        testUser.setUserPassword("w123456");
        testDiet.addNewIngredient("Beef", 20, 20, 2);
        testDiet2.addNewIngredient("Chicken", 20, 20, 2);
        testLocalUserDiet = new ArrayList<Diet>();
        testLocalUserDiet.add(testDiet);
    }

    @Test
    public void getUserId() {
        assertEquals("123", testUser.getUserId());
    }

    @Test
    public void getName() {
        assertEquals("patrick", testUser.getName());
    }

    @Test
    public void getUserEmail() {
        assertEquals("patrick@gmail.com", testUser.getUserEmail());
    }

    @Test
    public void getPledge() {
        assertEquals(10.23, testUser.getPledge(), 0.001);
    }

    @Test
    public void getCity() {
        assertEquals(0, testUser.getCity());
    }

    @Test
    public void addDiet() {
        testUser.addDiet(testDiet2);
        assertEquals(testDiet2.getFoodName(0),testUser.getCurrentDiet().getFoodName(0));
    }

    @Test
    public void getInitialDiet() {
        assertEquals(testDiet.getFoodName(0), testUser.getInitialDiet().getFoodName(0));
    }

    @Test
    public void getCurrentDiet() {
        assertEquals(testLocalUserDiet.get(0).getFoodName(0),testUser.getCurrentDiet().getFoodName(0));
    }

    @Test
    public void getDietDateList() {
        assertEquals(testDiet.getDate(), testUser.getDietDateList().get(0));
    }

    @Test
    public void getEmissionList() {
        LocalUser testUser2 = new LocalUser();
        float CO2e = 0;
        for (int i = 0; i < testLocalUserDiet.size(); i++)
        {
            CO2e = CO2e + testDiet.getUserDietEmission();
        }
        assertEquals(2276.250244140625, testUser2.getEmissionList().get(0), 0.001);
    }

    @Test
    public void renewDiet() {
        testUser.addDiet(testDiet2);
        testUser.addDiet(testDiet);
        testUser.renewDiet();
        assertEquals("Beef",testUser.getCurrentDiet().getFoodName(0));
    }

    @Test
    public void setUserId() {
        testUser.setUserId("testUserID");
        assertEquals("testUserID", testUser.getUserId());

    }

    @Test
    public void setFirstName() {
        testUser.setFirstName("testUserName");
        assertEquals("testUserName", testUser.getName());
    }

    @Test
    public void setUserEmail() {
        testUser.setUserEmail("testUserEmail");
        assertEquals("testUserEmail", testUser.getUserEmail());
    }

    @Test
    public void setPledge() {
        testUser.setPledge(1.1);
        assertEquals(1.1,testUser.getPledge(),0.01);
    }

    @Test
    public void setCity() {
        testUser.setCity(1);
        assertEquals(1, testUser.getCity());
    }

    @Test
    public void setUserPassword() {
        testUser.setUserPassword("testUserPassword");
        assertTrue(testUser.CheckPassword("testUserPassword"));
    }

    @Test
    public void checkPassword() {
        testUser.setUserPassword("testUserPassword");
        assertTrue(testUser.CheckPassword("testUserPassword"));
    }
}