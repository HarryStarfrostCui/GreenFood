package com.example.hca127.greenfood;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


public class ResultActivity extends AppCompatActivity {
    HorizontalBarChart mResultChart;
    HorizontalBarChart mSuggestionChart;
    PieChart mUserEmissionSplitChart;
    TextView mResultText;
    TextView mReduceSuggestionText;
    TextView mIncreaseSuggestionText;
    private float userCarbon;
    private float suggestedCarbon;
    private float averageCarbon = 15000f;
    private float lowCarbonPercentage = 0.9f;
    private float averageCarbonPercentage = 1.1f;


    private Diet diet;
    private ArrayList<Ingredient> basket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        Bundle extras = this.getIntent().getExtras();

        diet = (Diet)getIntent().getSerializableExtra("diet");
        basket = diet.getBasket();

        userCarbon = diet.get_total_user_co2_emission(); //insert calculated carbon in tC02e
        suggestedCarbon = 1200f; // insert suggested carbon here

        mResultText = findViewById(R.id.resultText);
        mReduceSuggestionText = findViewById(R.id.reduceSuggestionText);
        mIncreaseSuggestionText = findViewById(R.id.increaseSuggestionText);

        if (userCarbon < averageCarbon*lowCarbonPercentage) {
            mResultText.setText(R.string.low_carbon_result);
        } else if (userCarbon < averageCarbon*averageCarbonPercentage) {
            mResultText.setText(R.string.average_carbon_result);
        } else {
            mResultText.setText(R.string.high_carbon_result);
        }

        mResultChart = findViewById(R.id.resultChart);
        setUpHorizontalBarChart(mResultChart, averageCarbon, userCarbon);

        mReduceSuggestionText.setText(basket.get(0).getFoodName());
        mIncreaseSuggestionText.setText(basket.get(2).getFoodName());


        mSuggestionChart = findViewById(R.id.suggestionChart);
        setUpHorizontalBarChart(mSuggestionChart, suggestedCarbon, userCarbon);

        mUserEmissionSplitChart = findViewById(R.id.emissionSplitChart);
        setupPieChart(mUserEmissionSplitChart);

    }

    private void setUpHorizontalBarChart(HorizontalBarChart chart, float valueOne, float valueTwo) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, valueOne));
        entries.add(new BarEntry(1, valueTwo));

        BarDataSet barDataSet = new BarDataSet(entries, "BarDataSet");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData suggestionData = new BarData(barDataSet);
        chart.getXAxis().setDrawGridLines(false);
        chart.getLegend().setEnabled(false);

        chart.setData(suggestionData);
        chart.animateY(1200);
        chart.invalidate();
    }

    private void setupPieChart(PieChart chart) {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 0; i < basket.size(); i++) {
            pieEntries.add(new PieEntry((float)diet.getBasket().get(i).getUser_co2_emission(), "temp"));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);

        chart = findViewById(R.id.emissionSplitChart);
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
