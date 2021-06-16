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

import java.util.Arrays;
import java.util.List;

public class SavedRecipes extends AppCompatActivity {

    RecyclerView myRecyclerView;
    SavedRecipesAdapter adapter;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_recipes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String mail = prefs.getString("mail", "null");

        db = new DBHelper(this);

        List<String> Recipes = Arrays.asList();
        String recipes = db.getSaved(mail);
        Recipes = Arrays.asList(recipes.split(","));

        List<String> Names = Arrays.asList();
        List<String> Meals = Arrays.asList();
        List<String> Diets = Arrays.asList();
        List<String> Times = Arrays.asList();

        String names = db.getSavedNames(mail);
        Names = Arrays.asList(names.split(","));

        String meals = db.getSavedMeals(mail);
        Meals = Arrays.asList(meals.split(","));

        String diets = db.getSavedDiets(mail);
        Diets = Arrays.asList(diets.split(","));

        String times = db.getSavedTimes(mail);
        Times = Arrays.asList(times.split(","));


        myRecyclerView = (RecyclerView) findViewById(R.id.rView1);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new SavedRecipesAdapter(R.layout.saved_recipes_layout, this, Recipes, Names, Meals, Diets, Times);
        myRecyclerView.setAdapter(adapter);
    }

    public void toSearch(View view) {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }
}