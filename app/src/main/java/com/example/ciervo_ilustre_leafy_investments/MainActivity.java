package com.example.ciervo_ilustre_leafy_investments;

import static com.google.android.material.internal.ViewUtils.showKeyboard;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.chaos.view.PinView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PinView pinView = findViewById(R.id.firstPinView);
        pinView.setHideLineWhenFilled(true);
        pinView.setPasswordHidden(true);
        pinView.setAnimationEnable(true);





    }




}

