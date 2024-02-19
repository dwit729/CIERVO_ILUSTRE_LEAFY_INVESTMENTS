package com.example.ciervo_ilustre_leafy_investments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

public class CalendarPage extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CalendarView calendarView;
    Calendar calendar;
    String receivedData;
    EditText setDate, setBillerName, setAmount;
    Button addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_page);
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
            //Toast.makeText(getApplicationContext(), "Document Id = " + receivedData, Toast.LENGTH_LONG).show();
        }

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String billName = setBillerName.getText().toString();
                String date = setDate.getText().toString();
                String amount = setAmount.getText().toString();

                DocumentReference documentRef = db.collection("clients").document(receivedData);

                Map<String, Object> dueDates = new HashMap<>();
                dueDates.put("Bill Name" , billName);
                dueDates.put("Due Date" , date);
                dueDates.put("Amount" , amount);

                WriteBatch batch = db.batch();

                CollectionReference dueDatesRef = documentRef.collection("dueDates");
                DocumentReference newDocRef = dueDatesRef.document();
                batch.set(newDocRef, dueDates);

                batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Subcollection added successfully", Toast.LENGTH_LONG).show();
                                setBillerName.setText("");
                                setDate.setText("");
                                setAmount.setText("");
                        } else {
                            Log.w(TAG, "Error adding subcollection", task.getException());
                        }
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