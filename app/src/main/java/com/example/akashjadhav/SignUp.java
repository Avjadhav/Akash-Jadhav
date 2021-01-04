package com.example.akashjadhav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class SignUp extends AppCompatActivity {
    CountryCodePicker countryCodePicker;
    EditText etPhonenumber;
    Button generateOtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        generateOtp = findViewById(R.id.btnGenerateOTP);
        etPhonenumber = findViewById(R.id.etPhonenumber);
        countryCodePicker = findViewById(R.id.countryCodePicker);


        generateOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = countryCodePicker.getSelectedCountryCodeWithPlus().trim();
                String phonenumber = etPhonenumber.getText().toString().trim();
                if (phonenumber.isEmpty()){
                    etPhonenumber.setError("Enter number");
                    return;
                }else {

                    String number = code + phonenumber;
                    startActivity(new Intent(SignUp.this, VerifyNumber.class).putExtra("number", number));
                }
            }
        });
    }
}