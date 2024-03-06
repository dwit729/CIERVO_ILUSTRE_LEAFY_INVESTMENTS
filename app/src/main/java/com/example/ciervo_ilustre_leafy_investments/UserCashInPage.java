package com.example.ciervo_ilustre_leafy_investments;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserCashInPage extends AppCompatActivity {

    String receivedData;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference clientRef = db.collection("clients");

    EditText cashIn_name, cashIn_amount;
    int amount, newBalance;
    Button cashIn_button;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cash_in_page);
        cashIn_name = findViewById(R.id.CashIn_Name_Field);
        cashIn_amount = findViewById(R.id.CashIn_Amount_Field);
        cashIn_button = findViewById(R.id.CashIn_Button);

        this.receivedData = UserDashboard.receivedData;
        //Toast.makeText(this, "DOC ID" + this.receivedData,Toast.LENGTH_LONG).show();

        //TOOL BAR START
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
        //TOOL BAR END
        cashIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!cashIn_name.getText().toString().trim().isEmpty() && !cashIn_amount.getText().toString().trim().isEmpty())
                {
                    createLogs();
                    clientRef.document(receivedData).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        amount = Integer.parseInt(cashIn_amount.getText().toString());
                        newBalance = Integer.parseInt(documentSnapshot.getString("Balance")) + amount;

                        String newSBalance = String.valueOf(newBalance);
                        Map<String, Object> editBalance = new HashMap<String, Object>();
                        editBalance.put("Balance", newSBalance);
                        clientRef.document(receivedData).update(editBalance).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "CASH IN SUCCESFUL", Toast.LENGTH_LONG).show();
                                cashIn_name.setText("");
                                cashIn_amount.setText("");
                            }
                        });


                    }
                });
            }
            }
        });

    }


    public void createLogs()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        String documentNumber = receivedData.toString().trim();
        String name_cashIn = cashIn_name.getText().toString();
        String amount_cashIn = cashIn_amount.getText().toString();


        Map<String, Object> activity = new HashMap<>();
        activity.put("Activity" , "Cash In");
        activity.put("Name" , name_cashIn);
        activity.put("Amount" , amount_cashIn);
        activity.put("Time Stamp" , currentDateTime);

        clientRef.document(documentNumber).collection("activity").document().set(activity).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "CASH IN SUCCESSFUL", Toast.LENGTH_LONG).show();
                cashIn_name.setText("");
                cashIn_amount.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "di nagana CASH IN beh", Toast.LENGTH_LONG).show();

            }
        });
    }

}