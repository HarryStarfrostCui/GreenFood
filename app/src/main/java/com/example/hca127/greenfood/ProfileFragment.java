package com.example.hca127.greenfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    private ImageView pencil;
    private TextView display_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_profile, container, false);

        SharedPreferences google_stuff = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String name = google_stuff.getString("temp","");
        display_name = (TextView) view.findViewById(R.id.display_name);
        display_name.setText(name);
        pencil = (ImageView) view.findViewById(R.id.edit_display_name_button);
        pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_name.setCursorVisible(true);
                display_name.setFocusableInTouchMode(true);
                display_name.setInputType(InputType.TYPE_CLASS_TEXT);
                display_name.requestFocus();
            }
        });

        return view;
    }
}
