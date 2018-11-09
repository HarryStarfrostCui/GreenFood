package com.example.hca127.greenfood;

import org.junit.Test;
import com.example.hca127.greenfood.objects.LocalUser;
import static org.junit.Assert.*;


public class LocalUserTest {
    @Test
    public void LocalUserTest() {

        LocalUser user = new LocalUser();
        assertEquals("", user.getUserId());
        assertEquals("anonymoose", user.getName());

    }
}