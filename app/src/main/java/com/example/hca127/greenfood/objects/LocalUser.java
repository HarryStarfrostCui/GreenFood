package com.example.hca127.greenfood.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class LocalUser implements Serializable {
    private String mUserId;
    private String mName;
    private String mUserPassword;
    private String mUserEmail;
    private double mPledge;
    private int mCityIndex;
    ArrayList<Diet> mDietList;

    public LocalUser(){
        mUserId = "";
        mName = "anonymoose";
        mUserPassword = "";
        mUserEmail = "anony@moose.com";
        mPledge = 0.0;
        mCityIndex = 0;
        mDietList = new ArrayList<>();
    }

    public String getUserId() {        return mUserId;    }
    public String getFirstName() {        return mName;    }
    public String getUserEmail() {        return mUserEmail;    }
    public double getPledge() {        return mPledge;    }
    public int getCity() {        return mCityIndex;    }

    public void addDiet(Diet newDiet){        mDietList.add(newDiet);    }
    public Diet getInitialDiet(){   return mDietList.get(0);    }

    public Diet getCurrentDiet() {
        if( mDietList.size() == 0) {
            return new Diet();
        } else {
            return mDietList.get(mDietList.size() - 1);
        }

    }

    public ArrayList<Date> getDietDateList() {
        ArrayList<Date> dates = new ArrayList<>();
        for(int i = 0; i< mDietList.size(); i++){
            dates.add(mDietList.get(i).getDate());
        }
        return dates;
    }

    public ArrayList<Float> getEmissionList(){
        ArrayList<Float> CO2e = new ArrayList<>();
        for(int i = 0; i< mDietList.size(); i++){
            CO2e.add(mDietList.get(i).getUserDietEmission());
        }
        return CO2e;
    }

    public void renewDiet(){
        while(mDietList.size()>1){
            mDietList.remove(0);
        }
    }

    public void setUserId(String UserId) {        this.mUserId = UserId;    }
    public void setFirstName(String mFirstName) {        this.mName = mFirstName;    }
    public void setUserEmail(String userEmail) {        mUserEmail = userEmail;    }
    public void setPledge(double pledge) {        mPledge = pledge;    }
    public void setCity(int index) {        mCityIndex = index;    }
    public void setUserPassword(String userPassword) {        mUserPassword = userPassword;    }

    public boolean CheckPassword(String Password){        return mUserPassword.equals(Password);    }

}
