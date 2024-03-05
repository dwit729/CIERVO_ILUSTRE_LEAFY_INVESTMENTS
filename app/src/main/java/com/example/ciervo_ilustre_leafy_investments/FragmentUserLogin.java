package com.example.ciervo_ilustre_leafy_investments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class FragmentUserLogin extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference clientsRef = db.collection("clients");
    private DocumentReference clientsDoc = db.document("");

    View view;
    TextInputEditText emailAddress;
    TextInputEditText passWord;
    private LoadingScreen loadingScreen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            view = inflater.inflate(R.layout.fragment_user_login, container, false);
            loadingScreen = new LoadingScreen(this.getContext());
            emailAddress = view.findViewById(R.id.email_login_field);
            passWord= view.findViewById(R.id.password_login_field);

        Button loginbutton = view.findViewById(R.id.login_button);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(emailAddress.getText().toString().isEmpty()||passWord.getText().toString().isEmpty())
                {
                    //NILAGYAN KO LANG NG ONTING VALIDATIONSZZZ
                    Toast.makeText(getContext().getApplicationContext(), "PLEASE ENTER CREDENTIALS!", Toast.LENGTH_LONG).show();
                }
                else{
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

                                                    loadingScreen.show();
                                                    Handler handler = new Handler();
                                                    Runnable runnable = new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                                            Toast.makeText(getContext().getApplicationContext(), "CREDENTIALS MATCH!", Toast.LENGTH_LONG).show();
                                                            Intent intent = new Intent(getContext().getApplicationContext(),UserDashboard.class);
                                                            intent.putExtra("document_ID", document.getId());
                                                            emailAddress.setText("");
                                                            passWord.setText("");
                                                            startActivity(intent);
                                                        }
                                                    };
                                                    handler.postDelayed(runnable, 5000);



                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                        Toast.makeText(getContext().getApplicationContext(), "CREDENTIALS DOES NOT MATCH!", Toast.LENGTH_LONG).show();
                                        emailAddress.setText("");
                                        passWord.setText("");
                                    }
                                }
                            });}

            }
        });


        return view;
    }


}