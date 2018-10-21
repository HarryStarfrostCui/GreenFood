package com.example.hca127.greenfood;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class PopUpActivity extends Activity {

    private int integer = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        //change the size of the layout when pop up
        getWindow().setLayout((int)(width*.4), (int)(height*.05));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;

        //setting where to show the pop up window. The (x,y) of pop up window
        //x negative and smaller will move left
        //y positive and larger will move downward
        params.x = -265;
        params.y = 300;
        getWindow().setAttributes(params);
    }


    int chickenAmount = ((tempFoodAmount) this.getApplication()).getChickenAmount();

    private void display(int chickenAmount)
    {
        TextView displayInteger = findViewById(R.id.integerNumber);
        displayInteger.setText(String.valueOf(chickenAmount));
    }

    public void increaseInteger(View view)
    {
        chickenAmount++;
        display(chickenAmount);
    }

    public void decreaseInteger(View view)
    {
        chickenAmount--;
        display(chickenAmount);
    }


}
