package com.example.hca127.greenfood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {


    private GoogleSignInAccount account;
    private Integer RC_SIGN_IN = 0;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    Bundle google_account = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText email_input = (EditText) findViewById(R.id.emailInput);
        email_input.requestFocus();

        mAuth = FirebaseAuth.getInstance();





    }






}
