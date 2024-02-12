package com.example.ciervo_ilustre_leafy_investments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference clientRef = db.collection("clients");
    private DocumentReference clientsDoc = db.document("");
    View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        EditText emailAddress = view.findViewById(R.id.email);
        EditText passWord = view.findViewById(R.id.password);
        Button logInBtn = view.findViewById(R.id.logIn);

        //INSERT CODE HERE
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e_mail = emailAddress.getText().toString().trim();
                String p_word = passWord.getText().toString().trim();
                db.collection("clients")
                        .whereEqualTo("Email", e_mail.toString()).whereEqualTo("Password", p_word.toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        Toast.makeText(getContext().getApplicationContext(), "CREDENTIALS MATCH!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getContext().getApplicationContext(),DashBoard.class);
                                        startActivity(intent);
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                    Toast.makeText(getContext().getApplicationContext(), "CREDENTIALS DOES NOT MATCH!", Toast.LENGTH_LONG).show();
                                    emailAddress.setText("");
                                    passWord.setText("");
                                }
                            }
                        });

            }
        });


        return view;
    }



}