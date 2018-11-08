package com.example.hca127.greenfood.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hca127.greenfood.MainActivity;
import com.example.hca127.greenfood.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static android.support.v4.content.ContextCompat.getSystemService;


public class LoginFragment extends Fragment {
    private FirebaseUser mUser;
    private FirebaseAuth mAuthentication;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions mGoogleSignInOptions;
    private GoogleSignInAccount mGoogleSignInAccount;
    private SignInButton mGoogleSignInButton;
    private FirebaseAuth mFirebaseAuth;
    private Integer mRCSignIn = 0;
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
        mEmailInput.setShowSoftInputOnFocus(true);
        mPasswordInput = view.findViewById(R.id.passwordInput);
        mSignUpButton = view.findViewById(R.id.signUpButton);
        mLoginButton = view.findViewById(R.id.loginButton);
        mGoogleSignInButton = view.findViewById(R.id.googleLoginButton);
        mGoogleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent,mRCSignIn);
            }
        });
        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.request_token))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), mGoogleSignInOptions);


        mUser = mAuthentication.getCurrentUser();
        updateUserInterface(mUser);

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
                        updateUserInterface(mAuthentication.getCurrentUser());
                    }
                });
    }

    private void signUp(String email, String password) {

        mAuthentication.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
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
            mStatusText.setText(R.string.logged_in);
        } else {
            mStatusText.setText(R.string.login_slogan);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == mRCSignIn) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                mGoogleSignInAccount = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(mGoogleSignInAccount);

            } catch (ApiException e) {
                e.printStackTrace();
            }

        }
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);

    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                SharedPreferences account_info = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = account_info.edit();


                Log.d("CHECK",account.getEmail());
                Log.d("CHECK2", account.getId());
                editor.putString("account_name",account.getDisplayName());
                editor.putString("account_email", account.getEmail());
                editor.putString("account_id", account.getId());
                editor.putInt("city_choice",1);
                editor.apply();

                Toast.makeText(getActivity(),"Logged in with Google",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

                /*String account_name = user.getDisplayName();
                String account_email = user.getEmail();
                Uri account_photo = user.getPhotoUrl();

                google_account.putSerializable("name",account_name);
                google_account.putSerializable("email", account_email);*/
            }
        });
    }



}
