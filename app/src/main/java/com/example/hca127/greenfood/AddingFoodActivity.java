package com.example.hca127.greenfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AddingFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_food);


    }



    public void back(View v)
    {
        Intent intent = new Intent(AddingFoodActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
