package com.example.recipearchives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    DBHelper db;
    EditText firstN;
    EditText lastN;
    EditText email;
    EditText password1;
    EditText password2;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        db = new DBHelper(this);

        error = (TextView) findViewById(R.id.error2);
        error.setVisibility(View.GONE);
        error = (TextView) findViewById(R.id.error3);
        error.setVisibility(View.GONE);
    }

    public void register(View view) {
        firstN = (EditText) findViewById(R.id.first);
        lastN = (EditText) findViewById(R.id.last);
        email = (EditText) findViewById(R.id.email);
        password1 = (EditText) findViewById(R.id.pass1);
        password2 = (EditText) findViewById(R.id.pass2);
        String first = firstN.getText().toString();
        String last = lastN.getText().toString();
        String mail = email.getText().toString();
        String pass1 = password1.getText().toString();
        String pass2 = password2.getText().toString();

        if(pass1.equals(pass2)) {
            boolean check = db.registerUser(first, last, mail, pass1);
            if(check) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            }
            else {
                error = (TextView) findViewById(R.id.error2);
                error.setVisibility(View.VISIBLE);
            }
        }
        else {
            error = (TextView) findViewById(R.id.error2);
            error.setVisibility(View.GONE);
            error = (TextView) findViewById(R.id.error3);
            error.setVisibility(View.VISIBLE);
        }
    }

    public void toLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}