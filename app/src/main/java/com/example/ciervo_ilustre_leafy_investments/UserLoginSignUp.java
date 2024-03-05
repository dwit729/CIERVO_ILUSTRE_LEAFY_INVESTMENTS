package com.example.ciervo_ilustre_leafy_investments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserLoginSignUp extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentUserLogin fragmentUserLogin = new FragmentUserLogin();
        FragmentUserSignUp fragmentUserSignUp = new FragmentUserSignUp();
        setContentView(R.layout.activity_user_login_sign_up);

        BottomNavigationView bottomnav = findViewById(R.id.navBarLoginSignUp);

        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.logInSignUpFragmentCon, fragmentUserLogin).addToBackStack("name").commit();
        }

        bottomnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID = item.getItemId();
                if(itemID == R.id.nav_login)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.logInSignUpFragmentCon, fragmentUserLogin).addToBackStack("name").commit();

                }
                else if(itemID == R.id.nav_signup)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.logInSignUpFragmentCon, fragmentUserSignUp).addToBackStack("name1").commit();
                }

                return true;
            }
        });

    }
}