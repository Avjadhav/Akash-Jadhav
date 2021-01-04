package com.example.akashjadhav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
    }

    public void gotophonepage(View view) {
        startActivity(new Intent(MainActivity.this, SignUp.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user != null){

            startActivity(new Intent(MainActivity.this , Dashboard.class));
            finish();
        }
    }
}