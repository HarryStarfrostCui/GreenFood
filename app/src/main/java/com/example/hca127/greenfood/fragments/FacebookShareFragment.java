package com.example.hca127.greenfood.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hca127.greenfood.MainActivity;
import com.example.hca127.greenfood.R;
import com.example.hca127.greenfood.objects.Diet;
import com.example.hca127.greenfood.objects.LocalUser;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class FacebookShareFragment extends Fragment {

    private ImageButton mFacebookShare;
    private LocalUser mLocalUser;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        callbackManager = CallbackManager.Factory.create();
        final View view = inflater.inflate(R.layout.fragment_facebook_share, container, false);
        final Bitmap mLogoImage = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.logo_share);

        mLocalUser = ((MainActivity)getActivity()).getLocalUser();
        mFacebookShare = view.findViewById(R.id.facebookShare);
        shareDialog = new ShareDialog(this);


                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(getActivity(), "Share Cancelled.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getActivity(), "Share succeed.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                //Link share

//                ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                        .setContentUrl(Uri.parse("https://cmpt276.sfu.rosielab.ca/project"))
//                        .setQuote("I plaged...")
//                        .build();
//                if (ShareDialog.canShow(ShareLinkContent.class))
//                {
//                    shareDialog.show(linkContent);
//                }
//            }
//        });

//                Bitmap mLogoImage = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.logo_share);

                SharePhoto mSharePhoto = new SharePhoto.Builder()
                        .setBitmap(mLogoImage)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(mSharePhoto)
                        .setShareHashtag(new ShareHashtag.Builder().setHashtag(String.valueOf("#GreenVancouverProject ")).build())
                        .build();
                ShareApi.share(content, null);
                if (ShareDialog.canShow(SharePhotoContent.class)) {
                    shareDialog.show(content);
                }
                


        //call intent


//        mFacebookShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_STREAM,mLogoImage);
//                shareIntent.setType("image/jpeg");
//                startActivity(Intent.createChooser(shareIntent, "Share with"));
//            }
//        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}

