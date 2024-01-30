package com.example.ciervo_ilustre_leafy_investments;

import static com.google.android.material.internal.ViewUtils.showKeyboard;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.EventLog;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);//para to sa keypad

        initializedComponents(); //pinag isa ko na lahat ng may find by kineme
        passNumbers();//para maglipat lipat nung bilog haha
        deletePin();//para ma clear yung array list
        storePin(eText4);//testing lang to para makita ko kung nag store talaga yung data
        //nilagay ko to para pag open ng app naka focus agas sa first digit
        eText1.requestFocus();
        InputMethodManager show = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        show.showSoftInput(eText1,InputMethodManager.SHOW_IMPLICIT);

    }


    private EditText editText, eText1, eText2, eText3, eText4;
    private KeyEvent event;
    ArrayList<String> pin_code = new ArrayList<>();//for the store nung pin na ieenter ni user

    private void initializedComponents() //dito lahat ng values para sa Views
    {
        eText1 = (EditText) findViewById(R.id.digit_1);
        eText2 = (EditText) findViewById(R.id.digit_2);
        eText3 = (EditText) findViewById(R.id.digit_3);
        eText4 = (EditText) findViewById(R.id.digit_4);

    }

    private void storePin(EditText editText)
    {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    Toast.makeText(MainActivity.this, "CODE: " + pin_code, Toast.LENGTH_SHORT).show();
                    //nilagay ko lang to dito para makita kung na sstore talaga sa arraylist yung numbers
                    return true;
                }
                return false;
            }
        });
    }

    private void deletePin()
    {

        eText1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (eText1.getText().length() == 1) {
                        eText1.setBackgroundResource(R.drawable.grey_circle);
                        eText1.setText(null);
                        eText1.requestFocus();
                        pin_code.remove(0);
                        return true;
                    }
                }
                return false;
            }
        });
        eText2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (eText2.getText().length() == 1) {
                        eText2.setBackgroundResource(R.drawable.grey_circle);
                        eText2.setText(null);
                        eText1.setEnabled(true);
                        eText2.setEnabled(false);
                        eText3.setEnabled(false);
                        eText4.setEnabled(false);
                        eText1.requestFocus();
                        InputMethodManager show = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        show.showSoftInput(eText1,InputMethodManager.SHOW_IMPLICIT);
                        pin_code.remove(1);
                        return true;
                    }
                }
                return false;
            }
        });
        eText3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (eText3.getText().length() == 1) {
                        eText3.setBackgroundResource(R.drawable.grey_circle);
                        eText3.setText(null);
                        eText2.setEnabled(true);
                        eText3.setEnabled(false);
                        eText4.setEnabled(false);
                        eText2.requestFocus();
                        InputMethodManager show = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        show.showSoftInput(eText2,InputMethodManager.SHOW_IMPLICIT);
                        pin_code.remove(2);
                        return true;
                    }
                }
                return false;
            }
        });
        eText4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (eText4.getText().length() == 1) {
                        eText4.setBackgroundResource(R.drawable.grey_circle);
                        eText4.setText(null);
                        eText4.setEnabled(false);
                        eText3.setEnabled(true);
                        eText3.requestFocus();
                        InputMethodManager show = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        show.showSoftInput(eText3,InputMethodManager.SHOW_IMPLICIT);
                        pin_code.remove(3);
                        return true;
                    }
                }
                return false;
            }
        });

    }
    private void passNumbers() //para pag nag text watcher dito na lang kukunin lahat ng listener
    {

        eText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(eText1.getText().toString().length()==1)
                {
                    pin_code.add(eText1.getText().toString());
                    eText1.setBackgroundResource(R.drawable.black_circle);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(eText1.getText().toString().length()==1) {
                    eText1.setEnabled(false);
                    eText2.setEnabled(true);
                    eText2.requestFocus();
                    InputMethodManager show = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    show.showSoftInput(eText2,InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });
        eText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(eText2.getText().toString().length()==1)
                {
                    pin_code.add(eText2.getText().toString());
                    eText2.setBackgroundResource(R.drawable.black_circle);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                    eText1.setEnabled(false);
                    eText2.setEnabled(false);
                    eText3.setEnabled(true);
                    eText3.requestFocus();
                    InputMethodManager show = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    show.showSoftInput(eText3,InputMethodManager.SHOW_IMPLICIT);
            }
        });
        eText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(eText3.getText().toString().length()==1)
                {
                    pin_code.add(eText3.getText().toString());
                    eText3.setBackgroundResource(R.drawable.black_circle);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                    eText1.setEnabled(false);
                    eText2.setEnabled(false);
                    eText3.setEnabled(false);
                    eText4.setEnabled(true);
                    eText4.requestFocus();
                    InputMethodManager show = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    show.showSoftInput(eText4,InputMethodManager.SHOW_IMPLICIT);

            }
        });
        eText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(eText4.getText().toString().length()==1)
                {
                    pin_code.add(eText4.getText().toString());
                    eText4.setBackgroundResource(R.drawable.black_circle);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                eText1.setEnabled(false);
                eText2.setEnabled(false);
                eText3.setEnabled(false);
                eText4.clearFocus();
                InputMethodManager hide = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                hide.hideSoftInputFromWindow(eText4.getWindowToken(), 0);
            }
        });

    }







}

