package com.example.ciervo_ilustre_leafy_investments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FragmentUserSignUp extends Fragment {

    View view;
    FirebaseFirestore db;
    EditText birthday;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_user_sign_up, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //INSERT CODE HERE
        TextInputLayout passLayout = view.findViewById(R.id.passLayout);
        Button signUp = view.findViewById(R.id.signup_button);
        Button bday_picker = view.findViewById(R.id.birthday_picker_button);
        EditText fullname = view.findViewById(R.id.name_signup);
        EditText username = view.findViewById(R.id.username_signup);
        EditText email = view.findViewById(R.id.email_signup);
        birthday = view.findViewById(R.id.birthday_signUp);
        birthday.setEnabled(false);
        EditText password = view.findViewById(R.id.password_signup);
        EditText c_pass = view.findViewById(R.id.confirm_password_signup);
        EditText t_savings = view.findViewById(R.id.targetsavings_signup);
        PinView set_Pin = view.findViewById(R.id.setPinView);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String pass = s.toString();
                    if(pass.length()>= 8)
                    {
                        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
                        Matcher matcher = pattern.matcher(s);
                        boolean isStrong = matcher.find();
                        if(isStrong)
                        {
                            passLayout.setHelperText("STRONG PASS");
                            passLayout.setError(null);
                        }
                        else
                        {
                            passLayout.setHelperText("WEAK BRO");
                            passLayout.setError("Weak Password");
                        }
                    }
                    else
                    {
                        passLayout.setHelperText("Enter Minimum 8 Character");
                        passLayout.setError("too small");
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        bday_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(view);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!fullname.getText().toString().isEmpty()&&
                        !username.getText().toString().isEmpty()&&
                        !email.getText().toString().isEmpty()&&
                        !birthday.getText().toString().isEmpty()&&
                        !password.getText().toString().isEmpty()&&
                        !c_pass.getText().toString().isEmpty()&&
                        !set_Pin.getText().toString().isEmpty()&&
                        !t_savings.getText().toString().isEmpty()&&
                        fullname.getError()!=null&&
                        username.getError()!=null&&
                        email.getError()!=null&&
                        password.getError()!=null&&
                        c_pass.getError()!=null)
                {
                if (password.getText().toString().trim().equals(c_pass.getText().toString().trim())) {

                    String fullName = fullname.getText().toString();
                    String userName = username.getText().toString();
                    String emailAddress = email.getText().toString();
                    String birthDay = birthday.getText().toString();
                    String passWord = password.getText().toString();
                    String pinUser = set_Pin.getText().toString();
                    String tSavings = t_savings.getText().toString();

                    Map<String, Object> clients = new HashMap<>();
                    clients.put("Name", fullName);
                    clients.put("UserName", userName);
                    clients.put("Password", passWord);
                    clients.put("Email", emailAddress);
                    clients.put("Birthday", birthDay);
                    clients.put("PIN", pinUser);
                    clients.put("TargetSavings", tSavings);
                    clients.put("Balance", "0");

                    DocumentReference reference = db.collection("clients").document();

                    db.collection("clients").add(clients)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("success", "added" + reference.getId());
                                    String document_ID = reference.getId();
                                    fullname.setText("");
                                    username.setText("");
                                    email.setText("");
                                    birthday.setText("");
                                    password.setText("");
                                    c_pass.setText("");
                                    set_Pin.setText("");
                                    t_savings.setText("");

                                    Intent intent = new Intent(getContext().getApplicationContext(), Splash_Screen.class);
                                    startActivity(intent);
                                    getActivity().finish();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Log.d("failed", e.toString());
                                }
                            });
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "Password does not match!", Toast.LENGTH_LONG).show();
                }

            }
                else
                {
                    Toast.makeText(getContext().getApplicationContext(), "INVALID INPUT", Toast.LENGTH_LONG).show();
                }

            }
        });




        return view;
    }


    public void showDatePicker(View view1)
    {
        DatePickerDialog dialog = new DatePickerDialog(view1.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = format.format(new Date(year-1900,month,dayOfMonth));

                birthday.setText(date);

            }
        }, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());

        dialog.show();
    }





}