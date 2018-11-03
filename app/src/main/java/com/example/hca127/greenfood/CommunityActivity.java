package com.example.hca127.greenfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class CommunityActivity extends AppCompatActivity {

    private ImageView about_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

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
