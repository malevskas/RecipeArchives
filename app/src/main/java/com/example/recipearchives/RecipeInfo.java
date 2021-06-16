package com.example.recipearchives;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class RecipeInfo extends AppCompatActivity {

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        db = new DBHelper(this);

        Intent intent = getIntent();
        String rName = intent.getStringExtra("name");

        ImageView img = (ImageView) findViewById(R.id.img);
        int id = db.getID(rName)-1;
        String photo = "r"+id;
        Resources resources = getResources();
        final int resourceId = resources.getIdentifier(photo, "drawable", getPackageName());
        img.setImageResource(resourceId);

        TextView name = (TextView) findViewById(R.id.name1);
        name.setText(rName);
        TextView meal = (TextView) findViewById(R.id.meal1);
        meal.setText(db.getMeal(rName));
        TextView diet = (TextView) findViewById(R.id.diet1);
        diet.setText(db.getDiet(rName));
        TextView time = (TextView) findViewById(R.id.time1);
        time.setText(db.getTime(rName));
        TextView servings = (TextView) findViewById(R.id.servings1);
        servings.setText(db.getServings(rName));

        String ingredients = db.getIngredients(rName);
        List<String> Ing = Arrays.asList();
        Ing = Arrays.asList(ingredients.split("\n"));

        LinearLayout ingLL = (LinearLayout) findViewById(R.id.ing);

        for(int i=0; i<Ing.size(); i++) {
            LinearLayout newLL = new LinearLayout(this);
            newLL.setOrientation(LinearLayout.HORIZONTAL);
            newLL.setClickable(true);
            ImageView imageView = new ImageView(this);
            ImageView imageView1 = new ImageView(this);
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_check_box_outline_blank_24));
            imageView1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_check_box_24));
            LinearLayout.LayoutParams wh = new LinearLayout.LayoutParams(35, 35);
            wh.setMargins(0, 20, 5, 0);
            imageView.setLayoutParams(wh);
            imageView1.setLayoutParams(wh);
            imageView1.setVisibility(View.GONE);
            TextView textView = new TextView(this);
            String text = Ing.get(i);
            textView.setText(text);
            textView.setTextSize((float) 17);
            textView.setId(i);
            textView.setTextColor(Color.parseColor("#25523B"));
            newLL.addView(imageView);
            newLL.addView(imageView1);
            newLL.addView(textView);
            newLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imageView1.getVisibility() == View.GONE) {
                        imageView1.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.GONE);
                        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    else {
                        imageView1.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                        textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    }
                }
            });
            ingLL.addView(newLL);
        }

        String instructions = db.getInstructions(rName);
        TextView ins = (TextView) findViewById(R.id.ins);
        
        ins.setText(instructions);

        List<String> Info = Arrays.asList();
        String info = db.getInfo(rName);
        Info = Arrays.asList(info.split(", "));

        TextView cals = (TextView) findViewById(R.id.kcal);
        cals.setText(Info.get(0));

        TextView carbs = (TextView) findViewById(R.id.carbs);
        carbs.setText(Info.get(1));

        TextView fat = (TextView) findViewById(R.id.fat);
        fat.setText(Info.get(2));

        TextView sugar = (TextView) findViewById(R.id.sugar);
        sugar.setText(Info.get(3));

        TextView protein = (TextView) findViewById(R.id.protein);
        protein.setText(Info.get(4));

        ImageView saved = (ImageView) findViewById(R.id.like);
        ImageView unsaved = (ImageView) findViewById(R.id.unlike);
        LinearLayout heart = (LinearLayout) findViewById(R.id.heart);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String mail = prefs.getString("mail", "null");

        if(db.checkSaved(rName, mail)) {
            saved.setVisibility(View.VISIBLE);
            unsaved.setVisibility(View.GONE);
        }
        else {
            saved.setVisibility(View.GONE);
            unsaved.setVisibility(View.VISIBLE);
        }

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(saved.getVisibility() == View.GONE) {
                    saved.setVisibility(View.VISIBLE);
                    unsaved.setVisibility(View.GONE);
                    db.addSaved(name.getText().toString(), mail);
                }
                else {
                    saved.setVisibility(View.GONE);
                    unsaved.setVisibility(View.VISIBLE);
                    db.removeSaved(name.getText().toString(), mail);
                }
            }
        });

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