package com.example.ciervo_ilustre_leafy_investments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class HomeFragment extends Fragment {

    View view;
    Button calendar_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_home, container, false);

        calendar_button = view.findViewById(R.id.calendar_button);
        ImageView dashboardgif = view.findViewById(R.id.imageView2);
        Glide.with(this).asGif().load(R.raw.dashboard_tree_1).into(dashboardgif);



        calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext().getApplicationContext(), UserCalendarPage.class);
                startActivity(in);
            }
        });


        return view;
    }



}