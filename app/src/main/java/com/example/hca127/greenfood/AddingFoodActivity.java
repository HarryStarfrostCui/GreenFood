package com.example.hca127.greenfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class AddingFoodActivity extends AppCompatActivity {

    private ImageButton nextButton;
    private ImageButton backButton;
    private ArrayList<Integer> basket;

    private RadioGroup beefRadioGroup;
    private RadioGroup lambRadioGroup;
    private RadioGroup chickenRadioGroup;
    private RadioGroup fishRadioGroup;
    private RadioGroup porkRadioGroup;
    private RadioGroup eggRadioGroup;
    private RadioGroup veggieRadioGroup;
    private RadioGroup breadRadioGroup;

    private int beefRadioChoice;
    private int lambRadioChoice;
    private int chickenRadioChoice;
    private int fishRadioChoice;
    private int porkRadioChoice;
    private int eggRadioChoice;
    private int veggieRadioChoice;
    private int breadRadioChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_food);

        basket = new ArrayList<Integer>(8);

       /* backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AddingFoodActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });*/

        nextButton = (ImageButton) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                getUserInput();

                Intent intent = new Intent(AddingFoodActivity.this, ResultActivity.class);
                intent.putExtra("basket", basket);
                startActivity(intent);
                finish();
            }
        });

        beefRadioGroup = (RadioGroup) findViewById(R.id.beefRadioGroup);
        lambRadioGroup = (RadioGroup) findViewById(R.id.lambRadioGroup);
        chickenRadioGroup = (RadioGroup) findViewById(R.id.chickenRadioGroup);
        fishRadioGroup = (RadioGroup) findViewById(R.id.fishRadioGroup);
        porkRadioGroup = (RadioGroup) findViewById(R.id.porkRadioGroup);
        eggRadioGroup = (RadioGroup) findViewById(R.id.eggRadioGroup);
        veggieRadioGroup = (RadioGroup) findViewById(R.id.vegRadioGroup);
        breadRadioGroup = (RadioGroup) findViewById(R.id.breadRadioGroup);

        beefRadioGroup.check(R.id.beefRadio2);
        lambRadioGroup.check(R.id.lambRadio2);
        chickenRadioGroup.check(R.id.chickenRadio2);
        fishRadioGroup.check(R.id.fishRadio2);
        porkRadioGroup.check(R.id.porkRadio2);
        eggRadioGroup.check(R.id.eggRadio2);
        veggieRadioGroup.check(R.id.veggieRadio2);
        breadRadioGroup.check(R.id.breadRadio2);
    }

    public void getUserInput() {
        beefRadioChoice = beefRadioGroup.getCheckedRadioButtonId();
        assignUserInput(getResources().getResourceEntryName(beefRadioChoice)); //integer 1 2 3 4, 1 = high, 4 = none

        lambRadioChoice = lambRadioGroup.getCheckedRadioButtonId();
        assignUserInput(getResources().getResourceEntryName(lambRadioChoice));

        chickenRadioChoice = chickenRadioGroup.getCheckedRadioButtonId();
        assignUserInput(getResources().getResourceEntryName(chickenRadioChoice));

        fishRadioChoice = fishRadioGroup.getCheckedRadioButtonId();
        assignUserInput(getResources().getResourceEntryName(fishRadioChoice));

        porkRadioChoice = porkRadioGroup.getCheckedRadioButtonId();
        assignUserInput(getResources().getResourceEntryName(porkRadioChoice));

        eggRadioChoice = eggRadioGroup.getCheckedRadioButtonId();
        assignUserInput(getResources().getResourceEntryName(eggRadioChoice));

        veggieRadioChoice = veggieRadioGroup.getCheckedRadioButtonId();
        assignUserInput(getResources().getResourceEntryName(veggieRadioChoice));

        breadRadioChoice = breadRadioGroup.getCheckedRadioButtonId();
        assignUserInput(getResources().getResourceEntryName(breadRadioChoice));
    }

    private void assignUserInput(String userChoiceAsString){
        switch(userChoiceAsString){
            case "beefRadio1": case "lambRadio1" : case "chickenRadio1" : case "fishRadio1" : case "porkRadio1" : case "eggRadio1" : case "veggieRadio1" : case "breadRadio1":
                basket.add(1);
                break;
            case "beefRadio2": case "lambRadio2" : case "chickenRadio2" : case "fishRadio2" : case "porkRadio2" : case "eggRadio2" : case "veggieRadio2" : case "breadRadio2":
                basket.add(2);
                break;
            case "beefRadio3": case "lambRadio3" : case "chickenRadio3" : case "fishRadio3" : case "porkRadio3" : case "eggRadio3" : case "veggieRadio3" : case "breadRadio3":
                basket.add(3);
                break;
            case "beefRadio4": case "lambRadio4" : case "chickenRadio4" : case "fishRadio4" : case "porkRadio4" : case "eggRadio4" : case "veggieRadio4" : case "breadRadio4":
                basket.add(4);
                break;
        }
    }
}