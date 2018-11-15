package com.example.hca127.greenfood.objects;

public class Meal {
    private String mMealId;
    private String mOwnerId;
    private String mMealName;
    private String mRestaurant;
    private String mDescription;
    private int mCityIndex;
    private int[] mIngredient;

    public Meal(){
        mMealId = "";
        mOwnerId = "";
        mMealName = "";
        mRestaurant = "";
        mDescription = "";
        mCityIndex = 0;
        mIngredient = new int[3];
    }

    public void setMealId(String mMealId) {     this.mMealId = mMealId;     }
    public void setOwnerId(String mOwnerId) {   this.mOwnerId = mOwnerId;   }
    public void setMealName(String mMealName) {     this.mMealName = mMealName;     }
    public void setRestaurant(String mRestaurant) {     this.mRestaurant = mRestaurant;     }
    public void setDescription(String mDescription) {   this.mDescription = mDescription;   }
    public void setCityIndex(int mCityIndex) {      this.mCityIndex = mCityIndex;   }
    public void setIngredient(int[] mIngredient) {     this.mIngredient = mIngredient;     }

    public String getMealId() {     return mMealId;     }
    public String getOwnerId() {    return mOwnerId;    }
    public String getMealName() {       return mMealName;   }
    public String getRestaurant() {     return mRestaurant;     }
    public String getDescription() {    return mDescription;    }
    public int getCityIndex() {     return mCityIndex;      }
    public int[] getIngredient() {  return mIngredient;     }
}
