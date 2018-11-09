package com.example.hca127.greenfood.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hca127.greenfood.MainActivity;
import com.example.hca127.greenfood.R;
import com.example.hca127.greenfood.objects.Diet;
import com.example.hca127.greenfood.objects.LocalUser;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class PledgeFragment extends Fragment {
    private RadioGroup mPledgeChoiceRadioGroup;
    private Integer mPledgeChoiceButton;
    private Button mPledgeButton;
    private LocalUser mLocalUser;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pledge, container, false);

        mLocalUser = ((MainActivity)getActivity()).getLocalUser();

        mPledgeButton = view.findViewById(R.id.pledge_button);
        mPledgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPledgeChoiceRadioGroup = view.findViewById(R.id.pledge_radio_group);
                mPledgeChoiceButton = mPledgeChoiceRadioGroup.getCheckedRadioButtonId();

                String mChoice;
                String mLevel;

                mChoice = getResources().getResourceEntryName(mPledgeChoiceButton);
                mLevel = mChoice.substring(mChoice.length()-1, mChoice.length());

                mLocalUser.setPledge(Double.parseDouble(mLevel));
                Toast.makeText(getActivity(), mLevel, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }

}
