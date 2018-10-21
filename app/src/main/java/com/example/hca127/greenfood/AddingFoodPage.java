package com.example.hca127.greenfood;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class AddingFoodPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_food_page);


    }



    public void back(View v)
    {
        Intent intent = new Intent(AddingFoodPage.this, MainActivity.class);
        startActivity(intent);
    }

    public void chickenPopUp(View v) {
        Intent intent = new Intent(AddingFoodPage.this, PopUpActivity.class);
        startActivity(intent);

    }
}
