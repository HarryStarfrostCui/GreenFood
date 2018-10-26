package com.example.hca127.greenfood;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    TextView mResultText;
    TextView mSuggestionText;
    private float userCarbon;
    private float suggestedCarbon;
    private float averageCarbon = 1500f;
    private float lowCarbonPercentage = 0.9f;
    private float averageCarbonPercentage = 1.1f;


    private ArrayList<Ingredient> basket;
    private ArrayList<Ingredient> suggestionResult = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle extras = getIntent().getExtras();
        basket = (ArrayList<Ingredient>) getIntent().getSerializableExtra("basket");
        for (int i = 0; i < basket.size(); i++) {
            userCarbon += basket.get(i).getUser_co2_emission();
        }

        int minIndex = getSuggestionMinIndex();
        int maxIndex = getSuggestionMaxIndex();
        float totalSave = calculateSavingAmountCarbon();
        printSuggestion(minIndex, maxIndex, totalSave);
        suggestedCarbon = 1200f; // insert suggested carbon here

        mResultText = findViewById(R.id.resultText);
        //mSuggestionText = findViewById(R.id.suggestionText);

        if (userCarbon < averageCarbon * lowCarbonPercentage) {
            mResultText.setText(R.string.low_carbon_result);
        } else if (userCarbon < averageCarbon * averageCarbonPercentage) {
            mResultText.setText(R.string.average_carbon_result);
        } else {
            mResultText.setText(R.string.high_carbon_result);
        }

        mResultChart = findViewById(R.id.resultChart);
        setUpHorizontalBarChart(mResultChart, averageCarbon, userCarbon);

        //mSuggestionText.setText(R.string.temp_text);

        mSuggestionChart = findViewById(R.id.suggestionChart);
        setUpHorizontalBarChart(mSuggestionChart, averageCarbon, suggestedCarbon);

    }

    private void setUpHorizontalBarChart(HorizontalBarChart chart, float valueOne, float valueTwo) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, valueOne));
        entries.add(new BarEntry(1, valueTwo));

        BarDataSet barDataSet = new BarDataSet(entries, "BarDataSet");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData suggestionData = new BarData(barDataSet);
        chart.getXAxis().setDrawGridLines(false);

        chart.setData(suggestionData);
        chart.animateY(1200);
        chart.invalidate();
    }

    public ArrayList<Integer> getFavList()
    {
        ArrayList<Double> favourite = new ArrayList<>();
        ArrayList<Integer> index = new ArrayList<>();
        for (int i = 0; i < basket.size(); i++)
        {
            if(basket.get(i).getUser_consumption()>0.2){
                double temp = Math.round(basket.get(i).getUser_consumption()*100)/100;
                favourite.add(temp);
                index.add(i);
            }
        }

        double temp;
        for (int i = 1; i < favourite.size()-1; i++)
        {
            for (int j = i-1; j >= 0; j--)
            {
                if (favourite.get(j) < favourite.get(j+1))
                {
                    temp = favourite.get(j);
                    favourite.set(j, favourite.get(j+1));
                    favourite.set(j+1, temp);
                    temp = index.get(j);
                    index.set(j, index.get(j+1));
                    index.set(j+1, (int)temp);
                }
                else
                {
                    break;
                }
            }
        }
        return index;
    }

    public int getSuggestionMinIndex()
    {
        ArrayList<Integer> favourite = getFavList();
        int index = favourite.get(0);
        double current, temp;

        for(int i = 1; i < favourite.size(); i++)
        {
            current = basket.get(index).getCarbon_coefficient()*basket.get(index).getUser_co2_emission();
            temp = basket.get(favourite.get(i)).getCarbon_coefficient()*basket.get(favourite.get(i)).getUser_co2_emission();
            if (temp < current)
            {
                index = favourite.get(i);
            }
        }
        return index;
    }

    public int getSuggestionMaxIndex()
    {
        ArrayList<Integer> favourite = getFavList();
        int index = favourite.get(0);
        double current, temp;

        for(int i = 1; i < favourite.size(); i++)
        {
            current = basket.get(index).getCarbon_coefficient()*basket.get(index).getCarbon_coefficient()*basket.get(index).getUser_co2_emission();
            temp = basket.get(favourite.get(i)).getCarbon_coefficient()*basket.get(favourite.get(i)).getCarbon_coefficient()*basket.get(favourite.get(i)).getUser_co2_emission();
            if (temp > current)
            {
                index = favourite.get(i);
            }
        }
        return index;
    }


    public void printSuggestion(int minIndex, int maxIndex, float difference) {
        TextView suggestionTextView = (TextView) findViewById(R.id.suggestionText);
        suggestionTextView.setText(getString(R.string.suggestionResult, suggestionResult.get(minIndex).getFoodName(),
                suggestionResult.get(maxIndex).getFoodName(),
                difference, difference/22));
    }


    public float calculateSavingAmountCarbon() {
        float difference;
        for (int i = 0; i < basket.size(); i++) {
            suggestionResult.add(i, basket.get(i));
        }

        int maxIndex = getSuggestionMaxIndex();
        int minIndex = getSuggestionMinIndex();

        difference = (float) (suggestionResult.get(maxIndex).getCarbon_coefficient() - suggestionResult.get(minIndex).getCarbon_coefficient());

        return difference;
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
