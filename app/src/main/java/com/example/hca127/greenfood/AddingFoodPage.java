package com.example.hca127.greenfood;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
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

    /*EditText beefEditText = (EditText)findViewById(R.id.beefAmount);
    String beefAmt = beefEditText.getText().toString();
    /*int beefAmount_integer = Integer.parseInt(beefAmt);*/           /*Use to retrieve the amount*/
}
