package com.example.hca127.greenfood;

public class LocalUser {
    private String User_Id;
    private String Last_Name;
    private String First_Name;
    private String User_Password;
    private String User_Email;
    private double Pledge;
    private int City_index;

    public LocalUser(){
        User_Id = "";
        Last_Name = "";
        First_Name = "";
        User_Password = "";
        User_Email = "";
        Pledge = 0.0;
        City_index = 0;
    }

    public String getUser_Id() {        return User_Id;    }
    public String getLast_Name(){        return  Last_Name;    }
    public String getFirst_Name() {        return First_Name;    }
    public String getUser_Email() {        return User_Email;    }
    public double getPledge() {        return Pledge;    }
    public int getCity() {        return City_index;    }

    private void setUser_Id(String user_Id) {        User_Id = user_Id;    }
    public void setLast_Name(String last_Name) {        Last_Name = last_Name;    }
    public void setFirst_Name(String first_Name) {        First_Name = first_Name;    }
    public void setUser_Email(String user_Email) {        User_Email = user_Email;    }
    public void setPledge(double pledge) {        Pledge = pledge;    }
    public void setCity(int index) {        City_index = index;    }

    public boolean Check_Password(String Password){        return User_Password.equals(Password);    }

}
