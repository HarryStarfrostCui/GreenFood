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
import com.facebook.share.widget.DeviceShareButton;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PledgeFragment extends Fragment {
    private RadioGroup mPledgeChoiceRadioGroup;
    private Integer mPledgeChoiceButton;
    private Button mPledgeButton;
    private Button mFacebookPledgeButton;
    private LocalUser mLocalUser;
    private String mLevel;

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
                final String mLevel;
                mChoice = getResources().getResourceEntryName(mPledgeChoiceButton);
                mLevel = mChoice.substring(mChoice.length()-1, mChoice.length());

                DatabaseReference community = FirebaseDatabase.getInstance().getReference().child("Community pledge");
                community.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        double totalTemp = (double)dataSnapshot.child("total").child("pledge").getValue();
                        double cityTemp = (double)dataSnapshot.child(String.valueOf(mLocalUser.getCity())).child("pledge").getValue();
                        Toast.makeText(getContext(),String.valueOf(mLocalUser.getPledge()),Toast.LENGTH_SHORT).show();
                        totalTemp = totalTemp - mLocalUser.getPledge();
                        cityTemp = cityTemp - mLocalUser.getPledge();
                        mLocalUser.setPledgeByIndex(Integer.parseInt(mLevel));
                        totalTemp += mLocalUser.getPledge();
                        cityTemp += mLocalUser.getPledge();
                        dataSnapshot.child("total").child("pledge").getRef().setValue(totalTemp);
                        dataSnapshot.child(String.valueOf(mLocalUser.getCity())).child("pledge").getRef().setValue(cityTemp);
                        ((MainActivity)getActivity()).setLocalUser(mLocalUser);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CommunityFragment()).commit();

            }
        });

        mFacebookPledgeButton = view.findViewById(R.id.share_pledge_button);
        mFacebookPledgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPledgeChoiceRadioGroup = view.findViewById(R.id.pledge_radio_group);
                mPledgeChoiceButton = mPledgeChoiceRadioGroup.getCheckedRadioButtonId();

                String mChoice;
                String mLevel;

                mChoice = getResources().getResourceEntryName(mPledgeChoiceButton);
                mLevel = mChoice.substring(mChoice.length()-1, mChoice.length());

                mLocalUser.setPledgeByIndex(Integer.parseInt(mLevel));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FacebookShareFragment()).commit();


            }
        });

        return view;
    }

}
