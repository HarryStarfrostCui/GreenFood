package com.example.hca127.greenfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class SuggestionActivity extends AppCompatActivity {
    BarChart mSuggestionChart;
    Button mAboutButton;
    Diet diet;
    TextView mReduceSuggestionText;
    TextView mCarbonSaved;
    TextView mTreesSaved;

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
        entries.add(new BarEntry(0, diet.getUserDietEmission()));
        entries.add(new BarEntry(1, 1500f));
        entries.add(new BarEntry(2, diet.getSuggestedDietEmission()));


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
        mCarbonSaved = findViewById(R.id.carbonSaved);
        mCarbonSaved.setText(difference*2463 + " tons");
        mTreesSaved = findViewById(R.id.treesSaved);
        mTreesSaved.setText(Math.round(difference/22 *2463000)/1000 + " thousand trees");
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



