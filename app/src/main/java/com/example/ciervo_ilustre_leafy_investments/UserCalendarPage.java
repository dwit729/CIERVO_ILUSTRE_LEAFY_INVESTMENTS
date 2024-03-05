package com.example.ciervo_ilustre_leafy_investments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import  androidx.appcompat.widget.Toolbar;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.StringValue;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserCalendarPage extends AppCompatActivity {
    Toolbar toolbar;
    Date selected_date;
    CalendarView calendarView;
    String receivedData;
    LinearLayout layout;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference clientRef = db.collection("clients");


    Calendar calendar = Calendar.getInstance();
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_calendar_page);
        this.receivedData = UserDashboard.receivedData;
        calendarView = findViewById(R.id.user_calendar);
         toolbar= findViewById(R.id.calendar_toolbar);
         layout = findViewById(R.id.layout);
         setSupportActionBar(toolbar);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        documentReference= clientRef.document(this.receivedData);

       setEvents();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });



        calendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEvents();
            }
        });
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                calendar =eventDay.getCalendar();
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                String selected = format1.format(calendar.getTime());
                addDueDates(selected, receivedData);
                setEvents();
            }
        });

    }

    public void addDueDates(String selectedDate, String receivedData)
    {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View addPopUp = inflater.inflate(R.layout.calendar_popup_layout, null);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        PopupWindow popupWindow = new PopupWindow(addPopUp,width,height,focusable);
        layout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(layout, Gravity.CENTER,0,0);
            }
        });

        Button cancel = addPopUp.findViewById(R.id.Cancel_Add_button);
        Button addDue = addPopUp.findViewById(R.id.Add_DueDate_Button);
        EditText bdate = addPopUp.findViewById(R.id.due_Date);
        EditText bName = addPopUp.findViewById(R.id.Bill_Name_Field);
        EditText bAmount = addPopUp.findViewById(R.id.Bill_Amount_Field);
        Spinner category_spin = addPopUp.findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Bill_Categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spin.setAdapter(adapter);
        bdate.setText(selectedDate);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                setEvents();
            }
        });
        addDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String billName = bName.getText().toString();
                String amount= bAmount.getText().toString();
                String date = selectedDate;
                String documentNumber = receivedData;
                String category = category_spin.getSelectedItem().toString();

                Map<String, Object> dueDates = new HashMap<>();
                dueDates.put("Bill Name" , billName);
                dueDates.put("Due Date" , date);
                dueDates.put("Amount" , amount);
                dueDates.put("Category" , category);

                clientRef.document(documentNumber).collection("dueDates").document().set(dueDates).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                        popupWindow.dismiss();
                        setEvents();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "di nagana beh", Toast.LENGTH_LONG).show();

                    }
                });

            }
        });


    }

    public void setEvents()
    {
        List<EventDay> events = new ArrayList<>();
        documentReference.collection("dueDates").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    int year = 0;
                    int month = 0;
                    int day = 0;
                    String date = documentSnapshot.getString("Due Date").trim();
                    String name = documentSnapshot.getString("Bill Name").trim();
                    String category = documentSnapshot.getString("Category").trim();
                    String amount = documentSnapshot.getString("Amount").trim();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate dateTime = LocalDate.parse(date, formatter);
                    year = dateTime.getYear();
                    month = dateTime.getMonthValue();
                    day = dateTime.getDayOfMonth();
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.clear();
                    calendar1.set(year, month-1, day);
                    events.add(new EventDay(calendar1,R.drawable.imagebuttonstyle, Color.parseColor("#228B22")));


                }


                calendarView.setEvents(events);
            }
        });


    }

}