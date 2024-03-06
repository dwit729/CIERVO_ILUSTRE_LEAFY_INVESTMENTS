package com.example.ciervo_ilustre_leafy_investments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserCalendarPage extends AppCompatActivity implements RecyclerViewInterface {
    Toolbar toolbar;
    MyAdapter myAdapter;
    ArrayList<DueDate> dueDateArrayList;
    RecyclerView recyclerView;
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


        layout = findViewById(R.id.layout);
        recyclerView = findViewById(R.id.cal_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dueDateArrayList = new ArrayList<DueDate>();
        myAdapter = new MyAdapter(UserCalendarPage.this, dueDateArrayList, this);
        recyclerView.setAdapter(myAdapter);

        documentReference = clientRef.document(this.receivedData);

        setEvents();
        EventChangeListener();


        calendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEvents();
            }
        });
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar cal = Calendar.getInstance();
                if (eventDay.getCalendar().compareTo(cal) >= 0) {
                    calendar = eventDay.getCalendar();
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    String selected = format1.format(calendar.getTime());
                    addDueDates(selected, receivedData);
                    setEvents();
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot Add to Previous Dates!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void EventChangeListener() {

        dueDateArrayList.clear();
        documentReference.collection("dueDates").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    String name = documentSnapshot.getString("Bill Name");
                    String amount = documentSnapshot.getString("Amount");
                    String category = documentSnapshot.getString("Category");
                    String date = documentSnapshot.getString("Due Date");
                    String id = documentSnapshot.getId();
                    dueDateArrayList.add(new DueDate(name, amount, category, date, id));
                    myAdapter.notifyDataSetChanged();
                }


            }
        });
    }

    public void addDueDates(String selectedDate, String receivedData) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View addPopUp = inflater.inflate(R.layout.calendar_popup_layout, null);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        PopupWindow popupWindow = new PopupWindow(addPopUp, width, height, focusable);
        layout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });

        Button cancel = addPopUp.findViewById(R.id.Cancel_Add_button);
        Button addDue = addPopUp.findViewById(R.id.Add_DueDate_Button);
        EditText bdate = addPopUp.findViewById(R.id.due_Date);
        EditText bName = addPopUp.findViewById(R.id.Bill_Name_Field);
        EditText bAmount = addPopUp.findViewById(R.id.Bill_Amount_Field);
        bdate.setEnabled(false);
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
                String amount = bAmount.getText().toString();
                String category = category_spin.getSelectedItem().toString();

                Map<String, Object> dueDates = new HashMap<>();
                dueDates.put("Bill Name", billName);
                dueDates.put("Due Date", selectedDate);
                dueDates.put("Amount", amount);
                dueDates.put("Category", category);

                clientRef.document(receivedData).collection("dueDates").document().set(dueDates).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                        popupWindow.dismiss();
                        setEvents();
                        EventChangeListener();

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

    public void processDueDates(String ID) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View addPopUp = inflater.inflate(R.layout.duedate_edit_popup, null);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        PopupWindow popupWindow = new PopupWindow(addPopUp, width, height, focusable);
        layout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });

        EditText editDate = addPopUp.findViewById(R.id.due_Date_edit);
        EditText editBName = addPopUp.findViewById(R.id.Bill_Name);
        EditText editBAmount = addPopUp.findViewById(R.id.Bill_Amount);
        Button delete_button = addPopUp.findViewById(R.id.Delete_DueDate_Button);
        Button pay_button = addPopUp.findViewById(R.id.Pay_DueDate_Button);
        Button update_button = addPopUp.findViewById(R.id.Edit_DueDate_Button);
        Button cancel_button = addPopUp.findViewById(R.id.Cancel_edit_button);
        Spinner category_spin = addPopUp.findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Bill_Categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spin.setAdapter(adapter);

        String dueAmount;

        String[] categories = getResources().getStringArray(R.array.Bill_Categories);

        Log.d("DEBUGGING", ID);

        clientRef.document(receivedData).collection("dueDates").document(ID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                editDate.setText(documentSnapshot.getString("Due Date"));
                editBName.setText(documentSnapshot.getString("Bill Name"));
                category_spin.setSelection(Arrays.asList(categories).indexOf(documentSnapshot.getString("Category")));
                editBAmount.setText(documentSnapshot.getString("Amount"));

            }
        });


        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientRef.document(receivedData).collection("dueDates").document(ID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "DELETED SUCCESFULLY", Toast.LENGTH_LONG).show();
                        setEvents();
                        EventChangeListener();
                        popupWindow.dismiss();
                    }
                });
            }
        });
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editBName.getText().toString();
                String amount = editBAmount.getText().toString();
                String category = category_spin.getSelectedItem().toString();
                String date = editDate.getText().toString();

                Map<String, Object> editDueDates = new HashMap<String, Object>();
                editDueDates.put("Bill Name", name);
                editDueDates.put("Due Date", date);
                editDueDates.put("Amount", amount);
                editDueDates.put("Category", category);

                clientRef.document(receivedData).collection("dueDates").document(ID).update(editDueDates).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "UPDATED SUCCESFULLY", Toast.LENGTH_LONG).show();
                        setEvents();
                        EventChangeListener();
                        popupWindow.dismiss();
                    }
                });


            }
        });
        pay_button.setOnClickListener(new View.OnClickListener() {

            int dueAmount, newBalance;

            @Override
            public void onClick(View v) {

                clientRef.document(receivedData).collection("dueDates").document(ID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        dueAmount = Integer.parseInt(documentSnapshot.getString("Amount"));
                    }
                });
                clientRef.document(receivedData).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (Integer.parseInt(documentSnapshot.getString("Balance")) >= dueAmount) {
                            newBalance = Integer.parseInt(documentSnapshot.getString("Balance")) - dueAmount;
                            String newSBalance = String.valueOf(newBalance);
                            Map<String, Object> editBalance = new HashMap<String, Object>();
                            editBalance.put("Balance", newSBalance);
                            clientRef.document(receivedData).update(editBalance);

                            clientRef.document(receivedData).collection("dueDates").document(ID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    createLogs(documentSnapshot.getString("Bill Name"), Integer.parseInt(documentSnapshot.getString("Amount")));
                                    Toast.makeText(getApplicationContext(), "PAID SUCCESFULLY", Toast.LENGTH_LONG).show();
                                }
                            });
                            setEvents();
                            EventChangeListener();
                            popupWindow.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "BALANCE INSUFFICIENT SUCCESFULLY", Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });


    }

    public void setEvents() {
        List<EventDay> events = new ArrayList<>();
        documentReference.collection("dueDates").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
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
                    calendar1.set(year, month - 1, day);
                    events.add(new EventDay(calendar1, R.drawable.imagebuttonstyle, Color.parseColor("#228B22")));


                }


                calendarView.setEvents(events);
            }
        });


    }

    @Override
    public void onItemClick(int position) {
        processDueDates(dueDateArrayList.get(position).getId());
    }

    public void createLogs(String name, int amount)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        String documentNumber = receivedData.toString().trim();


        Map<String, Object> activity = new HashMap<>();
        activity.put("Activity" , "Cash Out");
        activity.put("Name" , name);
        activity.put("Amount" , String.valueOf(amount));
        activity.put("Time Stamp" , currentDateTime);

        clientRef.document(documentNumber).collection("activity").document().set(activity).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "CASH OUT SUCCESSFUL", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "di nagana CASH OUT beh", Toast.LENGTH_LONG).show();

            }
        });
    }
}

