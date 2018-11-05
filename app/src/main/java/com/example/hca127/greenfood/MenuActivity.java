package com.example.hca127.greenfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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

public class MenuActivity extends AppCompatActivity {

    private ImageView addFood;
    private ImageView about;
    private ImageView login;

    private SignInButton google_sign_in_button;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions google_sign_in_options;
    private GoogleSignInAccount account;
    private Integer RC_SIGN_IN = 0;
    private FirebaseUser user;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        addFood = (ImageView) findViewById(R.id.menu_add_food);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        about = (ImageView) findViewById(R.id.menu_about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        /*login = (ImageView) findViewById(R.id.login_button_menu);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });*/

        google_sign_in_button = (SignInButton) findViewById(R.id.google_sign_in_button);
        google_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        google_sign_in_options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("729621741617-a8mmkn2ankbokmmnnoe5ur5m6f0d0ke8.apps.googleusercontent.com")
                        .requestEmail()
                        .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, google_sign_in_options);

        mAuth = FirebaseAuth.getInstance();


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                e.printStackTrace();
            }

        }
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);

    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                user = mAuth.getCurrentUser();
                SharedPreferences google_account_info = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = google_account_info.edit();
               /* editor.putString("name",user.getDisplayName());
                editor.putString("email", user.getEmail());*/
                editor.putString("temp","hello");
                editor.apply();

                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
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
