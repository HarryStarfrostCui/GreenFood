package com.example.hca127.greenfood.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.Toast;

import com.example.hca127.greenfood.GlideApp;
import com.example.hca127.greenfood.MainActivity;
import com.example.hca127.greenfood.R;
import com.example.hca127.greenfood.objects.LocalUser;
import com.example.hca127.greenfood.objects.Meal;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class RestaurantFragment extends Fragment {
    private Button mMealOne;
    private Button mMealTwo;
    private Button mMealThree;
    private TextView mRestaurantTitle;
    private EditText mRestaurantName;
    private EditText mMealName;
    private EditText mMealDescription;
    private TextView mDescriptionText;
    private Spinner mMainIngredient;
    private Spinner mCity;
    private ImageView mSaveButton;
    private TextView mAddPhotoText;
    private ImageView mEditMeal;
    private ImageView mGalleryButton;
    private ImageView mCameraButton;
    private ImageView mResetButton;
    private ImageView mFinalImage;
    private ImageView mSpeechBubble;
    private TextView mSpeechBubbleText;
    private Drawable mDrawable;
    private Button mDeleteMealButton;


    private static final int mGetFromGallery = 0;
    private static final int mCameraRequest = 1;
    private Meal mMeal;
    private String mCameraFile;
    private Uri mUri;
    private DatabaseReference mDatabase;
    private LocalUser mLocalUser;
    private FirebaseStorage mCloudStorage;
    private StorageReference mStorageReference;
    private StorageReference mImagesReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        mMeal = new Meal();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mLocalUser = ((MainActivity)getActivity()).getLocalUser();
        mRestaurantTitle = view.findViewById(R.id.restaurant_title);
        mMealOne = view.findViewById(R.id.meal_1_button);
        mMealTwo = view.findViewById(R.id.meal_2_button);
        mMealThree = view.findViewById(R.id.meal_3_button);

        mCloudStorage = FirebaseStorage.getInstance();
        mStorageReference = mCloudStorage.getReference();
        mImagesReference = mStorageReference.child("images");

        mEditMeal = view.findViewById(R.id.meal_edit);
        mRestaurantName = view.findViewById(R.id.restaurant_name_edit);
        mMealName = view.findViewById(R.id.meal_name_edit);
        mDescriptionText = view.findViewById(R.id.description_text);
        mMealDescription = view.findViewById(R.id.description_edit);
        mMainIngredient = view.findViewById(R.id.main_ingredient_spinner);
        mCity = view.findViewById(R.id.location_spinner);
        mGalleryButton = view.findViewById(R.id.gallery_button);
        mCameraButton = view.findViewById(R.id.camera_button);
        mResetButton = view.findViewById(R.id.reset_button);
        mFinalImage = view.findViewById(R.id.final_photo);
        mSaveButton = view.findViewById(R.id.meal_edit_confirm);
        mAddPhotoText = view.findViewById(R.id.add_photo_text);
        mSpeechBubble = view.findViewById(R.id.speech_bubble);
        mSpeechBubbleText = view.findViewById(R.id.speech_bubble_text);
        mDeleteMealButton = view.findViewById(R.id.meal_delete);
        mDrawable = getResources().getDrawable(R.drawable.android);
        mMealOne.setAlpha(0.5f);
        mealOneView();

        lockAll();
        checkPhoto();

        mMealOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMealOne.setAlpha(0.5f);
                mMealTwo.setAlpha(1.0f);
                mMealThree.setAlpha(1.0f);
                mealOneView();
            }
        });

        mMealTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMealOne.setAlpha(1.0f);
                mMealTwo.setAlpha(0.5f);
                mMealThree.setAlpha(1.0f);
                mealTwoView();
            }
        });

        mMealThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMealOne.setAlpha(1.0f);
                mMealTwo.setAlpha(1.0f);
                mMealThree.setAlpha(0.5f);
                mealThreeView();
            }
        });

        mEditMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unlockAll();
                mMealOne.setVisibility(View.GONE);
                mMealTwo.setVisibility(View.GONE);
                mMealThree.setVisibility(View.GONE);
                mEditMeal.setVisibility(View.GONE);
            }
        });

        mDeleteMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mRestaurantName.setText("");
                mMealName.setText("");
                mMealDescription.setText("");
                mMainIngredient.setSelection(0);
                mCity.setSelection(0);
                mFinalImage.setImageDrawable(mDrawable);
                updateMeal();
                lockAll();
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
                Long currentTime = Calendar.getInstance().getTimeInMillis();
                String fileName = mLocalUser.getUserId() + "$" + currentTime;
                final StorageReference mUserUpload = mImagesReference.child(fileName + ".jpg");

                final DatabaseReference nMeal = mDatabase.child("meals").push();
                mLocalUser.addMeal(nMeal.getKey());
                mDatabase.child("users").child(mLocalUser.getUserId()).child("meal").push().setValue(nMeal.getKey());
                ((MainActivity)getActivity()).setLocalUser(mLocalUser);
                nMeal.child("meal name").setValue(mMealName.getText().toString());
                nMeal.child("restaurant").setValue(mRestaurantName.getText().toString());
                nMeal.child("protein").child("1").setValue(mMainIngredient.getSelectedItemPosition());
                nMeal.child("description").setValue(mMealDescription.getText().toString());
                nMeal.child("city index").setValue(mCity.getSelectedItemPosition());
                // photo upload starts here
                mFinalImage.setDrawingCacheEnabled(true);
                mFinalImage.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) mFinalImage.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mUserUpload.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    }
                });

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        // Continue with the task to get the download URL
                        return mUserUpload.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            nMeal.child("imageLink").setValue(downloadUri.toString());

                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });
                // RETRIEVE IMAGE
                /*StorageReference httpsReference = mCloudStorage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/greenfood-a5dd0.appspot.com/o/images%2F8muF1KY8cmXSLaX2rndGH3IP1143%241542927527944.jpg?alt=media&token=da7f1146-fd7b-44d5-acdb-962e587063a3");
                GlideApp.with(((MainActivity)getActivity()).getApplicationContext())
                        .load(httpsReference)
                        .into(mFinalImage);
                 */
                lockAll();
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
            try {
                Bitmap imageFromCamera = (Bitmap) data.getExtras().get("data");
                mFinalImage.setImageBitmap(imageFromCamera);
            } catch (NullPointerException e){
                e.printStackTrace();
            }

        }
    }

    public void lockAll() {
        mRestaurantName.setEnabled(false);
        mMealName.setEnabled(false);
        mMealDescription.setEnabled(false);
        mDescriptionText.setText(R.string.description_meal);
        mMainIngredient.setEnabled(false);
        mCity.setEnabled(false);
        mGalleryButton.setVisibility(View.GONE);
        mCameraButton.setVisibility(View.GONE);
        mResetButton.setVisibility(View.GONE);
        mFinalImage.setVisibility(View.VISIBLE);
        mAddPhotoText.setVisibility(View.GONE);
        mSaveButton.setVisibility(View.GONE);
        mEditMeal.setVisibility(View.VISIBLE);
        mDeleteMealButton.setVisibility(View.GONE);
        mMealOne.setVisibility(View.VISIBLE);
        mMealTwo.setVisibility(View.VISIBLE);
        mMealThree.setVisibility(View.VISIBLE);
        checkPhoto();
    }

    public void unlockAll(){
        mRestaurantName.setEnabled(true);
        mMealName.setEnabled(true);
        mMealDescription.setEnabled(true);
        mDescriptionText.setText(R.string.description_text);
        mMainIngredient.setEnabled(true);
        mCity.setEnabled(true);
        mGalleryButton.setVisibility(View.VISIBLE);
        mCameraButton.setVisibility(View.VISIBLE);
        mResetButton.setVisibility(View.VISIBLE);
        mAddPhotoText.setVisibility(View.VISIBLE);
        mSaveButton.setVisibility(View.VISIBLE);
        mSpeechBubble.setVisibility(View.GONE);
        mSpeechBubbleText.setVisibility(View.GONE);
        mDeleteMealButton.setVisibility(View.VISIBLE);
    }

    //TODO:

    public void mealOneView(){

        mRestaurantName.setText("");
        mMealName.setText("");
        mMealDescription.setText("");
        mMainIngredient.setSelection(1);
        mCity.setSelection(1);
        mFinalImage.setImageURI(mUri);
        mRestaurantTitle.setText(R.string.restaurant_title1);
        checkPhoto();
    }

    public void mealTwoView(){
        mRestaurantName.setText("");
        mMealName.setText("");
        mMealDescription.setText("");
        mMainIngredient.setSelection(1);
        mCity.setSelection(1);
        mFinalImage.setImageURI(mUri);
        mRestaurantTitle.setText(R.string.restaurant_title2);
        checkPhoto();
    }
    public void mealThreeView(){
        mRestaurantName.setText("");
        mMealName.setText("");
        mMealDescription.setText("");
        mMainIngredient.setSelection(1);
        mCity.setSelection(1);
        mFinalImage.setImageURI(mUri);
        mRestaurantTitle.setText(R.string.restaurant_title3);
        checkPhoto();
    }

    public void checkPhoto(){
        Drawable image = mFinalImage.getDrawable();
        Drawable.ConstantState cImage = image.getConstantState();
        Drawable.ConstantState cAndroid = mDrawable.getConstantState();
        if(cImage.equals(cAndroid)){
            mSpeechBubbleText.setVisibility(View.VISIBLE);
            mSpeechBubble.setVisibility(View.VISIBLE);
            mSpeechBubbleText.setText(R.string.why_not);
        }
        else{
            mSpeechBubbleText.setVisibility(View.VISIBLE);
            mSpeechBubble.setVisibility(View.VISIBLE);
            mSpeechBubbleText.setText(R.string.nice_photo);
        }
    }

    public void updateMeal(){
        DatabaseReference nMeal = mDatabase.child("meals").push();
        mLocalUser.addMeal(nMeal.getKey());
        mDatabase.child("users").child(mLocalUser.getUserId()).child("meal").push().setValue(nMeal.getKey());
        ((MainActivity)getActivity()).setLocalUser(mLocalUser);
        nMeal.child("meal name").setValue(mMealName.getText().toString());
        nMeal.child("restaurant").setValue(mRestaurantName.getText().toString());
        nMeal.child("MainIngredient").child("1").setValue(mMainIngredient.getSelectedItemPosition());
        nMeal.child("description").setValue(mMealDescription.getText().toString());
        nMeal.child("city index").setValue(mCity.getSelectedItemPosition());
    }
}