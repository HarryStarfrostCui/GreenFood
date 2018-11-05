package com.example.hca127.greenfood;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class PledgeActivity extends AppCompatActivity {

    private RadioGroup pledgeChoiceRadioGroup;
    private Integer pledgeChoiceButton;
    private ImageView pledge_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pledge);


        pledge_button = findViewById(R.id.pledge_button);
        pledge_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pledgeChoiceRadioGroup = findViewById(R.id.pledge_radio_group);
                pledgeChoiceButton = pledgeChoiceRadioGroup.getCheckedRadioButtonId();

                String mChoice;
                String mLevel;

                mChoice = getResources().getResourceEntryName(pledgeChoiceButton);
                mLevel = mChoice.substring(mChoice.length()-1, mChoice.length());

                SharedPreferences pledgeChoice = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = pledgeChoice.edit();
                editor.putString("pledge_amount", mLevel);
                editor.apply();

            }
        });



    }
}
