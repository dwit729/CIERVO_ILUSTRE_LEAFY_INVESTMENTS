package com.example.ciervo_ilustre_leafy_investments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawlay;
    NavigationView naview;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        drawlay = findViewById(R.id.drawer_layout);
        naview = findViewById(R.id.navigation_view);
        naview.bringToFront();
        naview.setNavigationItemSelectedListener(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawlay, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawlay.addDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();


        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            naview.setCheckedItem(R.id.nav_home);
        }




    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        if(itemID == R.id.nav_home)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        }
        else if(itemID == R.id.nav_acc)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountFragment()).commit();

        }
        else if(itemID == R.id.nav_faq)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FAQFragment()).commit();

        }
        else if(itemID == R.id.nav_pref)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PreferencesFragment()).commit();

        }
        else if(itemID == R.id.nav_logout)
        {
            showPopup();
        }

        drawlay.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if(drawlay.isDrawerOpen(GravityCompat.START))
        {
            drawlay.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }

    }

    public void showPopup() {
        AlertDialog.Builder alertbuild = new AlertDialog.Builder(this);
        alertbuild.setMessage("Are you sure?")
                .setCancelable(false)
                .setPositiveButton("Logout", new DialogInterface.OnClickListener()                 {

                    public void onClick(DialogInterface dialog, int which) {

                       Intent intent = new Intent(UserDashboard.this, UserLoginSignUp.class);
                       startActivity(intent);
                       finish();

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();

                    }
                });

        AlertDialog alert1 = alertbuild.create();
        alert1.setTitle("LOG OUT");
        alert1.show();
    }



}
