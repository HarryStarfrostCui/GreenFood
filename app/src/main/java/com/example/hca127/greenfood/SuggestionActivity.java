package com.example.hca127.greenfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hca127.greenfood.objects.Diet;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SuggestionActivity extends AppCompatActivity {
    private BarChart mSuggestionChart;
    private Button mAboutButton;
    private ImageButton mFacebookShare;
    private Diet mDiet;
    private TextView mReduceSuggestionText;
    private TextView mIncreaseSuggestionText;
    private TextView mUserEmissionSaving;
    private TextView mCarbonSaved;
    private TextView mTreesSaved;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_suggestion);

        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("mDiet", "");

        //mDiet = (Diet)getIntent().getSerializableExtra("diet");
        mDiet = gson.fromJson(json, Diet.class);


        mReduceSuggestionText = findViewById(R.id.reduceSuggestionText);
        mReduceSuggestionText.setText(mDiet.getFoodName(mDiet.getSuggestionMaxIndex()));

        mIncreaseSuggestionText = findViewById(R.id.increaseSuggestionText);
        mIncreaseSuggestionText.setText(mDiet.getFoodName(mDiet.getSuggestionMinIndex()));

        mUserEmissionSaving = findViewById(R.id.userEmissionSaving);
        mUserEmissionSaving.setText(String.valueOf(mDiet.getSuggestedDietSavingAmount()));

        mSuggestionChart = findViewById(R.id.suggestionChart);
        mFacebookShare = findViewById(R.id.facebookShare);
        shareDialog = new ShareDialog(this);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, mDiet.getUserDietEmission()));
        entries.add(new BarEntry(1, 1500f));
        entries.add(new BarEntry(2, mDiet.getSuggestedDietEmission()));

        BarDataSet barDataSet = new BarDataSet(entries, "BarDataSet");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData suggestionData = new BarData(barDataSet);
        mSuggestionChart.getXAxis().setDrawGridLines(false);
        mSuggestionChart.getLegend().setEnabled(false);
        mSuggestionChart.getAxisRight().setAxisMinimum(0f);
        mSuggestionChart.getAxisLeft().setAxisMinimum(0f);
        mSuggestionChart.getDescription().setEnabled(false);

        mSuggestionChart.setData(suggestionData);
        mSuggestionChart.animateY(1200);
        mSuggestionChart.invalidate();

        float carbonSaved = mDiet.getSuggestedDietSavingAmount() *.9f * 2463000f / 1000;
        float treesSaved = carbonSaved/22;  // carbon offset of trees

        if(carbonSaved < 0)
        {
            carbonSaved = 0;
            treesSaved = 0;
        }

        mCarbonSaved = findViewById(R.id.carbonSaved);
        mCarbonSaved.setText(String.valueOf(carbonSaved));

        mTreesSaved = findViewById(R.id.treesSaved);
        mTreesSaved.setText(String.valueOf(treesSaved));

        //share stuff
        mFacebookShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(SuggestionActivity.this,"share success!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(SuggestionActivity.this,"cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(SuggestionActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://cmpt276.sfu.rosielab.ca/project"))
                        .setQuote("testing")
                        .build();
                if (ShareDialog.canShow(ShareLinkContent.class))
                {
                    shareDialog.show(linkContent);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}



