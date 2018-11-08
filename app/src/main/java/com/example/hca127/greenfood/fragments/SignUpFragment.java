package com.example.hca127.greenfood.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hca127.greenfood.R;
import com.example.hca127.greenfood.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignUpFragment extends Fragment {


    private FirebaseAuth mAuth;
    private ImageView mSignUp;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordConfirm;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mEmail = (EditText) view.findViewById(R.id.create_email_input);
        mPassword = (EditText) view.findViewById(R.id.create_password_input);
        mPasswordConfirm = (EditText) view.findViewById(R.id.create_confirm_password);
        mSignUp = (ImageView) view.findViewById(R.id.sign_up_button);
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth = FirebaseAuth.getInstance();

                if (mPassword.getText().toString().equals(mPasswordConfirm.getText().toString())) {

                    mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("Check", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(getActivity(), user.getEmail(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("Check2", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(getActivity(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });


                } else {
                    Toast.makeText(getActivity(), "Passwords don't match!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}
