package com.example.hca127.greenfood.fragments;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hca127.greenfood.MainActivity;
import com.example.hca127.greenfood.R;
import com.example.hca127.greenfood.objects.LocalUser;

public class ProfileFragment extends Fragment {

    private ImageView name_pencil;
    private ImageView mNameCheck;
    private EditText display_name;
    private TextView email;
    private Spinner city_choice;
    private ImageView city_check;
    private ImageView city_pencil;

    private LocalUser mLocalUser;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mLocalUser = ((MainActivity)getActivity()).getLocalUser();

        mNameCheck = view.findViewById(R.id.edit_display_name_button_check);
        mNameCheck.setVisibility(ImageView.GONE);

        city_check = view.findViewById(R.id.edit_city_spinner_check);
        city_check.setVisibility(ImageView.GONE);

        city_choice = view.findViewById(R.id.edit_city_spinner_choice);
        city_choice.setEnabled(false);



        final SharedPreferences google_stuff = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String google_name = google_stuff.getString("account_name","");
        String google_email = google_stuff.getString("account_email","");
        int city = google_stuff.getInt("city_choice",0);
        city_choice.setSelection(city);
        display_name = (EditText) view.findViewById(R.id.display_name);
        display_name.setText(google_name);
        display_name.setEnabled(false);
        email = (TextView) view.findViewById(R.id.email);
        email.setText(google_email);


        name_pencil = (ImageView) view.findViewById(R.id.edit_display_name_button);
        name_pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_name.setEnabled(true);
                display_name.setCursorVisible(true);
                display_name.setFocusableInTouchMode(true);
                display_name.setInputType(InputType.TYPE_CLASS_TEXT);
                display_name.requestFocus();
                name_pencil.setVisibility(ImageView.GONE);
                mNameCheck.setVisibility(ImageView.VISIBLE);
            }
        });

        mNameCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_name.setEnabled(false);
                String newName = display_name.getText().toString();
                SharedPreferences.Editor editor = google_stuff.edit();
                editor.putString("google_account_name",newName);
                editor.apply();

                mLocalUser.setFirstName(newName);
                ((MainActivity)getActivity()).setLocalUser(mLocalUser);

                mNameCheck.setVisibility(ImageView.GONE);
                name_pencil.setVisibility(ImageView.VISIBLE);
            }
        });


        city_pencil = (ImageView) view.findViewById(R.id.edit_city_spinner_button);
        city_pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city_choice.setEnabled(true);
                city_pencil.setVisibility(ImageView.GONE);
                city_check.setVisibility(ImageView.VISIBLE);

                /*city_choice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int city = Integer.parseInt(city_choice.getItemAtPosition(position).toString());
                        final SharedPreferences.Editor editor = google_stuff.edit();
                        editor.putInt("city_choice",city);
                        editor.apply();
                    }
                });*/

            }
        });

        city_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city_choice.setEnabled(false);
                city_pencil.setVisibility(ImageView.VISIBLE);
                city_check.setVisibility(ImageView.GONE);
                int city = city_choice.getSelectedItemPosition();
                SharedPreferences.Editor editor = google_stuff.edit();
                editor.putInt("city_choice",city);
                editor.apply();
            }
        });

        return view;
    }
}
