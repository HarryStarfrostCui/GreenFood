package com.example.hca127.greenfood;

import android.app.Application;

public class tempFoodAmount extends Application {
    private int chickenAmount = 0;

    public int getChickenAmount() {
        return chickenAmount;
    }

    public void setChickenAmount(int newChickenAmount) {
        this.chickenAmount = newChickenAmount;
    }
}
