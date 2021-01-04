package com.example.akashjadhav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyNumber extends AppCompatActivity {
    String number;
    EditText etOTP;
    TextView tvnumber;
    Button verifyOtp;
    ProgressBar progressBar;
    FirebaseAuth auth;
    String systemcode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_number);

        etOTP= findViewById(R.id.etOTP);
        verifyOtp = findViewById(R.id.btnVerifyOTP);
        tvnumber =findViewById(R.id.tv3);
        progressBar = findViewById(R.id.progressbar);

        auth= FirebaseAuth.getInstance();

        number = getIntent().getStringExtra("number");
        tvnumber.setText(number);
        SendOtp(number);


    }

    private void SendOtp(String number) {

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(number)
                .setTimeout((long) 60, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                systemcode = s;

            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                if (code!=null){
                    etOTP.setText(code);
                    progressBar.setVisibility(View.VISIBLE);
                    verify(code);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(VerifyNumber.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
    };



    private void verify(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(systemcode,code);
        SignIn(credential);
    }

    private void SignIn(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(VerifyNumber.this, Dashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else {
                            Toast.makeText(VerifyNumber.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void btnverify(View view) {
        String cd = etOTP.getText().toString();

        if (cd.isEmpty() || cd.length()<6){
            etOTP.setError("Wrong OTP");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        verify(cd);
    }
}