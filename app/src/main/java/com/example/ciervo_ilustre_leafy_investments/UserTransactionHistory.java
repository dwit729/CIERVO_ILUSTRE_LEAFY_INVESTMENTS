package com.example.ciervo_ilustre_leafy_investments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;



public class UserTransactionHistory extends AppCompatActivity {

    Toolbar toolbar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference clientRef = db.collection("clients");
    DocumentReference documentReference;
    String receivedData;
    ArrayList<ActLogs>  data = new ArrayList<ActLogs>();;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_transaction_history);
        recyclerView = findViewById(R.id.logsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyListAdapter adapter = new MyListAdapter(this, data);
        this.receivedData = UserDashboard.receivedData;
        documentReference = clientRef.document(this.receivedData);
        documentReference.collection("activity").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                    String date = documentSnapshot.getString("Time Stamp").trim();
                    String name = documentSnapshot.getString("Name").trim();
                    String category = documentSnapshot.getString("Activity").trim();
                    String amount = documentSnapshot.getString("Amount").trim();
                    data.add(new ActLogs(name, amount, category, date));
                    adapter.notifyDataSetChanged();

                }

                Log.d("DEBUGGING", String.valueOf(data.size()));
            }

        });


        toolbar = findViewById(R.id.calendar_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });


        recyclerView.setAdapter(adapter);
    }


    public void loadData()
    {

    }



}