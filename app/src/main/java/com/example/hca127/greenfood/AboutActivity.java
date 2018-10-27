package com.example.hca127.greenfood;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class AboutActivity extends AppCompatActivity {

    private float emissionAmount[] = { 1.5f, 1.9f, 1.2f, 3.1f };
    private String emissionName[] = {"Food", "Buildings", "Consumables & Waste", "Transportation"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        PieChart mPieChart = findViewById(R.id.emissionChart);
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
    }

}