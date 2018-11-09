package com.example.hca127.greenfood.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hca127.greenfood.MainActivity;
import com.example.hca127.greenfood.R;
import com.example.hca127.greenfood.objects.LocalUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class SignUpFragment extends Fragment {

    private FirebaseAuth mAuth;

    private ImageView mSignUp;
    private EditText mUserName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordConfirm;
    private LocalUser mLocalUser;
    private String mUserNameLocal;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mUserName = (EditText) view.findViewById(R.id.create_username_input);
        mEmail = (EditText) view.findViewById(R.id.create_email_input);
        mPassword = (EditText) view.findViewById(R.id.create_password_input);
        mPasswordConfirm = (EditText) view.findViewById(R.id.create_confirm_password);
        mSignUp = (ImageView) view.findViewById(R.id.sign_up_button);
        mLocalUser = ((MainActivity)getActivity()).getLocalUser();
        mAuth = FirebaseAuth.getInstance();

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });


        return view;
    }

    private void registerUser() {
        final String userName = mUserName.getText().toString();
        mUserNameLocal = userName;
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String passwordConfirm = mPasswordConfirm.getText().toString();

        if (email.isEmpty()) {
            mEmail.setError("Email is required");
            mEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("Email is required");
            mEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            mPassword.setError("Password is required");
            mPassword.requestFocus();
            return;
        }
        if (!password.equals(passwordConfirm)) {
            mPasswordConfirm.setError("Passwords do not match!");
            mPasswordConfirm.requestFocus();
            return;
        }
        if (password.length()<6) {
            mPassword.setError("Minimum lenght of password should be 6");
            mPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new CommunityFragment()).commit();
                    NavigationView navigationView = getActivity().findViewById(R.id.navigation_view);
                    navigationView.setCheckedItem(R.id.menu_community);

                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getActivity().getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}
