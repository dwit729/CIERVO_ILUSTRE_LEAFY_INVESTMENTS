package com.example.ciervo_ilustre_leafy_investments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

public class LoadingScreen extends Dialog {

    ImageView loadingIcon;
    public LoadingScreen(@NonNull Context context) {
        super(context);


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setTitle("Loading...");
        setCancelable(false);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(R.layout.loading_layout,null);
        loadingIcon = view.findViewById(R.id.loading_icon);
        setContentView(view);
        Glide.with(view).asGif().load(R.drawable.loading_screen_seed_2).into(loadingIcon);
    }
}
