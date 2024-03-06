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

import java.util.HashMap;
import java.util.Map;

public class PreferencesFragment extends Fragment {


    View view;

    String state = "not editing";

    EditText balanceEdit, targetEdit;
    Button editPrefButton, submitPrefButton;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference clientRef = db.collection("clients");
    DocumentReference documentReference;
    String ID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        documentReference= clientRef.document(UserDashboard.receivedData);
        ID = UserDashboard.receivedData;
        state = "not editing";
        view = inflater.inflate(R.layout.fragment_preferences, container, false);
        balanceEdit = view.findViewById(R.id.balance_edit);
        targetEdit = view.findViewById(R.id.target_savings_edit);
        editPrefButton = view.findViewById(R.id.edit_pref_button);
        submitPrefButton = view.findViewById(R.id.submit_button);
        disableComponents();



        clientRef.document(UserDashboard.receivedData).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                balanceEdit.setText(documentSnapshot.getString("Balance"));
                targetEdit.setText(documentSnapshot.getString("TargetSavings"));
            }
        });

        editPrefButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableComponents();
                if(state.equals("not editing")) {
                    enableComponents();
                    editPrefButton.setEnabled(false);
                    state = "editing";
                }

            }
        });

        submitPrefButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state.equals("editing"))
                {
                    String balance = balanceEdit.getText().toString();
                    String target = targetEdit.getText().toString();

                    Map<String, Object> update = new HashMap<String, Object>();
                    update.put("Balance", balance);
                    update.put("TargetSavings", target);

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
        balanceEdit.setEnabled(false);
        targetEdit.setEnabled(false);

    }

    public void enableComponents()
    {
        balanceEdit.setEnabled(true);
        targetEdit.setEnabled(true);

    }
}