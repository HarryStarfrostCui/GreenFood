package com.example.hca127.greenfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private ArrayList<Integer> basket;
    private ArrayList<IngredientList> suggestionResult = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle extras = getIntent().getExtras();
        basket = extras.getIntegerArrayList("basket");
        Toast.makeText(ResultActivity.this,
                "its " +   basket.get(0).toString() + " "  + basket.get(1).toString() + " " + basket.get(2).toString() + " "  +
                                basket.get(3).toString() + " "  + basket.get(4).toString() + " " + basket.get(5).toString() + " "  +
                                basket.get(6).toString() + " "  + basket.get(7).toString(),
                Toast.LENGTH_LONG).show();
        setupStandard(array);
        getSuggestion(array);
        printSuggestion();
    }

    //check if array.get(i).getCarbon is for year, if not, remember to time the date of year
    public ArrayList<IngredientList> setupStandard(ArrayList<IngredientList> array)
    {
        ArrayList<IngredientList> standard = new ArrayList<>();
        for (int i = 0; i < array.size(); i++)
        {
            standard.get(i).addIng(array.get(i).getName(i), 225* 0.5 *array.get(i).getCarbon(i)/1000.0);
        }
        return standard;
    }

    //in this function, suggestionResult to store the result of all type food suggestions
    //in for loop, if consume is higher than standard suggest standard amount
    //else if consume equal to standard, give next food standard amount
    //else set suggestion to NULL and do not show it in result
    public void getSuggestion(ArrayList<IngredientList> array)
    {
        ArrayList<IngredientList> standard;
        //double standardAmount;
        standard = setupStandard(array);
        for (int i = 0; i < array.size(); i++)
        {
            if (array.get(i).getUserConsumption() > standard.get(i).getCarbon(i))
            {
                //standardAmount =  (standard.get(i).getCarbon(i)*1000)/array.get(i).getCarbon(i);
                suggestionResult.get(i).addIng(standard.get(i).getName(i), standard.get(i).getCarbon(i));
            }
            else if (array.get(i).getUserConsumption() == standard.get(i).getCarbon(i))
            {
                if (i+1 == array.size())
                {
                    suggestionResult.get(i).addIng(standard.get(i).getName(i), standard.get(i).getCarbon(i));
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

        if(index==initual){
            return index+1;
        }
        return index;
    }
*/
}
