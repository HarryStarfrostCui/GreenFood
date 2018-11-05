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
import android.widget.RadioGroup;
import android.widget.TextView;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class PledgeFragment extends Fragment {


    private RadioGroup pledgeChoiceRadioGroup;
    private Integer pledgeChoiceButton;
    private ImageView pledge_button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag_pledge, container, false);
        pledge_button = view.findViewById(R.id.pledge_button);
        pledge_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pledgeChoiceRadioGroup = view.findViewById(R.id.pledge_radio_group);
                pledgeChoiceButton = pledgeChoiceRadioGroup.getCheckedRadioButtonId();

                String mChoice;
                String mLevel;

                mChoice = getResources().getResourceEntryName(pledgeChoiceButton);
                mLevel = mChoice.substring(mChoice.length()-1, mChoice.length());

                SharedPreferences pledgeChoice = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = pledgeChoice.edit();
                editor.putString("pledge_amount", mLevel);
                editor.apply();

            }
        });

        return view;
    }
}
