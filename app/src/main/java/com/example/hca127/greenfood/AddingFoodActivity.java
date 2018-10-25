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

    private ArrayList<RadioGroup> rategroups;

    private int RadioChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_food);

        basket = new ArrayList<Ingredient>(FOOD_TYPES);
        userChoices = new ArrayList<Integer>(FOOD_TYPES);
        rategroups = new ArrayList<>();

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

        final int[] group_id = {
                R.id.beefRadioGroup, R.id.lambRadioGroup, R.id.chickenRadioGroup, R.id.fishRadioGroup,
                R.id.porkRadioGroup, R.id.eggRadioGroup, R.id.vegRadioGroup, R.id.breadRadioGroup
        };
        final int[] checklist = {
                R.id.beefRadio2, R.id.lambRadio2, R.id.chickenRadio2, R.id.fishRadio2,
                R.id.porkRadio2, R.id.eggRadio2, R.id.veggieRadio2, R.id.breadRadio2
        };

        for(int i = 0; i<group_id.length; i++){
            rategroups.add((RadioGroup)findViewById(group_id[i]));
            rategroups.get(i).check(checklist[i]);
        }
    }

    public void getUserInput() {

        for(int i = 0; i<rategroups.size(); i++){
            RadioChoice = rategroups.get(i).getCheckedRadioButtonId();
            assignUserInput(getResources().getResourceEntryName(RadioChoice));
        }

        populateArrayList();
    }

    private void assignUserInput(String userChoiceAsString){
        String num = userChoiceAsString.substring(userChoiceAsString.length()-1, userChoiceAsString.length());
        switch(num){
            case "1":
                userChoices.add(1);
                break;
            case "2":
                userChoices.add(2);
                break;
            case "3":
                userChoices.add(3);
                break;
            case "4":
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