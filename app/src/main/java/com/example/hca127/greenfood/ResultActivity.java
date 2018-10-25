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
    private ArrayList<Ingredient> suggestionResult = new ArrayList<>();

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

    //in this function, suggestionResult to store the result of all type food suggestions
    //in for loop, if consume is higher than standard suggest standard amount
    //else if consume equal to standard, give next food standard amount
    //else set suggestion to NULL and do not show it in result
    public void getSuggestion()
    {
        //ArrayList<IngredientList> standard;
        //double standardAmount;
        //standard = setupStandard(array);
        for (int i = 0; i < basket.size(); i++)
        {
            if (basket.get(i).getUser_co2_emission() > basket.get(i).getStandard_co2_emission())
            {
                suggestionResult.get(i).setFoodName(basket.get(i).getFoodName());
                basket.get(i).setupStandard();
                //if I don\t call the constructor, will the variable be setting up?
                suggestionResult.get(i).setUser_co2_emission(basket.get(i).getStandard_co2_emission());
            }
            else if (basket.get(i).getUser_co2_emission() == basket.get(i).getStandard_co2_emission())
            {
                if (i+1 == basket.size())
                {
                    suggestionResult.get(i).setFoodName(basket.get(i).getFoodName());
                    suggestionResult.get(i).setUser_co2_emission(basket.get(i+1).getUser_co2_emission());
                }
                //if food 1 is equal, then give food2, if food 2 is greater, give food2 as well. This will be a problem
                suggestionResult.get(i).addIng(standard.get(i+1).getName(i+1), standard.get(i+1).getCarbon(i+1));
            }
            else
            {
                suggestionResult.get(i).addIng("NULL", 0);
            }
        }
        removeDuplicate(suggestionResult);
    }

    private void removeDuplicate(ArrayList<IngredientList> suggestionResult) {
        double count = 0.0;
        for (int i = 0; i < suggestionResult.size(); i++)
        {
            for (int j = i+1; j < suggestionResult.size(); j++)
            {
                if (suggestionResult.get(i).getName(i) == suggestionResult.get(j).getName(j))
                {
                    count = count + suggestionResult.get(j).getCarbon(j);
                    suggestionResult.get(j).addIng("", 0);
                }
            }
            count = count + suggestionResult.get(i).getCarbon(i);
            suggestionResult.get(i).addIng(suggestionResult.get(i).getName(i), count);
        }
    }

    private ArrayList<Double> changeSuggestionCarbonToAmount(ArrayList<IngredientList> resultArray, ArrayList<IngredientList> array)
    {
        double carbon;
        ArrayList<Double> amount = new ArrayList<>();
        for (int i = 0; i < resultArray.size(); i++)
        {
            carbon = resultArray.get(i).getCarbon(i);
            amount.add(carbon*1000/array.get(i).getCarbon(i));
        }
        return amount;
    }

    public void printSuggestion()
    {
        ArrayList<Double> amount;
        amount = changeSuggestionCarbonToAmount(suggestionResult, array);
        TextView suggestionTextView = (TextView) findViewById(R.id.suggestion);
        suggestionTextView.setText(suggestionResult.get(0).getName(0) + amount.get(0).toString());
    }

    public double calculateSavingAmountCarbon(ArrayList<IngredientList>array)
    {
        double difference;
        double total = 0.0;
        for (int i = 0; i < array.size(); i++)
        {
            difference = array.get(i).getCarbon(i) - suggestionResult.get(i).getCarbon(i);
            total += difference;
        }
        return total;
    }

    public double calculateCarbonEquivalent()
    {
        double total = calculateSavingAmountCarbon(array);
        int treeAbsorbAnnually = 22;
        return total/treeAbsorbAnnually;
    }

    public double calcultateSavingInMetroVan()
    {
        double total = calculateSavingAmountCarbon(array);
        double nonVegetarians = 0.9*2463000;
        return total * nonVegetarians;
    }
  /*
    public int getSuggestionIndex() {  //later can be use for suggestion
        ArrayList<Integer> favourite = getFavList();
        int index = favourite.get(0);
        int initual = index;

        double current, temp;
        for(int i = 1; i<favourite.size();i++){
            current = plate.get(index).getCarbon_coefficient() * plate.get(index).getEmit();
            temp = plate.get(favourite.get(i)).getCarbon_coefficient() * plate.get(favourite.get(i)).getEmit();
            if(temp < current){
                index = i;
            }
        }

    }
}
