package com.example.hca127.greenfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CommunityActivity extends AppCompatActivity {

    private ImageView about_button;
    private ImageView pledge_button;
    private TextView temp;
    private String name;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        /*Bundle google_account_info = getIntent().getExtras();
        name = google_account_info.get("name").toString();*/

        SharedPreferences google_account_info = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        name = google_account_info.getString("temp","");
        email = google_account_info.getString("email","");

        temp = findViewById(R.id.number_of_participants);
        temp.setText(name);


        about_button = (ImageView) findViewById(R.id.about_button_in_community_activity);
        about_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommunityActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        pledge_button = (ImageView) findViewById(R.id.pledge_button);
        pledge_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommunityActivity.this, PledgeActivity.class);
                startActivity(intent);
            }
        });
    }


}
