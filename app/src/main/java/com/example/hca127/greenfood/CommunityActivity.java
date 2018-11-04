package com.example.hca127.greenfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class CommunityActivity extends AppCompatActivity {

    private ImageView about_button;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        /*Bundle google_account_info = getIntent().getExtras();
        name = google_account_info.get("name").toString();*/

        SharedPreferences google_account_info = PreferenceManager.getDefaultSharedPreferences(this);
        String name = google_account_info.getString("name","");
        String email = google_account_info.getString("email","");


        about_button = (ImageView) findViewById(R.id.about_button_community);
        about_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommunityActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }


}
