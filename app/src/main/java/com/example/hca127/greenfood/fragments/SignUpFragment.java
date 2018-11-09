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
    private String mLocalUserName;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mUserName = (EditText) view.findViewById(R.id.create_username_input);
        mEmail = (EditText) view.findViewById(R.id.create_email_input);
        mPassword = (EditText) view.findViewById(R.id.create_password_input);
        mPasswordConfirm = (EditText) view.findViewById(R.id.create_confirm_password);
        mSignUp = (ImageView) view.findViewById(R.id.sign_up_button);
        mLocalUser = ((MainActivity)getActivity()).getLocalUser();
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = mUserName.getText().toString();
                mLocalUserName = userName;
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                String passwordConfirm = mPasswordConfirm.getText().toString();
                if(email.isEmpty() == false && password.isEmpty() == false && password.length()>6 && password.equals(passwordConfirm)) {
                    mAuth = FirebaseAuth.getInstance();
                    FirebaseUser user = mAuth.getCurrentUser();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("Check", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(userName).build();
                                        user.updateProfile(profileUpdates);
                                        updateUser(user);
                                        Toast.makeText(getActivity(), user.getEmail(),
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);

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
                    Toast.makeText(getActivity(), "Sign up failed, please enter valid email and password!", Toast.LENGTH_LONG).show();
                }
            }

            private void updateUser(FirebaseUser user) {
                mLocalUser.setFirstName(mLocalUserName);
                mLocalUser.setUserEmail(user.getEmail());
                mLocalUser.setUserId(user.getUid());
                ((MainActivity)getActivity()).setLocalUser(mLocalUser);
            }
        });

        return view;
    }
}
