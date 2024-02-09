package com.example.ciervo_ilustre_leafy_investments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaos.view.PinView;

public class PinFragment extends Fragment {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_pin, container, false);
        PinView pinView = view.findViewById(R.id.firstPinView);

        pinView.setHideLineWhenFilled(false);
        pinView.setPasswordHidden(true);
        pinView.setAnimationEnable(true);



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

        return view;




    }



    }



