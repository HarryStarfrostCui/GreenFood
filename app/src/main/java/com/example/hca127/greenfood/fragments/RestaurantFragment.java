package com.example.hca127.greenfood.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.hca127.greenfood.R;

import java.io.FileNotFoundException;
import java.io.IOException;


public class RestaurantFragment extends Fragment {
    private EditText mRestaurantName;
    private EditText mMealName;
    private EditText mMealDescription;
    private Spinner mProtein;
    private Spinner mSecondIngredient;
    private Spinner mThirdIngredient;
    private Spinner mCityShare;
    private Switch mLocationSwitch;

    private ImageView mGalleryButton;
    private ImageView mCameraButton;
    private static final int mGetFromGallery = 0;
    private static final int mCameraRequest = 1;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);

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
        mGalleryButton = view.findViewById(R.id.beef_button);
        mGalleryButton.setVisibility(View.VISIBLE);
        mCameraButton = view.findViewById(R.id.lamb_button);
        mCameraButton.setVisibility(View.VISIBLE);


        mLocationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mCityShare.setEnabled(true);
            }
        });

        mGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, mGetFromGallery);
                mCameraButton.setVisibility(View.GONE);
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
                mGalleryButton.setVisibility(View.GONE);
            }
        });

        return view;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        //Detects request codes
        if(requestCode==mGetFromGallery && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                mGalleryButton.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                Log.w("IOException","NOT GOOD");
                e.printStackTrace();
            }
        }

        if(requestCode==mCameraRequest){
            Bitmap image = (Bitmap) data.getExtras().get("data");
            mCameraButton.setImageBitmap(image);

        }
    }
}