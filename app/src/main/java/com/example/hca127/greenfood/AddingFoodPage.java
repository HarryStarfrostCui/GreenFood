package com.example.hca127.greenfood;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddingFoodPage extends AppCompatActivity {

    public RadioGroup beefRadioGroup;
    public RadioGroup lambRadioGroup;
    public RadioGroup chickenRadioGroup;
    public RadioGroup fishRadioGroup;
    public RadioGroup porkRadioGroup;
    public RadioGroup eggRadioGroup;
    public RadioGroup vegRadioGroup;
    public RadioGroup breadRadioGroup;

    int beefRadioChoice;
    int lambRadioChoice;
    int chickenRadioChoice;
    int fishRadioChoice;
    int porkRadioChoice;
    int eggRadioChoice;
    int vegRadioChoice;
    int breadRadioChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_food_page);
    }

    public void check(View v)
    {

        /*addListenerOnButton();*/
        beefRadioGroup = (RadioGroup)findViewById(R.id.beefRadioGroup);
        beefRadioChoice = beefRadioGroup.getCheckedRadioButtonId();      //integer 1 2 3 4, 1 = high, 4 = none

        lambRadioGroup = findViewById(R.id.lambRadioGroup);
        lambRadioChoice = lambRadioGroup.getCheckedRadioButtonId();

        chickenRadioGroup = findViewById(R.id.chickenRadioGroup);
        chickenRadioChoice = chickenRadioGroup.getCheckedRadioButtonId();

        fishRadioGroup = findViewById(R.id.fishRadioGroup);
        fishRadioChoice = fishRadioGroup.getCheckedRadioButtonId();

        porkRadioGroup = findViewById(R.id.porkRadioGroup);
        porkRadioChoice = porkRadioGroup.getCheckedRadioButtonId();

        eggRadioGroup = findViewById(R.id.eggRadioGroup);
        eggRadioChoice = eggRadioGroup.getCheckedRadioButtonId();

        vegRadioGroup = findViewById(R.id.vegRadioGroup);
        vegRadioChoice = vegRadioGroup.getCheckedRadioButtonId();

        breadRadioGroup = findViewById(R.id.breadRadioGroup);
        breadRadioChoice = breadRadioGroup.getCheckedRadioButtonId();

    }


    /*private RadioGroup beefRadioGroup;
    private RadioButton beefRadioButton;
    private ImageButton checkMark;






    public void addListenerOnButton() {

        beefRadioGroup = (RadioGroup) findViewById(R.id.radio);
        checkMark = (ImageButton) findViewById(R.id.checkMark);

        checkMark.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroup
                int selectedId = beefRadioGroup.getCheckedRadioButtonId();
            }
        }
    }*/


    public void back(View v)
    {
        Intent intent = new Intent(AddingFoodPage.this, MainActivity.class);
        startActivity(intent);
    }
}
