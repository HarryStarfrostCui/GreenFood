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
    private BarChart mSuggestionChart;
    private Button mAboutButton;
    private Diet mDiet;
    private TextView mReduceSuggestionText;
    private TextView mIncreaseSuggestionText;
    private TextView mUserEmissionSaving;
    private TextView mCarbonSaved;
    private TextView mTreesSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        mDiet = (Diet)getIntent().getSerializableExtra("diet");

        mReduceSuggestionText = findViewById(R.id.reduceSuggestionText);
        mReduceSuggestionText.setText(mDiet.getFoodName(mDiet.getSuggestionMaxIndex()));

        mIncreaseSuggestionText = findViewById(R.id.increaseSuggestionText);
        mIncreaseSuggestionText.setText(mDiet.getFoodName(mDiet.getSuggestionMinIndex()));

        mUserEmissionSaving = findViewById(R.id.userEmissionSaving);
        mUserEmissionSaving.setText(String.valueOf(mDiet.getSuggestedDietSavingAmount()));

        mSuggestionChart = findViewById(R.id.suggestionChart);

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

        mCarbonSaved = findViewById(R.id.carbonSaved);
        mCarbonSaved.setText(String.valueOf(carbonSaved));

        mTreesSaved = findViewById(R.id.treesSaved);
        mTreesSaved.setText(String.valueOf(treesSaved));

        mAboutButton = (Button) findViewById(R.id.aboutButton);
        mAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SuggestionActivity.this, AboutActivity.class);
                intent.putExtra("diet", mDiet);
                startActivity(intent);
                finish();
            }
        });
    }
}



