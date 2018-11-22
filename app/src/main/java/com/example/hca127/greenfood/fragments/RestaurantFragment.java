package com.example.hca127.greenfood.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.hca127.greenfood.MainActivity;
import com.example.hca127.greenfood.R;
import com.example.hca127.greenfood.objects.LocalUser;
import com.example.hca127.greenfood.objects.Meal;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class RestaurantFragment extends Fragment {
    private EditText mRestaurantName;
    private EditText mMealName;
    private EditText mMealDescription;
    private TextView mDescriptionText;
    private Spinner mProtein;
    private Spinner mSecondIngredient;
    private Spinner mThirdIngredient;
    private Spinner mCityShare;
    private Switch mLocationSwitch;
    private Button mSaveButton;
    private TextView mAddPhotoText;
    private ImageView mEditMeal;
    private TextView mOR;
    private String mCameraFile;
    private Uri mUri;

    private ImageView mGalleryButton;
    private ImageView mCameraButton;
    private ImageView mResetButton;
    private ImageView mFinalImage;
    private static final int mGetFromGallery = 0;
    private static final int mCameraRequest = 1;
    private Meal mMeal;
    private Button mShareButton;
    private DatabaseReference mDatabase;
    private LocalUser mLocalUser;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        mMeal = new Meal();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mLocalUser = ((MainActivity)getActivity()).getLocalUser();

        mEditMeal = view.findViewById(R.id.meal_edit);
        mRestaurantName = view.findViewById(R.id.restaurant_name_edit);
        mMealName = view.findViewById(R.id.meal_name_edit);
        mDescriptionText = view.findViewById(R.id.description_text);
        mMealDescription = view.findViewById(R.id.description_edit);
        mProtein = view.findViewById(R.id.protein_spinner);
        mSecondIngredient = view.findViewById(R.id.second_ingredient_spinner);
        mThirdIngredient = view.findViewById(R.id.third_ingredient_spinner);
        mCityShare = view.findViewById(R.id.location_spinner);
        mLocationSwitch = view.findViewById(R.id.share_location_switch);
        mGalleryButton = view.findViewById(R.id.gallery_button);
        mCameraButton = view.findViewById(R.id.camera_button);
        mResetButton = view.findViewById(R.id.reset_button);
        mFinalImage = view.findViewById(R.id.final_photo);
        mShareButton = view.findViewById(R.id.facebook_share_button);
        mSaveButton = view.findViewById(R.id.save_meal_button);
        mAddPhotoText = view.findViewById(R.id.add_photo_text);
        mOR = view.findViewById(R.id.or_text);
        lockAll();

        mEditMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unlockAll();
            }
        });

        mLocationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
                   mCityShare.setEnabled(true);
               }
               else{
                   mCityShare.setEnabled(false);
               }
            }
        });

        mGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("-mm-ss");
                String newPicture = dateFormat.format(date)+".png";
                String outPath = "/Internal storage/DCIM/Camera"+newPicture;
                File outFile = new File(outPath);
                mCameraFile = outFile.toString();
                Uri uriSavedImage = Uri.fromFile(outFile);
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                startActivityForResult(cameraIntent, mCameraRequest);
                cameraIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable android = getResources().getDrawable(R.drawable.android);
                mFinalImage.setImageDrawable(android);
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
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
                lockAll();
            }
        });

        mShareButton.setOnClickListener(new View.OnClickListener() {
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
                lockAll();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FacebookShareFragment()).addToBackStack(null).commit();
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
                mFinalImage.setImageBitmap(imageFromGallery);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        if(requestCode==mCameraRequest){
            File file = new File(mCameraFile);
            if(!file.exists()){
                file.mkdir();
            }
            
            if(data != null){
                mUri = Uri.fromFile(file);
                /*mUri = data.getData();*/
                mFinalImage.setImageURI( mUri);
//                Bitmap imageFromCamera = (Bitmap) data.getExtras().get("data");
//                mFinalImage.setImageBitmap(imageFromCamera);
            }
            if( mUri == null && mCameraFile != null){
                mUri = Uri.fromFile(file);
                mFinalImage.setImageURI(mUri);
            }

//




        }
    }

    public void lockAll(){
        mRestaurantName.setEnabled(false);
        mMealName.setEnabled(false);
        mMealDescription.setEnabled(false);
        mDescriptionText.setText(R.string.description_meal);
        mProtein.setEnabled(false);
        mSecondIngredient.setEnabled(false);
        mThirdIngredient.setEnabled(false);
        mCityShare.setEnabled(false);
        mGalleryButton.setVisibility(View.GONE);
        mCameraButton.setVisibility(View.GONE);
        mResetButton.setVisibility(View.GONE);
        mFinalImage.setVisibility(View.VISIBLE);
        mAddPhotoText.setVisibility(View.GONE);
        mSaveButton.setVisibility(View.GONE);
        mShareButton.setVisibility(View.GONE);
        mLocationSwitch.setVisibility(View.GONE);
        mCityShare.setVisibility(View.GONE);
        mOR.setVisibility(View.GONE);
    }

    public void unlockAll(){
        mRestaurantName.setEnabled(true);
        mMealName.setEnabled(true);
        mMealDescription.setEnabled(true);
        mDescriptionText.setText(R.string.description_text);
        mProtein.setEnabled(true);
        mSecondIngredient.setEnabled(true);
        mThirdIngredient.setEnabled(true);
        mGalleryButton.setVisibility(View.VISIBLE);
        mCameraButton.setVisibility(View.VISIBLE);
        mResetButton.setVisibility(View.VISIBLE);
        mAddPhotoText.setVisibility(View.VISIBLE);
        mSaveButton.setVisibility(View.VISIBLE);
        mShareButton.setVisibility(View.VISIBLE);
        mLocationSwitch.setVisibility(View.VISIBLE);
        mCityShare.setVisibility(View.VISIBLE);
        mOR.setVisibility(View.VISIBLE);
    }
}