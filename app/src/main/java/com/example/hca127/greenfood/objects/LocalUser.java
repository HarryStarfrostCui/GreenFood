package com.example.hca127.greenfood.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/*
    LocalUser class, holds a more general data than firebase
    in local shared preference
    holds:
    user id
    user name
    user email
    user password
    user pledge
    city area
    current & previous meal plans
 */

public class LocalUser implements Serializable { //

    private String mUserId, mName, mUserEmail;
    private int mCityIndex, mProfileIconIndex;
    private Diet mCurrentDiet;
    private ArrayList<Emission> mEmissions;
    private double mPledge;

    public LocalUser(){
        mUserId = "";
        mName = "anonymoose";
        mUserEmail = "anony@moose.com";
        mPledge = -0.00001;
        mCityIndex = 0;
        Random rand = new Random();
        mProfileIconIndex = rand.nextInt(6);
        mEmissions = new ArrayList<>();
        mCurrentDiet = new Diet(true);
        mEmissions.add(new Emission(mCurrentDiet.getUserDietEmission()));
    }

    public String getUserId() {     return mUserId;    }
    public String getName() {       return mName;    }
    public String getUserEmail() {      return mUserEmail;    }
    public double getPledge() {     return mPledge;    }
    public int getCity() {      return mCityIndex;    }
    public int getProfileIcon(){    return mProfileIconIndex;    }
    public Diet getCurrentDiet() {      return mCurrentDiet;    }
    public ArrayList<Emission> getEmissions() {        return mEmissions;    }

    public void setCurrentDiet(Diet nCurrentDiet){
        this.mCurrentDiet = nCurrentDiet;
        addEmission(nCurrentDiet.getUserDietEmission());
    }
    private void addEmission(double nEmission){
        Emission temp = new Emission(nEmission);
        if(temp.getStrdate().equals(mEmissions.get(mEmissions.size()-1).getStrdate())){
            mEmissions.set(mEmissions.size()-1, temp);
        }else {
            mEmissions.add(temp);
            while(mEmissions.size()>7){
                mEmissions.remove(0);
            }
        }
    }

    public void setEmission(ArrayList<Emission> nEmission){
        mEmissions = nEmission;
    }

    public void setUserId(String UserId) {        this.mUserId = UserId;    }
    public void setName(String Name) {        this.mName = Name;    }
    public void setUserEmail(String userEmail) {        mUserEmail = userEmail;    }
    public void setPledge(double pledge) {       mPledge = pledge;    }
    public void setPledgeByIndex(int index){
        double temp = mCurrentDiet.getUserDietEmission()/9 - 0.00001;
        mPledge = index*temp;
    }
    public void setCity(int index) {        mCityIndex = index;    }
    public void setProfileIcon(int newIconIndex){   mProfileIconIndex = newIconIndex;     }

    //thtese two test in junit class, so this is used, if not, it will never use

}
