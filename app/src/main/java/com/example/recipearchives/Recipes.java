package com.example.recipearchives;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class Recipes extends AppCompatActivity {

    RecyclerView myRecyclerView;
    RecipeAdapter adapter;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        create();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.recipes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        create();
    }

    public void create() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String mail = prefs.getString("mail", "null");

        db = new DBHelper(this);
        String first = db.getName(mail);
        String hello = "Hello, " + first + "! What are we cooking today?";
        TextView hi = (TextView) findViewById(R.id.hi);
        hi.setText(hello);

        List<String> Names = Arrays.asList();
        List<String> Meals = Arrays.asList();
        List<String> Diets = Arrays.asList();
        List<String> Times = Arrays.asList();

        String names = db.getNames();
        Names = Arrays.asList(names.split(","));

        String meals = db.getMeals();
        Meals = Arrays.asList(meals.split(","));

        String diets = db.getDiets();
        Diets = Arrays.asList(diets.split(","));

        String times = db.getTimes();
        Times = Arrays.asList(times.split(","));

        myRecyclerView = (RecyclerView) findViewById(R.id.rView);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new RecipeAdapter(R.layout.recipes_layout, this, Names, Meals, Diets, Times);
        myRecyclerView.setAdapter(adapter);
    }

    public void toSaved(View view) {
        Intent intent = new Intent(this, SavedRecipes.class);
        startActivity(intent);
    }

    public void toSearch(View view) {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

}