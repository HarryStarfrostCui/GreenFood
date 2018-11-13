package com.example.hca127.greenfood.objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Emission {
    private String mStrdate;
    private double mAmount;

    public Emission(double nAmount){
        Date date = new Date();
        SimpleDateFormat formatter=new SimpleDateFormat("MM dd, yyyy");
        mStrdate = formatter.format(date);
        mAmount = nAmount;
    }
    public String getStrdate() {        return mStrdate;    }
    public double getAmount() {        return mAmount;    }
    public Date getDate() throws ParseException {
        SimpleDateFormat formatter=new SimpleDateFormat("MM dd, yyyy");
        return formatter.parse(mStrdate);
    }
}
