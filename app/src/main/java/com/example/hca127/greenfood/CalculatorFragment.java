package com.example.hca127.greenfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Arrays;

public class CalculatorFragment extends Fragment {

    private Diet diet;

    private ImageButton nextButton;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_calculator, container, false);

        diet = new Diet();

        nextButton = (ImageButton) view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                getUserInput();

                Intent intent = new Intent(getActivity(), ResultActivity.class);
                intent.putExtra("diet", diet);
                startActivity(intent);
                getActivity().recreate();
            }
        });

        beefRadioGroup = (RadioGroup) view.findViewById(R.id.beefRadioGroup);
        lambRadioGroup = (RadioGroup) view.findViewById(R.id.lambRadioGroup);
        chickenRadioGroup = (RadioGroup) view.findViewById(R.id.chickenRadioGroup);
        fishRadioGroup = (RadioGroup) view.findViewById(R.id.fishRadioGroup);
        porkRadioGroup = (RadioGroup) view.findViewById(R.id.porkRadioGroup);
        eggRadioGroup = (RadioGroup) view.findViewById(R.id.eggRadioGroup);
        veggieRadioGroup = (RadioGroup) view.findViewById(R.id.vegRadioGroup);
        breadRadioGroup = (RadioGroup) view.findViewById(R.id.breadRadioGroup);

        beefRadioGroup.check(R.id.beefRadio2);
        lambRadioGroup.check(R.id.lambRadio2);
        chickenRadioGroup.check(R.id.chickenRadio2);
        fishRadioGroup.check(R.id.fishRadio2);
        porkRadioGroup.check(R.id.porkRadio2);
        eggRadioGroup.check(R.id.eggRadio2);
        veggieRadioGroup.check(R.id.veggieRadio2);
        breadRadioGroup.check(R.id.breadRadio2);
        return view;
    }

    public void getUserInput() {
        String mChoice;
        String mLevel;

        ArrayList<String> mFoodNames = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.ingredient_name)));
        ArrayList<String> mCarbonCoefficient = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.carbon_coefficient)));
        ArrayList<String> mAverageConsumption = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.annual_average_consumption)));

        beefRadioChoice = beefRadioGroup.getCheckedRadioButtonId();
        mChoice = getResources().getResourceEntryName(beefRadioChoice);
        mLevel = mChoice.substring( mChoice.length()-1, mChoice.length());
        diet.addNewIngredient(mFoodNames.get(0), Float.parseFloat(mCarbonCoefficient.get(0)),
                Float.parseFloat(mAverageConsumption.get(0)),
                Float.parseFloat(mLevel));

        lambRadioChoice = lambRadioGroup.getCheckedRadioButtonId();
        mChoice = getResources().getResourceEntryName(lambRadioChoice);
        mLevel = mChoice.substring( mChoice.length()-1, mChoice.length());
        diet.addNewIngredient(mFoodNames.get(1), Float.parseFloat(mCarbonCoefficient.get(1)),
                Float.parseFloat(mAverageConsumption.get(1)),
                Float.parseFloat(mLevel));

        chickenRadioChoice = chickenRadioGroup.getCheckedRadioButtonId();
        mChoice = getResources().getResourceEntryName(chickenRadioChoice);
        mLevel = mChoice.substring( mChoice.length()-1, mChoice.length());
        diet.addNewIngredient(mFoodNames.get(2), Float.parseFloat(mCarbonCoefficient.get(2)),
                Float.parseFloat(mAverageConsumption.get(2)),
                Float.parseFloat(mLevel));

        fishRadioChoice = fishRadioGroup.getCheckedRadioButtonId();
        mChoice = getResources().getResourceEntryName(fishRadioChoice);
        mLevel = mChoice.substring( mChoice.length()-1, mChoice.length());
        diet.addNewIngredient(mFoodNames.get(3), Float.parseFloat(mCarbonCoefficient.get(3)),
                Float.parseFloat(mAverageConsumption.get(3)),
                Float.parseFloat(mLevel));

        porkRadioChoice = porkRadioGroup.getCheckedRadioButtonId();
        mChoice = getResources().getResourceEntryName(porkRadioChoice);
        mLevel = mChoice.substring( mChoice.length()-1, mChoice.length());
        diet.addNewIngredient(mFoodNames.get(4), Float.parseFloat(mCarbonCoefficient.get(4)),
                Float.parseFloat(mAverageConsumption.get(4)),
                Float.parseFloat(mLevel));

        eggRadioChoice = eggRadioGroup.getCheckedRadioButtonId();
        mChoice = getResources().getResourceEntryName(eggRadioChoice);
        mLevel = mChoice.substring( mChoice.length()-1, mChoice.length());
        diet.addNewIngredient(mFoodNames.get(5), Float.parseFloat(mCarbonCoefficient.get(5)),
                Float.parseFloat(mAverageConsumption.get(5)),
                Float.parseFloat(mLevel));

        veggieRadioChoice = veggieRadioGroup.getCheckedRadioButtonId();
        mChoice = getResources().getResourceEntryName(veggieRadioChoice);
        mLevel = mChoice.substring( mChoice.length()-1, mChoice.length());
        diet.addNewIngredient(mFoodNames.get(6), Float.parseFloat(mCarbonCoefficient.get(6)),
                Float.parseFloat(mAverageConsumption.get(6)),
                Float.parseFloat(mLevel));


        breadRadioChoice = breadRadioGroup.getCheckedRadioButtonId();
        mChoice = getResources().getResourceEntryName(breadRadioChoice);
        mLevel = mChoice.substring( mChoice.length()-1, mChoice.length());
        diet.addNewIngredient(mFoodNames.get(7), Float.parseFloat(mCarbonCoefficient.get(7)),
                Float.parseFloat(mAverageConsumption.get(7)),
                Float.parseFloat(mLevel));

    }



}