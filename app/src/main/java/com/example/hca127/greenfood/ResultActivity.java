package com.example.hca127.greenfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


public class ResultActivity extends AppCompatActivity {
    HorizontalBarChart mResultChart;
    HorizontalBarChart mSuggestionChart;
    TextView mResultText;
    TextView mSuggestionText;
    private float userCarbon;
    private float suggestedCarbon;
    private float averageCarbon = 1.5f; // in tCo2e

    private ArrayList<Ingredient> basket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle extras = getIntent().getExtras();
        basket =(ArrayList<Ingredient>)getIntent().getSerializableExtra("basket");
        for(int i = 0; i < basket.size() ; i++){
            userCarbon += basket.get(i).getUser_co2_emission();
        }

        Toast.makeText(this, "total = " + userCarbon, Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "carbon coef: " + Double.toString(basket.get(0).getCarbon_coefficient()) + " . Average consum: " + Double.toString(basket.get(0).getAverage_consumption()) + " . User consum: " + Double.toString(basket.get(0).getUser_consumption()) + " = " + Double.toString(basket.get(0).getUser_co2_emission()), Toast.LENGTH_LONG).show();

        //userCarbon = 2.0f; //insert calculated carbon in tC02e
        suggestedCarbon = .75f; // insert suggested carbon here



        mResultText = findViewById(R.id.resultText);
        mSuggestionText = findViewById(R.id.suggestionText);

        if (userCarbon < 1.25) {
            mResultText.setText(R.string.low_carbon_result);
        } else if (userCarbon < 1.75) {
            mResultText.setText(R.string.average_carbon_result);
        } else {
            mResultText.setText(R.string.high_carbon_result);
        }

        mResultChart = findViewById(R.id.resultChart);
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, userCarbon));
        entries.add(new BarEntry(1, averageCarbon));
        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        set.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(set);
        mResultChart.getXAxis().setDrawGridLines(false);

        mResultChart.setData(data);
        mResultChart.animateY(1200);
        mResultChart.invalidate();


        mSuggestionText.setText(R.string.temp_text);

        mSuggestionChart = findViewById(R.id.suggestionChart);
        ArrayList<BarEntry> suggestionEntries = new ArrayList<>();
        suggestionEntries.add(new BarEntry(0, suggestedCarbon));
        suggestionEntries.add(new BarEntry(1, averageCarbon));
        BarDataSet suggestionSet = new BarDataSet(suggestionEntries, "BarDataSet");
        suggestionSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData suggestionData = new BarData(suggestionSet);
        mSuggestionChart.getXAxis().setDrawGridLines(false);

        mSuggestionChart.setData(suggestionData);
        mSuggestionChart.animateY(2400);
        mSuggestionChart.invalidate();

    }
}
