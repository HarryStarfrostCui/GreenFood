package com.example.hca127.greenfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    //check if array.get(i).getCarbon is for year, if not, remember to time the date of year
   /* public ArrayList<Double> setupStandard()
    {
        ArrayList<Double> standard = new ArrayList<>();
        for (int i = 0; i < basket.size(); i++)
        {
            standard.get(i) = basket.get(i).getStandard_co2_emission();
        }
        return standard;
    }*/

    public void sortArrayListBasedOnCarbon_coefficient() {
        for (int i = 0; i < basket.size(); i++) {
            for (int j = i + 1; j < basket.size(); j++) {
                if (basket.get(i).getCarbon_coefficient() < basket.get(j).getCarbon_coefficient()) {
                    Ingredient temp = basket.get(i);
                    basket.set(i, basket.get(j));
                    basket.set(j, temp);
                }
            }
        }
    }


    //in this function, suggestionResult to store the result of all type food suggestions
    //in for loop, if consume is higher than standard suggest standard amount
    //else if consume equal to standard, give next food standard amount
    //else set suggestion to NULL and do not show it in result
    public void getSuggestion() {
        sortArrayListBasedOnCarbon_coefficient();
        for (int i = 0; i < basket.size(); i++) {
            if (basket.get(i).getUser_co2_emission() > basket.get(i).getStandard_co2_emission()) {
                basket.get(i).setupStandard();
                suggestionResult.get(i).setFoodName(basket.get(i).getFoodName());
                //if I don\t call the constructor, will the variable be setting up?
                suggestionResult.get(i).setUser_co2_emission(basket.get(i).getStandard_co2_emission());
            } else if (basket.get(i).getUser_co2_emission() == basket.get(i).getStandard_co2_emission()) {
                basket.get(i).setupStandard();
                if (i + 1 == basket.size()) {
                    suggestionResult.get(i).setFoodName(basket.get(i).getFoodName());
                    suggestionResult.get(i).setUser_co2_emission(basket.get(i).getStandard_co2_emission() * 0.4);
                    break;
                }
                //if food 1 is equal, then give food2, if food 2 is greater, give food2 as well. This will be a problem
                double saved_co2_emission = basket.get(i + 1).getCarbon_coefficient() * basket.get(i).getAverage_consumption() * basket.get(i).getUser_consumption();
                suggestionResult.get(i).setFoodName(basket.get(i + 1).getFoodName());
                suggestionResult.get(i).setUser_co2_emission(saved_co2_emission);
            } else {
                suggestionResult.get(i).setFoodName("");
            }
        }
        //removeDuplicate(suggestionResult);
    }

    /*private void removeDuplicate(ArrayList<IngredientList> suggestionResult) {
        double count = 0.0;
        for (int i = 0; i < suggestionResult.size(); i++) {
            for (int j = i + 1; j < suggestionResult.size(); j++) {
                if (suggestionResult.get(i).getName(i) == suggestionResult.get(j).getName(j)) {
                    count = count + suggestionResult.get(j).getCarbon(j);
                    suggestionResult.get(j).addIng("", 0);
                }
            }
            count = count + suggestionResult.get(i).getCarbon(i);
            suggestionResult.get(i).addIng(suggestionResult.get(i).getName(i), count);
        }
    }*/

    public void printSuggestion() {
        TextView suggestionTextView = (TextView) findViewById(R.id.suggestionText);
        for (int i = 0; i < suggestionResult.size(); i++)
        {
            for (int j = i+1; j < suggestionResult.size(); j++)
            {
                if(suggestionResult.get(i).getFoodName().equals(suggestionResult.get(j).getFoodName()))
                {
                    //suggestionResult.get(i).setUser_co2_emission();
                }
            }
            if (suggestionResult.get(i).getFoodName().equals(""))
            {
                continue;
            }
            else
            {
                suggestionTextView.setText(getString());
            }
        }
    }

    public double calculateSavingAmountCarbon() {
        double difference;
        double total = 0.0;
        for (int i = 0; i < basket.size(); i++) {
            difference = basket.get(i).getUser_co2_emission() - suggestionResult.get(i).getUser_co2_emission();
            total += difference;
        }
        return total;
    }

    public double calculateCarbonEquivalent() {
        double total = calculateSavingAmountCarbon();
        int treeAbsorbAnnually = 22;
        return total / treeAbsorbAnnually;
    }

    public double calcultateSavingInMetroVan() {
        double total = calculateSavingAmountCarbon();
        double nonVegetarians = 0.9 * 2463000;
        return total * nonVegetarians;
    }
}
