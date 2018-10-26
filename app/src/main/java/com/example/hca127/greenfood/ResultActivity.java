package com.example.hca127.greenfood;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


public class ResultActivity extends AppCompatActivity {
    PieChart mUserEmissionSplitChart;
    TextView mResultText;
    private float userCarbon;
    private float averageCarbon = 1500f;
    private float lowCarbonPercentage = 0.9f;
    private float averageCarbonPercentage = 1.1f;
    Button mGetSuggestion;

    private Diet diet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        diet = (Diet)getIntent().getSerializableExtra("diet");

        userCarbon = diet.getUserDietEmission(); //insert calculated mCarbonSaved in tC02e

        mResultText = findViewById(R.id.resultText);

        if (userCarbon < averageCarbon*lowCarbonPercentage) {
            mResultText.setText(R.string.low_carbon_result);
        } else if (userCarbon < averageCarbon*averageCarbonPercentage) {
            mResultText.setText(R.string.average_carbon_result);
        } else {
            mResultText.setText(R.string.high_carbon_result);
        }

        mUserEmissionSplitChart = findViewById(R.id.emissionSplitChart);
        setupPieChart(mUserEmissionSplitChart);

        mGetSuggestion = (Button) findViewById(R.id.getSuggestion);
        mGetSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ResultActivity.this, SuggestionActivity.class);
                intent.putExtra("diet", diet);
                startActivity(intent);
                finish();
            }
        });

    }


    private void setupPieChart(PieChart chart) {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 0; i <diet.getSize(); i++) {
            if (diet.getIngUserCo2Emission(i) != 0) {
                pieEntries.add(new PieEntry((float) diet.getIngUserCo2Emission(i), diet.getIngName(i)));
            }
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setDrawValues(false);
        PieData data = new PieData(dataSet);

        chart = findViewById(R.id.emissionSplitChart);
        Legend legend = chart.getLegend();
        legend.setEnabled(false);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);

        chart.setEntryLabelColor(Color.BLACK);
        chart.getDescription().setEnabled(false);

        chart.setData(data);
        chart.animateY(1200);
        chart.invalidate();


    }

}
