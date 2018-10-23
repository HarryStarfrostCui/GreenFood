package com.example.hca127.greenfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private ArrayList<Integer> basket;

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

    }
}
