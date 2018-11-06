package com.example.hca127.greenfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.hca127.greenfood.objects.Diet;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;


public class ResultActivity extends AppCompatActivity {
    private PieChart mUserEmissionPieChart;
    private TextView mResultText;
    private float mUserCarbon;
    private float mAverageCarbon = 1500f;
    private float mLowCarbonPercentage = 0.9f;
    private float mAverageCarbonPercentage = 1.1f;
    private Button mGetSuggestion;

    private Diet mDiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("mDiet", "");

        //mDiet = (Diet)getIntent().getSerializableExtra("diet");
        mDiet = gson.fromJson(json, Diet.class);


        // use this to get saved diet from shared preferences
        /*
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("diet", "");
        Diet savedDiet = gson.fromJson(json, Diet.class);
        */

        mUserCarbon = mDiet.getUserDietEmission(); //insert calculated mCarbonSaved in tC02e

        mResultText = findViewById(R.id.resultText);

        if (mUserCarbon < mAverageCarbon*mLowCarbonPercentage) {
            mResultText.setText(R.string.low_carbon_result);
        } else if (mUserCarbon < mAverageCarbon*mAverageCarbonPercentage) {
            mResultText.setText(R.string.average_carbon_result);
        } else {
            mResultText.setText(R.string.high_carbon_result);
        }

        mUserEmissionPieChart = findViewById(R.id.emissionSplitChart);
        setupPieChart(mUserEmissionPieChart);

        mGetSuggestion = (Button) findViewById(R.id.getSuggestion);
        mGetSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ResultActivity.this, SuggestionActivity.class);
                intent.putExtra("diet", mDiet);
                startActivity(intent);
            }
        });
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

        chart = findViewById(R.id.emissionSplitChart);
        chart.getLegend().setEnabled(false);
        chart.setEntryLabelColor(Color.BLACK);
        chart.getDescription().setEnabled(false);

        chart.setData(data);
        chart.animateY(1200);
        chart.invalidate();
    }
}
