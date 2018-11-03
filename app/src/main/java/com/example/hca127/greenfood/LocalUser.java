package com.example.hca127.greenfood;

import java.util.ArrayList;
import java.util.Date;

public class LocalUser {
    private String UserId;
    private String LastName;
    private String FirstName;
    private String UserPassword;
    private String UserEmail;
    private double Pledge;
    private int CityIndex;
    ArrayList<Diet> DietList;

    public LocalUser(){
        UserId = "";
        LastName = "";
        FirstName = "";
        UserPassword = "";
        UserEmail = "";
        Pledge = 0.0;
        CityIndex = 0;
        DietList = new ArrayList<>();
    }

    public String getUserId() {        return UserId;    }
    public String getLastName(){        return  LastName;    }
    public String getFirstName() {        return FirstName;    }
    public String getUserEmail() {        return UserEmail;    }
    public double getPledge() {        return Pledge;    }
    public int getCity() {        return CityIndex;    }

    public void addDiet(Diet newDiet){        DietList.add(newDiet);    }
    public Diet getInitialDiet(){   return DietList.get(0);    }

    public ArrayList<Date> getDietDateList() {
        ArrayList<Date> dates = new ArrayList<>();
        for(int i = 0; i<DietList.size(); i++){
            dates.add(DietList.get(i).getDate());
        }
        return dates;
    }

    public ArrayList<Float> getEmissionList(){
        ArrayList<Float> CO2e = new ArrayList<>();
        for(int i = 0; i<DietList.size(); i++){
            CO2e.add(DietList.get(i).getUserDietEmission());
        }
        return CO2e;
    }

    public void renewDiet(){
        while(DietList.size()>1){
            DietList.remove(0);
        }
    }

    private void setUserId(String UserId) {        UserId = UserId;    }
    public void setLastName(String lastName) {        LastName = lastName;    }
    public void setFirstName(String firstName) {        FirstName = firstName;    }
    public void setUserEmail(String userEmail) {        UserEmail = userEmail;    }
    public void setPledge(double pledge) {        Pledge = pledge;    }
    public void setCity(int index) {        CityIndex = index;    }
    public void setUserPassword(String userPassword) {        UserPassword = userPassword;    }

    public boolean CheckPassword(String Password){        return UserPassword.equals(Password);    }

}
