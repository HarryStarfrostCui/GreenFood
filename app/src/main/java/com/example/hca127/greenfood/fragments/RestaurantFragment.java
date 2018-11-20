package com.example.hca127.greenfood.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.hca127.greenfood.MainActivity;
import com.example.hca127.greenfood.R;
import com.example.hca127.greenfood.objects.LocalUser;
import com.example.hca127.greenfood.objects.Meal;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;


public class RestaurantFragment extends Fragment {
    private EditText mRestaurantName;
    private EditText mMealName;
    private EditText mMealDescription;
    private Spinner mProtein;
    private Spinner mSecondIngredient;
    private Spinner mThirdIngredient;
    private Spinner mCityShare;
    private Switch mLocationSwitch;

    private int temp;
    private ImageView mGalleryButton;
    private ImageView mCameraButton;
    private ImageView mResetButton;
    private static final int mGetFromGallery = 0;
    private static final int mCameraRequest = 1;
    private Meal mMeal;
    private Button mShareBotton;
    private DatabaseReference mDatabase;
    private LocalUser mLocalUser;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        mMeal = new Meal();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mLocalUser = ((MainActivity)getActivity()).getLocalUser();

        mRestaurantName = view.findViewById(R.id.restaurant_name_edit);
        mMealName = view.findViewById(R.id.meal_name_edit);
        mMealName.setEnabled(true);
        mMealDescription = view.findViewById(R.id.description_edit);
        mMealDescription.setEnabled(true);
        mProtein = view.findViewById(R.id.protein_spinner);
        mSecondIngredient = view.findViewById(R.id.second_ingredient_spinner);
        mThirdIngredient = view.findViewById(R.id.third_ingredient_spinner);
        mCityShare = view.findViewById(R.id.location_spinner);
        mCityShare.setEnabled(false);
        mLocationSwitch = view.findViewById(R.id.share_location_switch);
        mGalleryButton = view.findViewById(R.id.gallery_button);
        mGalleryButton.setVisibility(View.VISIBLE);
        mCameraButton = view.findViewById(R.id.camera_button);
        mCameraButton.setVisibility(View.VISIBLE);
        mResetButton = view.findViewById(R.id.reset_button);
        mResetButton.setVisibility(View.GONE);
        mShareBotton = view.findViewById(R.id.facebook_share_button);

        mLocationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mCityShare.setEnabled(true);
            }
        });

        mGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                while (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, mGetFromGallery);
                }

                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                galleryIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(galleryIntent, mGetFromGallery);
                }
            });

        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                while (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED){
                    String[] PERMISSIONS = {android.Manifest.permission.CAMERA};
                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, mCameraRequest);
                }

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, mCameraRequest);
                cameraIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable galleryDrawable = getResources().getDrawable(R.drawable.gallery);
                Drawable CameraDrawable = getResources().getDrawable(R.drawable.camera);
                mGalleryButton.setImageDrawable(galleryDrawable);
                mGalleryButton.setVisibility(View.VISIBLE);
                mCameraButton.setImageDrawable(CameraDrawable);
                mCameraButton.setVisibility(View.VISIBLE);
                mResetButton.setVisibility(View.GONE);

            }
        });

        mShareBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference nMeal = mDatabase.child("meals").push();
                mLocalUser.addMeal(nMeal.getKey());
                mDatabase.child("users").child(mLocalUser.getUserId()).child("meal").push().setValue(nMeal.getKey());
                ((MainActivity)getActivity()).setLocalUser(mLocalUser);
                nMeal.child("meal name").setValue(mMealName.getText().toString());
                nMeal.child("restaurant").setValue(mRestaurantName.getText().toString());
                nMeal.child("protein").child("1").setValue(mProtein.getSelectedItemPosition());
                nMeal.child("protein").child("2").setValue(mSecondIngredient.getSelectedItemPosition());
                nMeal.child("protein").child("3").setValue(mThirdIngredient.getSelectedItemPosition());
                nMeal.child("description").setValue(mMealDescription.getText().toString());
                nMeal.child("city index").setValue(mCityShare.getSelectedItemPosition());
            }
        });

        return view;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        //Detects request codes
        if(requestCode==mGetFromGallery) {
            try {
                Uri selectedImage = data.getData();
                Bitmap imageFromGallery = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                mGalleryButton.setImageBitmap(imageFromGallery);
                mCameraButton.setVisibility(View.GONE);
                mResetButton.setVisibility(View.VISIBLE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        if(requestCode==mCameraRequest){
            try {
                Bitmap imageFromCamera = (Bitmap) data.getExtras().get("data");
                mCameraButton.setImageBitmap(imageFromCamera);
                mGalleryButton.setVisibility(View.GONE);
                mResetButton.setVisibility(View.VISIBLE);
            } catch (NullPointerException e){
                e.printStackTrace();
            }


        }
    }
}