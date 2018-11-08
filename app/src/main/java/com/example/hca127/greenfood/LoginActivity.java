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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ImageButton logIn;
    private EditText email;
    private EditText password;
    private CheckBox remember;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions google_sign_in_options;
    private SignInButton google_sign_in_button;
    private GoogleSignInAccount account;
    private Integer RC_SIGN_IN = 0;
    private FirebaseUser user;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences account_info = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        SharedPreferences.Editor editor = account_info.edit();

        email = (EditText) findViewById(R.id.email_input);
        email.setText(account_info.getString("saved_email",""));
        password = (EditText) findViewById(R.id.password_input);
        password.setText(account_info.getString("saved_password",""));
        logIn = (ImageButton) findViewById(R.id.login_button);
        remember = (CheckBox) findViewById(R.id.remember_me);
        if(account_info.getString("saved_email","")!="" ){
            remember.setChecked(true);
        }
        /*email_input.requestFocus();*/



        signUp = (ImageView) findViewById(R.id.sign_up_button);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Check", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(LoginActivity.this, "Signed in with "+user.getEmail(),
                                            Toast.LENGTH_LONG).show();

                                    SharedPreferences account_info = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                    SharedPreferences.Editor editor = account_info.edit();

                                    editor.putString("account_name",user.getDisplayName());
                                    editor.putString("account_email", user.getEmail());
                                    editor.putString("account_id", user.getUid());
                                    editor.putInt("city_choice",1);
                                    editor.apply();

                                    if(remember.isChecked()){
                                        editor.putString("saved_email", email.getText().toString());
                                        editor.putString("saved_password", password.getText().toString());
                                        editor.apply();
                                    }

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Check", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });

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
