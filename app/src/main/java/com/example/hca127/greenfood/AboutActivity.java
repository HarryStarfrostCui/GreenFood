package com.example.hca127.greenfood;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class AboutActivity extends AppCompatActivity {
    float emissionAmount[] = { 1.5f, 1.9f, 1.2f, 3.1f};
    String emissionName[] = {"Food", "Buildings", "Consumables & Waste", "Transportation"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setupPieChart();
    }

    private void setupPieChart() {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 0; i < emissionAmount.length; i++) {
            pieEntries.add(new PieEntry(emissionAmount[i], emissionName[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Consumption-Based GHG Emissions, 2015");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);

        PieChart chart = findViewById(R.id.emissionChart);
        Legend legend = chart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);

        chart.setEntryLabelColor(Color.BLACK);

        chart.setData(data);
        chart.animateY(1200);
        chart.invalidate();


    }

}
