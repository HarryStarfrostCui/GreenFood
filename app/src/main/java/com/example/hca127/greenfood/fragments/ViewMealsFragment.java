package com.example.hca127.greenfood.fragments;

import android.support.design.widget.NavigationView;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hca127.greenfood.MainActivity;
import com.example.hca127.greenfood.R;
import com.example.hca127.greenfood.objects.Diet;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class ViewMealsFragment extends Fragment {
    private TextView mMealOneUser;
    private TextView mMealOneTitle;
    private TextView mMealOneDescription;
    private TextView mMealOneLocation;
    private ImageView mMealOneImage;

    private TextView mMealTwoUser;
    private TextView mMealTwoTitle;
    private TextView mMealTwoDescription;
    private TextView mMealTwoLocation;
    private ImageView mMealTwoImage;

    private TextView mMealThreeUser;
    private TextView mMealThreeTitle;
    private TextView mMealThreeDescription;
    private TextView mMealThreeLocation;
    private ImageView mMealThreeImage;

    private TextView mMealFourUser;
    private TextView mMealFourTitle;
    private TextView mMealFourDescription;
    private TextView mMealFourLocation;
    private ImageView mMealFourImage;

    private TextView mMealFiveUser;
    private TextView mMealFiveTitle;
    private TextView mMealFiveDescription;
    private TextView mMealFiveLocation;
    private ImageView mMealFiveImage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_meals, container, false);

        mMealOneUser = view.findViewById(R.id.meal_1_user);
        mMealOneTitle = view.findViewById(R.id.meal_1_title);
        mMealOneDescription = view.findViewById(R.id.meal_1_description);
        mMealOneLocation = view.findViewById(R.id.meal_1_location);
        mMealOneImage = view.findViewById(R.id.meal_1_image);

        mMealTwoUser = view.findViewById(R.id.meal_2_user);
        mMealTwoTitle = view.findViewById(R.id.meal_2_title);
        mMealTwoDescription = view.findViewById(R.id.meal_2_description);
        mMealTwoLocation = view.findViewById(R.id.meal_2_location);
        mMealTwoImage = view.findViewById(R.id.meal_2_image);

        mMealThreeUser = view.findViewById(R.id.meal_3_user);
        mMealThreeTitle = view.findViewById(R.id.meal_3_title);
        mMealThreeDescription = view.findViewById(R.id.meal_3_description);
        mMealThreeLocation = view.findViewById(R.id.meal_3_location);
        mMealThreeImage = view.findViewById(R.id.meal_3_image);

        mMealFourUser = view.findViewById(R.id.meal_4_user);
        mMealFourTitle = view.findViewById(R.id.meal_4_title);
        mMealFourDescription = view.findViewById(R.id.meal_4_description);
        mMealFourLocation = view.findViewById(R.id.meal_4_location);
        mMealFourImage = view.findViewById(R.id.meal_4_image);

        mMealFiveUser = view.findViewById(R.id.meal_5_user);
        mMealFiveTitle = view.findViewById(R.id.meal_5_title);
        mMealFiveDescription = view.findViewById(R.id.meal_5_description);
        mMealFiveLocation = view.findViewById(R.id.meal_5_location);
        mMealFiveImage = view.findViewById(R.id.meal_5_image);


        return view;
    }

}
