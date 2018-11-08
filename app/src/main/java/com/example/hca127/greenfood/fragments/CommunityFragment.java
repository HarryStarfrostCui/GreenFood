package com.example.hca127.greenfood.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hca127.greenfood.R;

import com.google.gson.Gson;

public class CommunityFragment extends Fragment {

    private ImageView about;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        SharedPreferences google_account_info = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String name = google_account_info.getString("name","");
        String email = google_account_info.getString("email","");

        about = view.findViewById(R.id.about_button_community);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Intent intent = new Intent(getActivity(), AboutFragment.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
