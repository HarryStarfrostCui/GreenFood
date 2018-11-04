package com.example.hca127.greenfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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


public class LoginActivity extends AppCompatActivity {

    private ImageView signUp;
    private ImageView logoCommunity;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions google_sign_in_options;
    private SignInButton google_sign_in_button;
    private GoogleSignInAccount account;
    private Integer RC_SIGN_IN = 0;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    Bundle google_account = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText email_input = (EditText) findViewById(R.id.email_input);
        email_input.requestFocus();

        signUp = (ImageView) findViewById(R.id.sign_up_button);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        logoCommunity = (ImageView) findViewById(R.id.logo_community);
        logoCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CommunityActivity.class);
                startActivity(intent);
            }
        });

        google_sign_in_button = (SignInButton) findViewById(R.id.google_sign_in_button);
        google_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent,RC_SIGN_IN);
            }
        });

        google_sign_in_options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
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
            Intent communityPage = new Intent(LoginActivity.this, CommunityActivity.class);
            communityPage.putExtras(google_account);
            startActivity(communityPage);

        }

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    SharedPreferences google_account_info = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = google_account_info.edit();
                    user = mAuth.getCurrentUser();
                    editor.putString("name",user.getDisplayName());
                    editor.putString("email", user.getEmail());
                    editor.apply();


                    /*String account_name = user.getDisplayName();
                    String account_email = user.getEmail();
                    Uri account_photo = user.getPhotoUrl();

                    google_account.putSerializable("name",account_name);
                    google_account.putSerializable("email", account_email);*/
                }
            }
        });
    }




    /*public void signUp(View view) {
        signUp = (TextView) findViewById(R.id.sign_up_button);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }*/
}
