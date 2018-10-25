package com.example.hca127.greenfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class AddingFoodActivity extends AppCompatActivity {

    private ImageButton nextButton;
    private ImageButton backButton;
    private ArrayList<Ingredient> basket;
    private ArrayList<Integer> userChoices;
    private int FOOD_TYPES = 8;

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

        basket = new ArrayList<Ingredient>(FOOD_TYPES);
        userChoices = new ArrayList<Integer>(FOOD_TYPES);

        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AddingFoodActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

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

        beefRadioGroup.check(R.id.beefRadio4);
        lambRadioGroup.check(R.id.lambRadio4);
        chickenRadioGroup.check(R.id.chickenRadio4);
        fishRadioGroup.check(R.id.fishRadio4);
        porkRadioGroup.check(R.id.porkRadio4);
        eggRadioGroup.check(R.id.eggRadio4);
        veggieRadioGroup.check(R.id.veggieRadio4);
        breadRadioGroup.check(R.id.breadRadio4);
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

        populateArrayList();
    }

    private void assignUserInput(String userChoiceAsString){
        switch(userChoiceAsString){
            case "beefRadio1": case "lambRadio1" : case "chickenRadio1" : case "fishRadio1" : case "porkRadio1" : case "eggRadio1" : case "veggieRadio1" : case "breadRadio1":
                userChoices.add(1);
                break;
            case "beefRadio2": case "lambRadio2" : case "chickenRadio2" : case "fishRadio2" : case "porkRadio2" : case "eggRadio2" : case "veggieRadio2" : case "breadRadio2":
                userChoices.add(2);
                break;
            case "beefRadio3": case "lambRadio3" : case "chickenRadio3" : case "fishRadio3" : case "porkRadio3" : case "eggRadio3" : case "veggieRadio3" : case "breadRadio3":
                userChoices.add(3);
                break;
            case "beefRadio4": case "lambRadio4" : case "chickenRadio4" : case "fishRadio4" : case "porkRadio4" : case "eggRadio4" : case "veggieRadio4" : case "breadRadio4":
                userChoices.add(4);
                break;
        }
    }

    private void populateArrayList(){
        ArrayList<String> names = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.ingredient_name)));
        ArrayList<String> carbon_coefficient = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.carbon_coefficient)));
        ArrayList<String> average_consumption = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.annual_average_consumption)));

        for(int i = 0; i < FOOD_TYPES ; i++){
            Ingredient ing = new Ingredient(    names.get(i),
                                                Double.parseDouble(carbon_coefficient.get(i)),
                                                Double.parseDouble(average_consumption.get(i)),
                                                userChoices.get(i));

            basket.add(ing);
        }
    }
}