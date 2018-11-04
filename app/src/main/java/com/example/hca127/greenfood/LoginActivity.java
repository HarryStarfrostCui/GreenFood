package com.example.hca127.greenfood;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.android.gms.tasks.Task;


public class LoginActivity extends AppCompatActivity {

    private ImageView signUp;
    private ImageView logoCommunity;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions google_sign_in_options;
    private SignInButton google_sign_in_button;
    private GoogleSignInAccount account;
    private Integer RC_SIGN_IN = 0;

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
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, google_sign_in_options);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
           /* account = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);
            String account_name = ((GoogleSignInAccount) account).getDisplayName();
            String account_given_name = account.getGivenName();
            String account_family_name = account.getFamilyName();
            String account_email = account.getEmail();
            String account_id = account.getId();
            Uri account_photo = account.getPhotoUrl();

            google_account.putSerializable("name",account_name);
            google_account.putSerializable("given_name", account_given_name);
            google_account.putSerializable("family_name",account_family_name);
            google_account.putSerializable("email", account_email);
            google_account.putSerializable("id",account_id);*/

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            findViewById(R.id.google_sign_in_button).setVisibility(View.GONE);
            Intent communityPage = new Intent(LoginActivity.this, CommunityActivity.class);
            communityPage.putExtras(google_account);
            startActivity(communityPage);

        }

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);

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
