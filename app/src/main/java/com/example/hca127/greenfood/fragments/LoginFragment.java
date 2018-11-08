package com.example.hca127.greenfood.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hca127.greenfood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {
    private FirebaseUser mUser;
    private FirebaseAuth mAuthentication;
    private TextView mStatusText;
    private EditText mEmailInput;
    private EditText mPasswordInput;
    private Button mSignUpButton;
    private Button mLoginButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuthentication = FirebaseAuth.getInstance();
        mStatusText = view.findViewById(R.id.statusText);
        mEmailInput = view.findViewById(R.id.emailInput);
        mEmailInput.requestFocus();
        mPasswordInput = view.findViewById(R.id.passwordInput);
        mSignUpButton = view.findViewById(R.id.signUpButton);
        mLoginButton = view.findViewById(R.id.loginButton);

        mUser = mAuthentication.getCurrentUser();
        updateUser(mUser);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailInput.getText().toString();
                String password = mPasswordInput.getText().toString();

                if( checkPassword(password) ) {
                    logIn(email, password);
                } else {
                    Toast.makeText(getActivity(), "not a valid password", Toast.LENGTH_LONG ).show();
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
                    Toast.makeText(getActivity(), "not a valid password", Toast.LENGTH_LONG ).show();
                }
            }
        });

    return view;
    }

    private void logIn(String email, String password) {

        mAuthentication.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        updateUser(mAuthentication.getCurrentUser());
                    }
                });
    }

    private void signUp(String email, String password) {

        mAuthentication.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        updateUser(mAuthentication.getCurrentUser());
                    }
                });

    }

    private boolean checkPassword(String password) {
        if (6 <= password.length())
            return true;
        else
            return false;
    }

    private void updateUser(FirebaseUser user) {
        if(user != null) {
            mStatusText.setText(R.string.logged_in);
        } else {
            mStatusText.setText(R.string.login_slogan);
        }
    }



}
