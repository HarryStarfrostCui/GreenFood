package com.example.hca127.greenfood;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
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

import java.util.ArrayList;

public class SuggestionActivity extends AppCompatActivity {
    BarChart mSuggestionChart;
    private Button mAboutButton;
    private Diet diet;
    TextView mReduceSuggestionText;
    TextView carbon;
    TextView tree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        diet = (Diet)getIntent().getSerializableExtra("diet");

        int minIndex = diet.getSuggestionMinIndex();
        int maxIndex = diet.getSuggestionMaxIndex();

        mAboutButton = (Button) findViewById(R.id.aboutButton);
        mAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SuggestionActivity.this, AboutActivity.class);
                intent.putExtra("diet", diet);
                startActivity(intent);
                finish();
            }
        });

        mSuggestionChart = findViewById(R.id.suggestionChart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, diet.get_total_user_co2_emission()));
        entries.add(new BarEntry(1, 1500f));
        entries.add(new BarEntry(2, diet.get_total_user_co2_emission()-3*calculateSavingAmountCarbon()));

        mReduceSuggestionText = findViewById(R.id.reduceSuggestionText);
        float totalSave = calculateSavingAmountCarbon();
        printSuggestion(minIndex, maxIndex, totalSave);
        mReduceSuggestionText.setText(diet.getIngName(maxIndex));

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


    }

    public float calculateSavingAmountCarbon() {
        float difference;

        int maxIndex = diet.getSuggestionMaxIndex();
        int minIndex = diet.getSuggestionMinIndex();

        difference = (float) (diet.getIngCarbon(maxIndex) - diet.getIngCarbon(minIndex));

        return difference;
    }
    public void printSuggestion(int minIndex, int maxIndex, float difference) {
        carbon = findViewById(R.id.carbonSaved);
        carbon.setText(difference*2463 + " tons");
        tree = findViewById(R.id.treesSaved);
        tree.setText(Math.round(difference/22 *2463000)/1000 + " thousand trees");
    }


    public double calculateCarbonEquivalent() {
        double total = calculateSavingAmountCarbon();
        int treeAbsorbAnnually = 22;
        return total / treeAbsorbAnnually;
    }

    public double calculateSavingInMetroVan() {
        double total = calculateSavingAmountCarbon();
        double nonVegetarians = 0.9 * 2463000;
        return total * nonVegetarians;
    }



}



//    TextView mReduceSuggestionText;
//    TextView carbon;
//    TextView tree;
//    private float userCarbon;
//    private float suggestedCarbon;


//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_result);
//
//        diet = (Diet)getIntent().getSerializableExtra("diet");
//
//        userCarbon = diet.get_total_user_co2_emission(); //insert calculated carbon in tC02e
//        suggestedCarbon = 1200f; // insert suggested carbon here
//        int minIndex = diet.getSuggestionMinIndex();
//        int maxIndex = diet.getSuggestionMaxIndex();
//
//        float totalSave = calculateSavingAmountCarbon();
//        printSuggestion(minIndex, maxIndex, totalSave);
//        suggestedCarbon = 1200f; // insert suggested carbon here
//
//        mResultText = findViewById(R.id.resultText);
//        mReduceSuggestionText = findViewById(R.id.reduceSuggestionText);
//
//        if (userCarbon < averageCarbon*lowCarbonPercentage) {
//            mResultText.setText(R.string.low_carbon_result);
//        } else if (userCarbon < averageCarbon*averageCarbonPercentage) {
//            mResultText.setText(R.string.average_carbon_result);
//        } else {
//            mResultText.setText(R.string.high_carbon_result);
//        }
//
//        mResultChart = findViewById(R.id.resultChart);
//        setUpHorizontalBarChart(mResultChart, averageCarbon, userCarbon);
//
//        mReduceSuggestionText.setText(diet.getIngName(maxIndex));
//
//
//        mSuggestionChart = findViewById(R.id.suggestionChart);
//        setUpHorizontalBarChart(mSuggestionChart, suggestedCarbon, userCarbon);
//
//        mUserEmissionSplitChart = findViewById(R.id.emissionSplitChart);
//        setupPieChart(mUserEmissionSplitChart);
//
//
//    }
//
//    private void setUpHorizontalBarChart(HorizontalBarChart chart, float valueOne, float valueTwo) {
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(0, valueOne));
//        entries.add(new BarEntry(1, valueTwo));
//
//        BarDataSet barDataSet = new BarDataSet(entries, "BarDataSet");
//        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
//
//        BarData suggestionData = new BarData(barDataSet);
//        chart.getXAxis().setDrawGridLines(false);
//        chart.getLegend().setEnabled(false);
//        chart.getAxisRight().setAxisMinimum(0f);
//        chart.getAxisLeft().setAxisMinimum(0f);
//
//        chart.setData(suggestionData);
//        chart.animateY(1200);
//        chart.invalidate();
//    }
//
//
//    public void printSuggestion(int minIndex, int maxIndex, float difference) {
//        carbon = findViewById(R.id.carbonSaved);
//        carbon.setText(difference*2463 + " tons");
//        tree = findViewById(R.id.treesSaved);
//        tree.setText(Math.round(difference/22 *2463000)/1000 + " thousand trees");
//    }
//
//
//    public float calculateSavingAmountCarbon() {
//        float difference;
//
//        int maxIndex = diet.getSuggestionMaxIndex();
//        int minIndex = diet.getSuggestionMinIndex();
//
//        difference = (float) (diet.getIngCarbon(maxIndex) - diet.getIngCarbon(minIndex));
//
//        return difference;
//    }
//
//    public double calculateCarbonEquivalent() {
//        double total = calculateSavingAmountCarbon();
//        int treeAbsorbAnnually = 22;
//        return total / treeAbsorbAnnually;
//    }
//
//    public double calculateSavingInMetroVan() {
//        double total = calculateSavingAmountCarbon();
//        double nonVegetarians = 0.9 * 2463000;
//        return total * nonVegetarians;
//    }
//
//}
