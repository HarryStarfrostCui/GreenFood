package com.example.hca127.greenfood;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class aboutFragment extends Fragment {

    private float emissionAmount[] = { 1.5f, 1.9f, 1.2f, 3.1f };
    private String emissionName[] = {"Food", "Buildings", "Consumables & Waste", "Transportation"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_about, container, false);

        PieChart mPieChart = v.findViewById(R.id.emissionChart);
        ArrayList<PieEntry> mPieEntries = new ArrayList<>();
        for (int i = 0; i < emissionAmount.length; i++) {
            mPieEntries.add(new PieEntry(emissionAmount[i], emissionName[i]));
        }

        PieDataSet mDataSet = new PieDataSet(mPieEntries, "");
        mDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        mDataSet.setDrawValues(false);
        PieData mData = new PieData(mDataSet);

        mPieChart.getLegend().setEnabled(false);
        mPieChart.setEntryLabelColor(Color.BLACK);
        mPieChart.getDescription().setEnabled(false);

        mPieChart.setData(mData);
        mPieChart.animateY(1200);
        mPieChart.invalidate();
        return v;
    }
}
