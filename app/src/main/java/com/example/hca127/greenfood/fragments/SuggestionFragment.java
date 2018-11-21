package com.example.hca127.greenfood.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hca127.greenfood.MainActivity;
import com.example.hca127.greenfood.R;
import com.example.hca127.greenfood.objects.Diet;
import com.example.hca127.greenfood.objects.LocalUser;
import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareDialog;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class SuggestionFragment extends Fragment {
    private BarChart mSuggestionChart;
    private Diet mDiet;
    private Button mPledgeButton;
    private TextView mReduceSuggestionText;
    private LocalUser mLocalUser;
    private RadioGroup mPledgeRadioGroup;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        callbackManager = CallbackManager.Factory.create();
        final View view = inflater.inflate(R.layout.fragment_suggestion, container, false);
        final Bitmap mLogoImage = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.logo_share);

        mLocalUser = ((MainActivity)getActivity()).getLocalUser();
        mDiet = ((MainActivity)getActivity()).getLocalUserDiet();

        mPledgeButton = view.findViewById(R.id.pledge_button);
        mPledgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(mLocalUser.getUserId().equals("")){
                    Toast.makeText(getActivity(), "Area Only Opens For Logged-in Users",Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new LoginFragment()).addToBackStack(null).commit();
                }else {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new PledgeFragment()).addToBackStack(null).commit();
                }
            }
        });
        shareDialog = new ShareDialog(this);

        mReduceSuggestionText = view.findViewById(R.id.reduceSuggestionText);



        mSuggestionChart = view.findViewById(R.id.suggestionChart);
        setupBarChart(mSuggestionChart, 0);

        mPledgeRadioGroup = view.findViewById(R.id.pledgeRadioGroup);
        mPledgeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String choice = "a little";
                String temp = "";
                switch (checkedId) {
                    case R.id.pledge3:
                        choice = "a ton";
                        temp = String.format(getResources().getString(R.string.suggestion_text),
                                choice,
                                "to do",
                                "TO DO");
                        mReduceSuggestionText.setText(temp);
                        setupBarChart(mSuggestionChart, mDiet.getSuggestedDietEmission());
                        break;
                    case R.id.pledge2:
                        choice = "a decent amount";
                        temp = String.format(getResources().getString(R.string.suggestion_text),
                                choice,
                                "to do",
                                "TO DO");
                        mReduceSuggestionText.setText(temp);
                        setupBarChart(mSuggestionChart, mDiet.getSuggestedDietEmission());
                        break;
                    case R.id.pledge1:
                        choice = "a little";
                        setupBarChart(mSuggestionChart, mDiet.getSuggestedDietEmission());
                        temp = String.format(getResources().getString(R.string.suggestion_text),
                                choice,
                                mDiet.getFoodName(mDiet.getSuggestionMaxIndex()),
                                mDiet.getFoodName(mDiet.getSuggestionMinIndex()));
                        mReduceSuggestionText.setText(temp);
                        break;
                    case R.id.pledge0:
                        mReduceSuggestionText.setText("To save nothing\n Do nothing : )");
                        setupBarChart(mSuggestionChart, 0);
                        break;
                }

            }
        });


        float carbonSaved = mDiet.getSuggestedDietSavingAmount() *.9f * 2463000f / 1000;
        float treesSaved = carbonSaved/22;  // carbon offset of trees


        return view;
    }

    private void setupBarChart(BarChart chart, float suggestedDiet) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, mDiet.getUserDietEmission()));
        entries.add(new BarEntry(1, 1500f));
        if (suggestedDiet != 0) {
            entries.add(new BarEntry(2, suggestedDiet));
        }


        BarDataSet barDataSet = new BarDataSet(entries, "BarDataSet");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData suggestionData = new BarData(barDataSet);
        chart.getXAxis().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setAxisMinimum(0f);
        chart.getDescription().setEnabled(false);

        chart.setData(suggestionData);
        chart.animateY(1200);
        chart.invalidate();
    }



}

