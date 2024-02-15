package com.example.ciervo_ilustre_leafy_investments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class DashBoard extends AppCompatActivity {

    String receivedData;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference clients = db.collection("clients");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dash_board);
        EditText name = findViewById(R.id.name);
        EditText username = findViewById(R.id.username);
        EditText age = findViewById(R.id.age);
        EditText birthday = findViewById(R.id.birthday);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button edit = findViewById(R.id.editBtn);
        Button update = findViewById(R.id.updateBtn);

        allowEditing();

        //to get the email for reference in the database na galing sa log in page
        Intent intent = getIntent();
        if(intent != null) {
           receivedData = intent.getStringExtra("document_ID");
            //email.setText(receivedData); //PANG CHECK LANG TO KUNG NAPAPASA NGA YUNG DOCUMENT ID, KASI YUN YUNG GAGAMITIN KO PANG RETRIEVE :>
        }

        DocumentReference documentReference = clients.document(receivedData);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText(value.getString("Name"));
                username.setText(value.getString("UserName"));
                age.setText(value.getString("Age"));
                birthday.setText(value.getString("Birthday"));
                email.setText(value.getString("Email"));
                password.setText(value.getString("Password"));
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updated_name = name.getText().toString().trim();
                String updated_username = username.getText().toString().trim();
                String updated_age = age.getText().toString().trim();
                String updated_birthday = birthday.getText().toString().trim();
                String updated_email = email.getText().toString().trim();
                String updated_password = password.getText().toString().trim();

                Map<String, Object> update_data = new HashMap<>();
                update_data.put("Name", updated_name);
                update_data.put("UserName", updated_username);
                update_data.put("Age", updated_age);
                update_data.put("Birthday", updated_birthday);
                update_data.put("Email", updated_email);
                update_data.put("Password", updated_password);

                clients.document(receivedData).update(update_data);
                Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_LONG).show();
                loadNewData();

            }
        });

    }

    private void allowEditing()
    {
        Button edit = findViewById(R.id.editBtn);
        EditText name = findViewById(R.id.name);
        EditText username = findViewById(R.id.username);
        EditText age = findViewById(R.id.age);
        EditText birthday = findViewById(R.id.birthday);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setEnabled(true);
                username.setEnabled(true);;
                age.setEnabled(true);;
                birthday.setEnabled(true);;
                email.setEnabled(true);;
                password.setEnabled(true);;
            }
        });
    }

    private void loadNewData()
    {
        EditText name = findViewById(R.id.name);
        EditText username = findViewById(R.id.username);
        EditText age = findViewById(R.id.age);
        EditText birthday = findViewById(R.id.birthday);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);

        DocumentReference documentReference = clients.document(receivedData);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText(value.getString("Name"));
                username.setText(value.getString("UserName"));
                age.setText(value.getString("Age"));
                birthday.setText(value.getString("Birthday"));
                email.setText(value.getString("Email"));
                password.setText(value.getString("Password"));
            }
        });
        name.setEnabled(false);
        username.setEnabled(false);;
        age.setEnabled(false);;
        birthday.setEnabled(false);;
        email.setEnabled(false);;
        password.setEnabled(false);;

    }
}