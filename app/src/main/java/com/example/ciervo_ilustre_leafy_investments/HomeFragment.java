package com.example.ciervo_ilustre_leafy_investments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.User;

import java.util.concurrent.Executor;


public class HomeFragment extends Fragment {
    View view;
    Button calendar_button, cashOut_button, cashIn_button, history_button, wishList_button, analytics_button;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference clients = db.collection("clients");
    TextView balanceView;
    ImageView dashboardgif;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_home, container, false);

        balanceView = view.findViewById(R.id.Balance_TextView);
        calendar_button = view.findViewById(R.id.calendar_button);
        wishList_button = view.findViewById(R.id.wishlist_button);
        analytics_button = view.findViewById(R.id.analytics_button);
        cashIn_button = view.findViewById(R.id.cash_in_button);
        cashOut_button = view.findViewById(R.id.cash_out_button);
        history_button = view.findViewById(R.id.history_logs_button);

        dashboardgif = view.findViewById(R.id.imageView2);

        Intent intent = getActivity().getIntent();
        if(intent != null) {
            UserDashboard.receivedData = intent.getStringExtra("document_ID");
        }


        calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext().getApplicationContext(), UserCalendarPage.class);
                startActivity(in);
            }
        });
        wishList_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext().getApplicationContext(), UserWishListPage.class);
                startActivity(in);
            }
        });
        analytics_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext().getApplicationContext(), UserAnalyticsPage.class);
                startActivity(in);
            }
        });
        cashIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext().getApplicationContext(), UserCashInPage.class);
                startActivity(in);
            }
        });
        cashOut_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext().getApplicationContext(), UserCashOutPage.class);
                startActivity(in);
            }
        });
        history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext().getApplicationContext(), UserTransactionHistory.class);
                startActivity(in);
            }
        });






        if(UserDashboard.receivedData==null)
        {

        }
        else
        {
            DocumentReference documentReference = clients.document(UserDashboard.receivedData);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    balanceView.setText(documentSnapshot.getString("Balance"));
                    int Balance = Integer.parseInt(documentSnapshot.getString("Balance"));
                    int Target = Integer.parseInt(documentSnapshot.getString("TargetSavings"));

                    if((Target/3)>Balance)
                    {
                        Glide.with(getContext().getApplicationContext()).asGif().load(R.raw.dashboard_tree_1).into(dashboardgif);
                    }
                    else if((2*Target/3)>Balance)
                    {
                        Glide.with(getContext().getApplicationContext()).asGif().load(R.raw.dashboard_tree_2).into(dashboardgif);
                    }
                    else if((4*Target/5)<=Balance)
                    {
                        Glide.with(getContext().getApplicationContext()).asGif().load(R.raw.dashboard_tree_3).into(dashboardgif);
                    }
                }
            });
        }



        return view;
    }



}