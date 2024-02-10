package com.example.ciervo_ilustre_leafy_investments;

import static android.content.ContentValues.TAG;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUpFragment extends Fragment {

    View view;
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        //Firebase Firestore Integration
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //INSERT CODE HERE
        Button signUp = view.findViewById(R.id.signUp);
        EditText fullname = view.findViewById(R.id.name);
        EditText username = view.findViewById(R.id.username);
        EditText email = view.findViewById(R.id.email);
        EditText age = view.findViewById(R.id.age);
        EditText birthday = view.findViewById(R.id.birthday);
        EditText password = view.findViewById(R.id.password);
        EditText c_pass = view.findViewById(R.id.confirmPass);
try{
    signUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String fullName = fullname.getText().toString();
            String userName = username.getText().toString();
            String userAge = age.getText().toString();
            String emailAddress = email.getText().toString();
            String birthDay = birthday.getText().toString();
            String passWord = password.getText().toString();

            Map<String,Object> clients = new HashMap<>();
            clients.put("Name",fullName);
            clients.put("UserName", userName);
            clients.put("Password", passWord);
            clients.put("Email",emailAddress);
            clients.put("Age",userAge);
            clients.put("Birthday",birthDay);

            db.collection("clients").document("KerujiMartin").set(clients)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext().getApplicationContext(), "Note saved", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext().getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });

//                    .add(clients)
//                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                            Toast.makeText(getContext().getApplicationContext(), "Client has been added!", Toast.LENGTH_LONG).show();
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(getContext().getApplicationContext(), "Client was not added.", Toast.LENGTH_LONG).show();
//
//                        }
//                    });

        }
    });
}
catch(Exception e)
{
    Log.d("ERROR", e.toString());
}



        return view;
    }


}


