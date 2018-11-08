package com.example.hca127.greenfood.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hca127.greenfood.MainActivity;
import com.example.hca127.greenfood.R;
import com.example.hca127.greenfood.objects.Diet;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class PledgeFragment extends Fragment {
    private BarChart mSuggestionChart;
    private Diet mDiet;
    private TextView mReduceSuggestionText;
    private TextView mIncreaseSuggestionText;
    private TextView mUserEmissionSaving;
    private TextView mCarbonSaved;
    private TextView mTreesSaved;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pledge, container, false);

        mDiet = ((MainActivity)getActivity()).getLocalUserDiet();

        mReduceSuggestionText = view.findViewById(R.id.reduceSuggestionText);
        mReduceSuggestionText.setText(mDiet.getFoodName(mDiet.getSuggestionMaxIndex()));

        mIncreaseSuggestionText = view.findViewById(R.id.increaseSuggestionText);
        mIncreaseSuggestionText.setText(mDiet.getFoodName(mDiet.getSuggestionMinIndex()));

        mUserEmissionSaving = view.findViewById(R.id.userEmissionSaving);
        mUserEmissionSaving.setText(String.valueOf(mDiet.getSuggestedDietSavingAmount()));

        mSuggestionChart = view.findViewById(R.id.suggestionChart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, mDiet.getUserDietEmission()));
        entries.add(new BarEntry(1, 1500f));
        entries.add(new BarEntry(2, mDiet.getSuggestedDietEmission()));

        BarDataSet barDataSet = new BarDataSet(entries, "BarDataSet");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData suggestionData = new BarData(barDataSet);
        mSuggestionChart.getXAxis().setDrawGridLines(false);
        mSuggestionChart.getLegend().setEnabled(false);
        mSuggestionChart.getAxisRight().setAxisMinimum(0f);
        mSuggestionChart.getAxisLeft().setAxisMinimum(0f);
        mSuggestionChart.getDescription().setEnabled(false);

        mSuggestionChart.setData(suggestionData);
        mSuggestionChart.animateY(1200);
        mSuggestionChart.invalidate();

        float carbonSaved = mDiet.getSuggestedDietSavingAmount() *.9f * 2463000f / 1000;
        float treesSaved = carbonSaved/22;  // carbon offset of trees

        if(carbonSaved < 0)
        {
            carbonSaved = 0;
            treesSaved = 0;
        }

        mCarbonSaved = view.findViewById(R.id.carbonSaved);
        mCarbonSaved.setText(String.valueOf(carbonSaved));

        mTreesSaved = view.findViewById(R.id.treesSaved);
        mTreesSaved.setText(String.valueOf(treesSaved));

        return view;
    }

}
