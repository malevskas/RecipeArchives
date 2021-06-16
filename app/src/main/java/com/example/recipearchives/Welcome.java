package com.example.recipearchives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
    }

    public void toLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void toRegister(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}