package com.example.hca127.greenfood;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth mAuthentication;
    private TextView mStatusText;
    private EditText mEmailInput;
    private EditText mPasswordInput;
    private Button mSignUpButton;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuthentication = FirebaseAuth.getInstance();
        mStatusText = findViewById(R.id.statusText);
        mEmailInput = findViewById(R.id.emailInput);
        mEmailInput.requestFocus();
        mPasswordInput = findViewById(R.id.passwordInput);
        mSignUpButton = findViewById(R.id.signUpButton);
        mLoginButton = findViewById(R.id.loginButton);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailInput.getText().toString();
                String password = mPasswordInput.getText().toString();

                if( checkPassword(password) ) {
                    logIn(email, password);
                } else {
                    Toast.makeText(LoginActivity.this, "not a valid password", Toast.LENGTH_LONG ).show();
                }
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailInput.getText().toString();
                String password = mPasswordInput.getText().toString();
                if( checkPassword(password) ) {
                    signUp(email, password);
                } else {
                    Toast.makeText(LoginActivity.this, "not a valid password", Toast.LENGTH_LONG ).show();
                }
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuthentication.getCurrentUser();
        updateUserInterface(currentUser);
    }

    private void logIn(String email, String password) {

        mAuthentication.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        updateUserInterface(mAuthentication.getCurrentUser());
                    }
                });
    }

    private void signUp(String email, String password) {

        mAuthentication.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        updateUserInterface(mAuthentication.getCurrentUser());
                    }
                });

    }

    private boolean checkPassword(String password) {
        if (6 <= password.length())
            return true;
        else
            return false;
    }

    private void updateUserInterface(FirebaseUser user) {
        if(user != null) {
            mStatusText.setText("you're a user!!!!");
        } else {
            mStatusText.setText("not logged in");
        }
    }


}
