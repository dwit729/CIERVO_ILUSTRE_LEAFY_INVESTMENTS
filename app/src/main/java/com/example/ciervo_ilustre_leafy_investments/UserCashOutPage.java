package com.example.ciervo_ilustre_leafy_investments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserCashOutPage extends AppCompatActivity {

    EditText cashOut_name, cashOut_amount;
    Button cashOut_button;
    Toolbar toolbar;
    String receivedData;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference clientRef = db.collection("clients");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cash_out_page);
        cashOut_name = findViewById(R.id.CashOut_Name_Field);
        cashOut_amount = findViewById(R.id.CashOut_Amount_Field);
        cashOut_button = findViewById(R.id.CashOut_Button);

        this.receivedData = UserDashboard.receivedData;

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

        cashOut_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDateTime = dateFormat.format(new Date());
                String documentNumber = receivedData.toString().trim();
                String name_cashOut = cashOut_name.getText().toString();
                int amount_cashOut = Integer.parseInt(cashOut_amount.getText().toString());

                Map<String, Object> activity = new HashMap<>();
                activity.put("Activity" , "Cash Out");
                activity.put("Name" , name_cashOut);
                activity.put("Amount" , amount_cashOut);
                activity.put("Time Stamp" , currentDateTime);

                clientRef.document(documentNumber).collection("activity").document().set(activity).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "CASH OUT SUCCESSFUL", Toast.LENGTH_LONG).show();
                        cashOut_name.setText("");
                        cashOut_amount.setText("");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "di nagana CASH OUT beh", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });


    }
}