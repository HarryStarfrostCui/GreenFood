package com.example.hca127.greenfood.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.hca127.greenfood.MainActivity;
import com.example.hca127.greenfood.R;
import com.example.hca127.greenfood.objects.Diet;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class AddingFoodFragment extends Fragment {

    private Diet mDiet;

    private ImageView mNextImageView;

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
        View view = inflater.inflate(R.layout.fragment_adding_food, container, false);

        mDiet = ((MainActivity)getActivity()).getLocalUserDiet();

        mNextImageView = view.findViewById(R.id.nextImageView);
        mNextImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                getUserInput();

                ((MainActivity)getActivity()).setLocalUserDiet(mDiet);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ResultFragment()).addToBackStack(null).commit();
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
        String choice;
        String level;

        ArrayList<String> mFoodNames = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.ingredient_name)));
        ArrayList<String> mCarbonCoefficient = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.carbon_coefficient)));
        ArrayList<String> mAverageConsumption = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.annual_average_consumption)));

        beefRadioChoice = beefRadioGroup.getCheckedRadioButtonId();
        choice = getResources().getResourceEntryName(beefRadioChoice);
        level = choice.substring( choice.length()-1, choice.length());
        mDiet.addNewIngredient(mFoodNames.get(0), Float.parseFloat(mCarbonCoefficient.get(0)),
                Float.parseFloat(mAverageConsumption.get(0)),
                Float.parseFloat(level));

        lambRadioChoice = lambRadioGroup.getCheckedRadioButtonId();
        choice = getResources().getResourceEntryName(lambRadioChoice);
        level = choice.substring( choice.length()-1, choice.length());
        mDiet.addNewIngredient(mFoodNames.get(1), Float.parseFloat(mCarbonCoefficient.get(1)),
                Float.parseFloat(mAverageConsumption.get(1)),
                Float.parseFloat(level));

        chickenRadioChoice = chickenRadioGroup.getCheckedRadioButtonId();
        choice = getResources().getResourceEntryName(chickenRadioChoice);
        level = choice.substring( choice.length()-1, choice.length());
        mDiet.addNewIngredient(mFoodNames.get(2), Float.parseFloat(mCarbonCoefficient.get(2)),
                Float.parseFloat(mAverageConsumption.get(2)),
                Float.parseFloat(level));

        fishRadioChoice = fishRadioGroup.getCheckedRadioButtonId();
        choice = getResources().getResourceEntryName(fishRadioChoice);
        level = choice.substring( choice.length()-1, choice.length());
        mDiet.addNewIngredient(mFoodNames.get(3), Float.parseFloat(mCarbonCoefficient.get(3)),
                Float.parseFloat(mAverageConsumption.get(3)),
                Float.parseFloat(level));

        porkRadioChoice = porkRadioGroup.getCheckedRadioButtonId();
        choice = getResources().getResourceEntryName(porkRadioChoice);
        level = choice.substring( choice.length()-1, choice.length());
        mDiet.addNewIngredient(mFoodNames.get(4), Float.parseFloat(mCarbonCoefficient.get(4)),
                Float.parseFloat(mAverageConsumption.get(4)),
                Float.parseFloat(level));

        eggRadioChoice = eggRadioGroup.getCheckedRadioButtonId();
        choice = getResources().getResourceEntryName(eggRadioChoice);
        level = choice.substring( choice.length()-1, choice.length());
        mDiet.addNewIngredient(mFoodNames.get(5), Float.parseFloat(mCarbonCoefficient.get(5)),
                Float.parseFloat(mAverageConsumption.get(5)),
                Float.parseFloat(level));

        veggieRadioChoice = veggieRadioGroup.getCheckedRadioButtonId();
        choice = getResources().getResourceEntryName(veggieRadioChoice);
        level = choice.substring( choice.length()-1, choice.length());
        mDiet.addNewIngredient(mFoodNames.get(6), Float.parseFloat(mCarbonCoefficient.get(6)),
                Float.parseFloat(mAverageConsumption.get(6)),
                Float.parseFloat(level));

        breadRadioChoice = breadRadioGroup.getCheckedRadioButtonId();
        choice = getResources().getResourceEntryName(breadRadioChoice);
        level = choice.substring( choice.length()-1, choice.length());
        mDiet.addNewIngredient(mFoodNames.get(7), Float.parseFloat(mCarbonCoefficient.get(7)),
                Float.parseFloat(mAverageConsumption.get(7)),
                Float.parseFloat(level));
    }

}
