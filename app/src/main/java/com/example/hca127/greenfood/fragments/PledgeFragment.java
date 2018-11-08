package com.example.hca127.greenfood.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hca127.greenfood.MainActivity;
import com.example.hca127.greenfood.R;
import com.example.hca127.greenfood.objects.Diet;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class PledgeFragment extends Fragment {
    private BarChart mSuggestionChart;
    private Button mAboutButton;
    private ImageButton mFacebookShare;
    private Diet mDiet;
    private TextView mReduceSuggestionText;
    private TextView mIncreaseSuggestionText;
    private TextView mUserEmissionSaving;
    private TextView mCarbonSaved;
    private TextView mTreesSaved;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pledge, container, false);

        mDiet = ((MainActivity) getActivity()).getLocalUserDiet();

        mReduceSuggestionText = view.findViewById(R.id.reduceSuggestionText);
        mReduceSuggestionText.setText(mDiet.getFoodName(mDiet.getSuggestionMaxIndex()));

        mIncreaseSuggestionText = view.findViewById(R.id.increaseSuggestionText);
        mIncreaseSuggestionText.setText(mDiet.getFoodName(mDiet.getSuggestionMinIndex()));

        mUserEmissionSaving = view.findViewById(R.id.userEmissionSaving);
        mUserEmissionSaving.setText(String.valueOf(mDiet.getSuggestedDietSavingAmount()));

        mSuggestionChart = view.findViewById(R.id.suggestionChart);
        mFacebookShare = view.findViewById(R.id.facebookShare);
        shareDialog = new ShareDialog(this);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, mDiet.getUserDietEmission()));
        entries.add(new BarEntry(1, 1500f));
        entries.add(new BarEntry(2, mDiet.getSuggestedDietEmission()));

        BarDataSet barDataSet = new BarDataSet(entries, "BarDataSet");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData suggestionData = new BarData(barDataSet);
        mSuggestionChart.getXAxis().setDrawGridLines(false);
        mSuggestionChart.getLegend().setEnabled(false);
        mSuggestionChart.getAxisRight().setAxisMinimum(0f);
        mSuggestionChart.getAxisLeft().setAxisMinimum(0f);
        mSuggestionChart.getDescription().setEnabled(false);

        mSuggestionChart.setData(suggestionData);
        mSuggestionChart.animateY(1200);
        mSuggestionChart.invalidate();

        float carbonSaved = mDiet.getSuggestedDietSavingAmount() * .9f * 2463000f / 1000;
        float treesSaved = carbonSaved / 22;  // carbon offset of trees

        if (carbonSaved < 0) {
            carbonSaved = 0;
            treesSaved = 0;
        }

        mCarbonSaved = view.findViewById(R.id.carbonSaved);
        mCarbonSaved.setText(String.valueOf(carbonSaved));

        mTreesSaved = view.findViewById(R.id.treesSaved);
        mTreesSaved.setText(String.valueOf(treesSaved));

        //share stuff
        mFacebookShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(getActivity(), "share success!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getActivity(), "cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://cmpt276.sfu.rosielab.ca/project"))
                        .setQuote("testing")
                        .build();
                if (ShareDialog.canShow(ShareLinkContent.class))
                {
                    shareDialog.show(linkContent);
                }


                Bitmap mLogoImage = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.logo);
                SharePhoto mSharePhoto = new SharePhoto.Builder()
                        .setBitmap(mLogoImage)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(mSharePhoto)
                        .setShareHashtag(new ShareHashtag.Builder().setHashtag("#I dialog").build())
                        .build();
                ShareApi.share(content, null);
                if (ShareDialog.canShow(SharePhotoContent.class)) {
                    shareDialog.show(content);
                }
            }
        });

//        mFacebookShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_TEXT, Uri.parse("https://cmpt276.sfu.rosielab.ca/project"));
//                shareIntent.setType("text/plain");
//                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.welcome)));
//            }
//        });

//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//            callbackManager.onActivityResult(requestCode, resultCode, data);
//        }
//    }

        //}

        return view;
    }
}
