package com.example.hca127.greenfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class MenuActivity extends AppCompatActivity {

    private ImageView addFood;
    private ImageView community;
    private ImageView about;
    private ImageView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        addFood = (ImageView) findViewById(R.id.add_food_button_menu);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AddingFoodActivity.class);
                startActivity(intent);
            }
        });

        community = (ImageView) findViewById(R.id.community_button_menu);
        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CommunityActivity.class);
                startActivity(intent);
            }
        });

        about = (ImageView) findViewById(R.id.about_button_menu);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        login = (ImageView) findViewById(R.id.login_button_menu);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
