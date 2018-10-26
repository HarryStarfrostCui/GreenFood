package com.example.hca127.greenfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class SuggestionActivity extends AppCompatActivity {
    BarChart mSuggestionChart;
    Button mAboutButton;
    Diet mDiet;
    TextView mReduceSuggestionText;
    TextView mCarbonSaved;
    TextView mTreesSaved;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        mDiet = (Diet)getIntent().getSerializableExtra("diet");

        int minIndex = mDiet.getSuggestionMinIndex();
        int maxIndex = mDiet.getSuggestionMaxIndex();

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

        mSuggestionChart = findViewById(R.id.suggestionChart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, mDiet.getUserDietEmission()));
        entries.add(new BarEntry(1, 1500f));
        entries.add(new BarEntry(2, mDiet.getSuggestedDietEmission()));


        mReduceSuggestionText = findViewById(R.id.reduceSuggestionText);
        mReduceSuggestionText.setText(mDiet.getIngName(maxIndex));

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
        float treesSaved = carbonSaved/22;


        mCarbonSaved = findViewById(R.id.carbonSaved);
        mTreesSaved = findViewById(R.id.treesSaved);
        mCarbonSaved.setText(String.valueOf(carbonSaved));
        mTreesSaved.setText(String.valueOf(treesSaved));
    }


}



