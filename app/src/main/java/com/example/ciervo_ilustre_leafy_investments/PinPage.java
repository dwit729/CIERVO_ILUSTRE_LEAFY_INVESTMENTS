package com.example.ciervo_ilustre_leafy_investments;

import static com.google.android.material.internal.ViewUtils.showKeyboard;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;
import com.chaos.view.PinView;


public class PinPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_page);

        PinView pinView = findViewById(R.id.firstPinView);
        pinView.setHideLineWhenFilled(false);
        pinView.setPasswordHidden(true);
        pinView.setAnimationEnable(true);

        LottieAnimationView leaves = findViewById(R.id.leaves);
        leaves.playAnimation();
        leaves.loop(true);


        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pin = pinView.getText().toString();
                Log.d("testing", pin);
            }
        });


    }




}

