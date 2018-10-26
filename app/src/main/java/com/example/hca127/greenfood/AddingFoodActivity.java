package com.example.hca127.greenfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;

public class AddingFoodActivity extends AppCompatActivity {

    private Diet diet;

    private ImageButton nextButton;
    private ImageButton backButton;

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

        diet = new Diet();

        nextButton = (ImageButton) findViewById(R.id.nextButton);
        /*nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                getUserInput();

                Intent intent = new Intent(AddingFoodActivity.this, ResultActivity.class);
                intent.putExtra("diet", diet);
                startActivity(intent);
                finish();
            }
        });
        */
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
        diet.assignUserInput(getResources().getResourceEntryName(beefRadioChoice)); //integer 1 2 3 4, 1 = high, 4 = none

        lambRadioChoice = lambRadioGroup.getCheckedRadioButtonId();
        diet.assignUserInput(getResources().getResourceEntryName(lambRadioChoice));

        chickenRadioChoice = chickenRadioGroup.getCheckedRadioButtonId();
        diet.assignUserInput(getResources().getResourceEntryName(chickenRadioChoice));

        fishRadioChoice = fishRadioGroup.getCheckedRadioButtonId();
        diet.assignUserInput(getResources().getResourceEntryName(fishRadioChoice));

        porkRadioChoice = porkRadioGroup.getCheckedRadioButtonId();
        diet.assignUserInput(getResources().getResourceEntryName(porkRadioChoice));

        eggRadioChoice = eggRadioGroup.getCheckedRadioButtonId();
        diet.assignUserInput(getResources().getResourceEntryName(eggRadioChoice));

        veggieRadioChoice = veggieRadioGroup.getCheckedRadioButtonId();
        diet.assignUserInput(getResources().getResourceEntryName(veggieRadioChoice));

        breadRadioChoice = breadRadioGroup.getCheckedRadioButtonId();
        diet.assignUserInput(getResources().getResourceEntryName(breadRadioChoice));

        diet.populateBasket(this);
    }
}