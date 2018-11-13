package com.example.hca127.greenfood.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hca127.greenfood.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CommunityFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private ImageView mabout;
    private Spinner mCitySpinner;
    private TextView mParticipantDisplay, mReducedDisplay, mTreesDisplay, mAverageDisplay;
    private String mCurrentCity;
    private DatabaseReference mDatabase;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mCitySpinner = view.findViewById(R.id.community_cityList);
        mCitySpinner.setOnItemSelectedListener(this);
        mCurrentCity = "total";
        mCitySpinner.setSelection(0);
        mProgressBar = view.findViewById(R.id.community_progress_bar);

        mParticipantDisplay = (TextView) view.findViewById(R.id.community_pledge_participant);
        mReducedDisplay = (TextView)view.findViewById(R.id.community_pledge_total);
        mTreesDisplay = (TextView)view.findViewById(R.id.community_pledge_trees);
        mAverageDisplay = (TextView)view.findViewById(R.id.community_pledge_average);

        SharedPreferences google_account_info = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String name = google_account_info.getString("name","");
        String email = google_account_info.getString("email","");

        mabout = (ImageView) view.findViewById(R.id.about_button_community);
        mabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AboutFragment()).addToBackStack(null).commit();
                NavigationView navigationView = getActivity().findViewById(R.id.navigation_view);
                navigationView.setCheckedItem(R.id.menu_about);
            }
        });
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mProgressBar.setVisibility(View.VISIBLE);
        if(position==0){
            mCurrentCity = "total";
        }else {
            mCurrentCity = Integer.toString(position);
        }
        DatabaseReference community = mDatabase.child("Community pledge").child(mCurrentCity);
        community.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int numOParticipant = (int)(long)dataSnapshot.child("participant").getValue();
                String toBDisplayed = String.valueOf(numOParticipant);
                Toast.makeText(getContext(),String.valueOf(numOParticipant),Toast.LENGTH_SHORT).show();
                mParticipantDisplay.setText(toBDisplayed);
                double pledged = (double)dataSnapshot.child("pledge").getValue();
                mParticipantDisplay.setText(String.valueOf(Math.round(pledged*100)/100));
                mAverageDisplay.setText(String.valueOf(Math.round(pledged/numOParticipant*100)/100));
                mTreesDisplay.setText(String.valueOf(Math.round(pledged/22*100)/100));
                mProgressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        DatabaseReference users = mDatabase.child("users");
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
