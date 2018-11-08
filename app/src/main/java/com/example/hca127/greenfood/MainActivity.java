package com.example.hca127.greenfood;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hca127.greenfood.fragments.AboutFragment;
import com.example.hca127.greenfood.fragments.AddingFoodFragment;
import com.example.hca127.greenfood.fragments.CommunityFragment;
import com.example.hca127.greenfood.fragments.LoginFragment;
import com.example.hca127.greenfood.fragments.ProfileFragment;
import com.example.hca127.greenfood.objects.Diet;
import com.example.hca127.greenfood.objects.LocalUser;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawer;
    private LocalUser mLocalUser;
    private TextView mUserEmail;
    private TextView mUserName;

    //  https://www.youtube.com/watch?v=bjYstsO1PgI&index=4&list=PLrnPJCHvNZuDQ-jWPw13-wY2J57Z6ep6G&t=0s

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawer = findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar,
                R.string.Navigation_drawer_open, R.string.Navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        //open default fragment, currently default = calculator

        // load local user from shared Preferences
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("mLocalUser", "");

        mLocalUser = gson.fromJson(json, LocalUser.class);
        if(mLocalUser == null)
            mLocalUser = new LocalUser();

        updateHeader();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CommunityFragment()).commit();
            navigationView.setCheckedItem(R.id.menu_community);
        }

    }

    @Override
    public void onBackPressed() {
        if(mDrawer.isDrawerOpen(GravityCompat.START)){
            mDrawer.closeDrawer(GravityCompat.START);
        }else {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (count == 0) {
                super.onBackPressed();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu_community:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CommunityFragment()).addToBackStack(null).commit();
                break;
            case R.id.menu_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AboutFragment()).addToBackStack(null).commit();
                break;
            case R.id.menu_calculator:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddingFoodFragment()).addToBackStack("AddingFoodFragment").commit();
                break;
            case R.id.menu_user:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).addToBackStack(null).commit();
                break;
            case R.id.menu_Login:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LoginFragment()).addToBackStack(null).commit();
                break;
            case R.id.menu_LogOff:
                Toast.makeText(this, "this creates the logoff warning popup,\n with option of continue logging off", Toast.LENGTH_LONG).show();
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Diet getLocalUserDiet() {
        return mLocalUser.getCurrentDiet();
    }

    public void setLocalUserDiet(Diet diet) {
        mLocalUser.addDiet(diet);
    }

    public LocalUser getLocalUser() {
        return mLocalUser;
    }

    public void setLocalUser(LocalUser user) {
        mLocalUser = user;
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mLocalUser);
        prefsEditor.putString("mLocalUser", json);
        prefsEditor.apply();
        updateHeader();
    }

    private void updateHeader() {
        NavigationView navigationView = findViewById(R.id.navigation_view);
        mUserEmail = navigationView.getHeaderView(0).findViewById(R.id.userEmail);
        mUserEmail.setText(mLocalUser.getUserEmail());
        mUserName = navigationView.getHeaderView(0).findViewById(R.id.userName);
        mUserName.setText(mLocalUser.getFirstName());
    }

}
