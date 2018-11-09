package com.example.hca127.greenfood.fragments;


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
import android.widget.TextView;

import com.example.hca127.greenfood.MainActivity;
import com.example.hca127.greenfood.R;
import com.example.hca127.greenfood.objects.Diet;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class ResultFragment extends Fragment {
    private PieChart mUserEmissionPieChart;
    private TextView mResultText;
    private float mUserCarbon;
    private float mAverageCarbon = 1500f;
    private float mLowCarbonPercentage = 0.9f;
    private float mAverageCarbonPercentage = 1.1f;
    private Button mGetSuggestion;

    private Diet mDiet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        mDiet = ((MainActivity)getActivity()).getLocalUserDiet();
        mUserCarbon = mDiet.getUserDietEmission(); //insert calculated mCarbonSaved in tC02e

        mResultText = view.findViewById(R.id.resultText);

        if (mUserCarbon < mAverageCarbon*mLowCarbonPercentage) {
            mResultText.setText(R.string.low_carbon_result);
        } else if (mUserCarbon < mAverageCarbon*mAverageCarbonPercentage) {
            mResultText.setText(R.string.average_carbon_result);
        } else {
            mResultText.setText(R.string.high_carbon_result);
        }

        mUserEmissionPieChart = view.findViewById(R.id.emissionSplitChart);
        setupPieChart(mUserEmissionPieChart);

        mGetSuggestion = (Button) view.findViewById(R.id.getSuggestion);
        mGetSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SuggestionFragment()).addToBackStack(null).commit();
                ((NavigationView)getActivity().findViewById(R.id.navigation_view)).setCheckedItem(R.id.menu_suggestion);
            }
        });

        return view;
    }
    private void setupPieChart(PieChart chart) {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 0; i <mDiet.getSize(); i++) {
            if (mDiet.getIngUserCo2Emission(i) != 0) {
                pieEntries.add(new PieEntry((float) mDiet.getIngUserCo2Emission(i), mDiet.getFoodName(i)));
            }
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setDrawValues(false);
        PieData data = new PieData(dataSet);

        chart.getLegend().setEnabled(false);
        chart.setEntryLabelColor(Color.BLACK);
        chart.getDescription().setEnabled(false);

        chart.setData(data);
        chart.animateY(1200);
        chart.invalidate();
    }

}
