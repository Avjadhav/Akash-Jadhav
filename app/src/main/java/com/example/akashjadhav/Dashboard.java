package com.example.akashjadhav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Dashboard extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        
        auth = FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
    }

    public void Logout(View view) {
        try {
            auth.signOut();
            startActivity(new Intent(Dashboard.this , MainActivity.class));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user == null){

            startActivity(new Intent(Dashboard.this , MainActivity.class));
            finish();
        }
    }
}