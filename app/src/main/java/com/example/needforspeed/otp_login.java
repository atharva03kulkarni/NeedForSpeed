package com.example.needforspeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otp_login extends AppCompatActivity {
    EditText inputcode1, inputcode2, inputcode3, inputcode4, inputcode5, inputcode6, inputMobile, name;

    String verificationID;

    Button genOtp, verifyOtp, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        inputMobile = findViewById(R.id.editText);
        name = findViewById(R.id.editText3);

        genOtp = findViewById(R.id.button2);
        verifyOtp = findViewById(R.id.button3);
        next = findViewById(R.id.button);

        next.setEnabled(false);

        inputcode1 = findViewById(R.id.editText8);
        inputcode2 = findViewById(R.id.editText9);
        inputcode3 = findViewById(R.id.editText10);
        inputcode4 = findViewById(R.id.editText11);
        inputcode5 = findViewById(R.id.editText12);
        inputcode6 = findViewById(R.id.editText13);

        genOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputMobile.getText().toString().trim().isEmpty()){

                    Toast.makeText(otp_login.this, "Please enter a mobile number!", Toast.LENGTH_SHORT).show();
                    return;

                }

                PhoneAuthProvider.getInstance().verifyPhoneNumber( "+91"+inputMobile.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        otp_login.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){


                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(otp_login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                verificationID = s;
                                //super.onCodeSent(s, forceResendingToken);
                            }
                        });

            }
        });

        setUpotp();

        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inputcode1.getText().toString().trim().isEmpty()
                        || inputcode2.getText().toString().trim().isEmpty()
                        || inputcode3.getText().toString().trim().isEmpty()
                        || inputcode4.getText().toString().trim().isEmpty()
                        || inputcode5.getText().toString().trim().isEmpty()
                        || inputcode6.getText().toString().trim().isEmpty()
                ){
                    Toast.makeText(otp_login.this, "Please Enter Valid Otp", Toast.LENGTH_SHORT).show();
                    return;
                }

                String code = inputcode1.getText().toString() +
                        inputcode2.getText().toString() +
                        inputcode3.getText().toString() +
                        inputcode4.getText().toString() +
                        inputcode5.getText().toString() +
                        inputcode6.getText().toString() ;

                if (verificationID != null){

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationID,
                            code
                    );

                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isComplete()){

                                        Toast.makeText(otp_login.this, "Correct Otp", Toast.LENGTH_SHORT).show();

                                    }else {

                                        Toast.makeText(otp_login.this, "Otp is wrong", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }

                next.setEnabled(true);


            }
        });

    }



    public void setUpotp(){

        inputcode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){

                    inputcode2.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){

                    inputcode3.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){

                    inputcode4.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){

                    inputcode5.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){

                    inputcode6.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }



    public void next(View view){

        Intent intent8 = new Intent(this, shoppingScreen.class);
        startActivity(intent8);

    }
}