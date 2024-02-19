package com.example.ciervo_ilustre_leafy_investments;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUpFragment extends Fragment {


    View view;
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if(password.getText().toString().trim().equals(c_pass.getText().toString().trim())){

    String fullName = fullname.getText().toString();
    String userName = username.getText().toString();
    String userAge = age.getText().toString();
    String emailAddress = email.getText().toString();
    String birthDay = birthday.getText().toString();
    String passWord = password.getText().toString();

    Map<String, Object> clients = new HashMap<>();
    clients.put("Name", fullName);
    clients.put("UserName", userName);
    clients.put("Password", passWord);
    clients.put("Email", emailAddress);
    clients.put("Age", userAge);
    clients.put("Birthday", birthDay);

    DocumentReference reference = db.collection("clients").document();

    db.collection("clients").add(clients)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d("success", "added" + reference.getId());
                    fullname.setText("");
                    username.setText("");
                    age.setText("");
                    email.setText("");
                    birthday.setText("");
                    password.setText("");
                    c_pass.setText("");
                    //Intent intent = new Intent(getContext().getApplicationContext(), SetPinFragment.class);
                    //startActivity(intent);

                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Log.d("failed", e.toString());
                }
            });
}
else
{
    Toast.makeText(getContext().getApplicationContext(),"Password does not match!", Toast.LENGTH_LONG).show();
}


            }
        });
        return view;
    }
}