package com.example.ciervo_ilustre_leafy_investments;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CalendarEventSetter extends AppCompatActivity {

    CalendarView calendarView;
    Calendar calendar;
    String receivedData;
    EditText setDate, setBillerName, setAmount;
    Button addEvent;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference clientRef = db.collection("clients");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_event_setter);
        //Initializes the components
        calendarView = findViewById(R.id.calendarView);
        calendar = Calendar.getInstance();
        setDate = findViewById(R.id.dateOfBill);
        setBillerName = findViewById(R.id.nameOfBill);
        setAmount = findViewById(R.id.amountOfBill);
        addEvent = findViewById(R.id.addDueDate);

        //get the intent and the data passed
        Intent intent = getIntent();
        if(intent != null) {
            receivedData = intent.getStringExtra("document_ID");
            Toast.makeText(getApplicationContext(), "Document Id = " + receivedData, Toast.LENGTH_LONG).show();
        }

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String billName = setBillerName.getText().toString();
                String date = setDate.getText().toString();
                String amount = setAmount.getText().toString();
                String documentNumber = receivedData.toString().trim();

                Map<String, Object> dueDates = new HashMap<>();
                dueDates.put("Bill Name" , billName);
                dueDates.put("Due Date" , date);
                dueDates.put("Amount" , amount);

                clientRef.document(documentNumber).collection("dueDates").document().set(dueDates).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "pwede ka na matulog", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "di nagana beh", Toast.LENGTH_LONG).show();

                    }
                });


         }
        });



calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//        Toast.makeText(getApplicationContext(), month + "/" + dayOfMonth + "/" + year +"/", Toast.LENGTH_LONG).show();
        setDate.setText(month + "/" + dayOfMonth + "/" + year );
    }
});



    }
}