package com.example.hca127.greenfood.objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Emission {
    private String mStringDate;
    private double mAmount;

    public Emission(double nAmount){
        Date date = new Date();
        SimpleDateFormat formatter=new SimpleDateFormat("MM dd, yyyy");
        mStringDate = formatter.format(date);
        mAmount = nAmount;
    }
    public Emission(String nStrDate, double nAmount){
        mStringDate = nStrDate;
        mAmount = nAmount;
    }
    public String getStringDate() {        return mStringDate;    }
    public double getAmount() {        return mAmount;    }
    public Date getDate() throws ParseException {
        SimpleDateFormat formatter=new SimpleDateFormat("MM dd, yyyy");
        return formatter.parse(mStringDate);
    }
    public void setmStringDate(String mStringDate) {
        this.mStringDate = mStringDate;
    }
}
