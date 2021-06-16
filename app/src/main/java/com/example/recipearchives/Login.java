package com.example.recipearchives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    DBHelper db;
    EditText email;
    EditText password;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        db = new DBHelper(this);

        error = (TextView) findViewById(R.id.error1);
        error.setVisibility(View.GONE);
    }

    public void login(View view) {
        db.popolni();
        email = (EditText) findViewById(R.id.log_mail);
        password = (EditText) findViewById(R.id.log_pass);
        String mail = email.getText().toString();
        String pass = password.getText().toString();
        boolean check = db.checkLogin(mail, pass);
        if(check) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("mail", mail);
            editor.commit();
            Intent intent = new Intent(this, Recipes.class);
            startActivity(intent);
        }
        else {
            error = (TextView) findViewById(R.id.error1);
            error.setVisibility(View.VISIBLE);
        }
    }

    public void toRegister(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}