package com.example.ciervo_ilustre_leafy_investments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class AccountFragment extends Fragment {
    View view;
    String state = "not editing";
    EditText nameEdit, userNameEdit, emailEdit, birthdayEdit;
    Button datepicker, submitButton, editaccButton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference clientRef = db.collection("clients");
    DocumentReference documentReference;
    String ID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        documentReference= clientRef.document(UserDashboard.receivedData);
        ID = UserDashboard.receivedData;
        state = "not editing";
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);
        nameEdit = view.findViewById(R.id.name_edit);
        userNameEdit = view.findViewById(R.id.username_edit);
        emailEdit = view.findViewById(R.id.email_edit);
        birthdayEdit = view.findViewById(R.id.birthday_edit);
        datepicker =  view.findViewById(R.id.datePicker);
        submitButton = view.findViewById(R.id.submit_edit);
        editaccButton = view.findViewById(R.id.edit_acc_button);

        disableComponents();

        clientRef.document(UserDashboard.receivedData).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                nameEdit.setText(documentSnapshot.getString("Name"));
                userNameEdit.setText(documentSnapshot.getString("UserName"));
                birthdayEdit.setText(documentSnapshot.getString("Birthday"));
                emailEdit.setText(documentSnapshot.getString("Email"));
            }
        });

        editaccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(state.equals("not editing")) {
                    enableComponents();
                    editaccButton.setEnabled(false);
                    state = "editing";
                }

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state.equals("editing"))
                {
                    String name = nameEdit.getText().toString();
                    String username = userNameEdit.getText().toString();
                    String email = emailEdit.getText().toString();
                    String bday = birthdayEdit.getText().toString();

                    Map<String, Object> update = new HashMap<String, Object>();
                    update.put("Name", name);
                    update.put("Email", email);
                    update.put("UserName", username);
                    update.put("Birthday", bday);

                    clientRef.document(UserDashboard.receivedData).update(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext().getApplicationContext(), "UPDATED SUCCESFULLY", Toast.LENGTH_LONG).show();
                            disableComponents();
                            state = "not editing";
                        }
                    });
                }
                else
                {
                    Toast.makeText(getContext().getApplicationContext(), "PRESS EDIT BUTTON", Toast.LENGTH_LONG).show();
                }
            }
        });










        return view;
    }

    public void disableComponents()
    {
        nameEdit.setEnabled(false);
        userNameEdit.setEnabled(false);
        emailEdit.setEnabled(false);
        birthdayEdit.setEnabled(false);
        datepicker.setEnabled(false);

    }

    public void enableComponents()
    {
        nameEdit.setEnabled(true);
        userNameEdit.setEnabled(true);
        emailEdit.setEnabled(true);
        datepicker.setEnabled(true);

    }

}