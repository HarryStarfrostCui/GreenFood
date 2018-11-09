package com.example.hca127.greenfood.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
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

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private ImageView name_pencil;
    private ImageView mNameCheck;
    private EditText mDisplayName;
    private TextView mEmail;
    private Spinner mCityChoice;
    private ImageView mCityCheck;
    private ImageView mCityPencil;
    private LocalUser mLocalUser;
    private ImageView mProfilePicture;
    private static final int mPickedPicture = 1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mLocalUser = ((MainActivity)getActivity()).getLocalUser();

        mNameCheck = view.findViewById(R.id.edit_display_name_button_check);
        mNameCheck.setVisibility(ImageView.GONE);

        mCityCheck = view.findViewById(R.id.edit_city_spinner_check);
        mCityCheck.setVisibility(ImageView.GONE);

        mCityChoice = view.findViewById(R.id.edit_city_spinner_choice);
        mCityChoice.setEnabled(false);

        final SharedPreferences google_stuff = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        /*String google_name = google_stuff.getString("google_account_name","");
        String google_email = google_stuff.getString("google_account_email", mLocalUser.getUserEmail());
        final int city = google_stuff.getInt("mCityChoice",0);*/
        mCityChoice.setSelection(mLocalUser.getCity());
        mDisplayName = (EditText) view.findViewById(R.id.display_name);
        mDisplayName.setText(mLocalUser.getFirstName());
        mDisplayName.setEnabled(false);
        mEmail = (TextView) view.findViewById(R.id.email);
        mEmail.setText(mLocalUser.getUserEmail());

        mProfilePicture = (ImageView)view.findViewById(R.id.ProfilePicture);

        mProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, mPickedPicture);
            }
        });


        name_pencil = (ImageView) view.findViewById(R.id.edit_display_name_button);
        name_pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDisplayName.setEnabled(true);
                mDisplayName.setCursorVisible(true);
                mDisplayName.setFocusableInTouchMode(true);
                mDisplayName.setInputType(InputType.TYPE_CLASS_TEXT);
                mDisplayName.requestFocus();
                name_pencil.setVisibility(ImageView.GONE);
                mNameCheck.setVisibility(ImageView.VISIBLE);
            }
        });

        mNameCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDisplayName.setEnabled(false);
                String newName = mDisplayName.getText().toString();
                SharedPreferences.Editor editor = google_stuff.edit();
                editor.putString("google_account_name",newName);
                editor.apply();

                mLocalUser.setFirstName(newName);
                ((MainActivity)getActivity()).setLocalUser(mLocalUser);

                mNameCheck.setVisibility(ImageView.GONE);
                name_pencil.setVisibility(ImageView.VISIBLE);
            }
        });


        mCityPencil = (ImageView) view.findViewById(R.id.edit_city_spinner_button);
        mCityPencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCityChoice.setEnabled(true);
                mCityPencil.setVisibility(ImageView.GONE);
                mCityCheck.setVisibility(ImageView.VISIBLE);

                /*mCityChoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int city = Integer.parseInt(mCityChoice.getItemAtPosition(position).toString());
                        final SharedPreferences.Editor editor = google_stuff.edit();
                        editor.putInt("mCityChoice",city);
                        editor.apply();
                    }
                });*/

            }
        });

        mCityCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCityChoice.setEnabled(false);
                mCityPencil.setVisibility(ImageView.VISIBLE);
                mCityCheck.setVisibility(ImageView.GONE);
                int city = mCityChoice.getSelectedItemPosition();
                mLocalUser.setCity(city);
                ((MainActivity)getActivity()).setLocalUser(mLocalUser);
                SharedPreferences.Editor editor = google_stuff.edit();
                editor.putInt("mCityChoice",city);
                editor.apply();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == mPickedPicture && resultCode==RESULT_OK && data!=null){
            Uri selectedImage = data.getData();
            mProfilePicture.setImageURI(null);
            mProfilePicture.setImageURI(selectedImage);
            ((MainActivity)getActivity()).updateNavigationProfile(mProfilePicture.getDrawable());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
