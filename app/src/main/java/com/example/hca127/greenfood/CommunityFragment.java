package com.example.hca127.greenfood;

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

public class CommunityFragment extends Fragment {

    private String name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_community, container, false);

        SharedPreferences google_account_info = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String name = google_account_info.getString("name","");
        String email = google_account_info.getString("email","");

        return view;
    }
}