package com.example.ciervo_ilustre_leafy_investments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CalendarPage extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
Button addEvent;
String receivedData, docID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_page);
        addEvent= findViewById(R.id.addBtn);
        Intent intent = getIntent();
        if(intent != null) {
            receivedData = intent.getStringExtra("document_ID");
            Toast.makeText(getApplicationContext(), "Document Id = " + receivedData, Toast.LENGTH_LONG).show();

        }
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDocId();
            }
        });

    }

    private void getDocId()
    {
        DocumentReference docRef = db.collection("clients").document(receivedData);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists())
                    {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Intent intent = new Intent(getApplicationContext(), CalendarEventSetter.class);
                        intent.putExtra("document_ID", document.getId());
                        startActivity(intent);
                    }
                    else
                    {
                        Log.d(TAG, "No such document");
                    }
                }
                else
                {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}