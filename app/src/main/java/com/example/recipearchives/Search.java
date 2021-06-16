package com.example.recipearchives;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Attributes;

public class Search extends AppCompatActivity {

    DBHelper db;
    TextView not;
    LinearLayout recipes;
    List<String> Names;
    private String meal="";
    private String diet="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        db = new DBHelper(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Names = Arrays.asList();

        not = (TextView) findViewById(R.id.notF);
        not.setVisibility(View.GONE);

        recipes = (LinearLayout) findViewById(R.id.recipes);
        recipes.setVisibility(View.GONE);

        LinearLayout ll = (LinearLayout) findViewById(R.id.filters);
        ll.setVisibility(View.GONE);

        TextView add = (TextView) findViewById(R.id.add);
        add.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    add.setBackgroundResource(R.drawable.round_border);
                }
                else {
                    add.setBackgroundResource(R.drawable.rounded_green);
                }
                return false;
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ll.getVisibility() == View.GONE)
                    ll.setVisibility(View.VISIBLE);
                else
                    ll.setVisibility(View.GONE);
            }
        });

        TextView breakfast = (TextView) findViewById(R.id.breakfast);
        TextView lunch = (TextView) findViewById(R.id.lunch);
        TextView dinner = (TextView) findViewById(R.id.dinner);
        TextView dessert = (TextView) findViewById(R.id.dessert);
        TextView snack = (TextView) findViewById(R.id.snack);

        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(breakfast.getCurrentTextColor() == Color.parseColor("#FA8072")) {
                    breakfast.setTextColor(Color.parseColor("#6497B1"));
                    meal = "";
                }
                else {
                    breakfast.setTextColor(Color.parseColor("#FA8072"));
                    lunch.setTextColor(Color.parseColor("#6497B1"));
                    dinner.setTextColor(Color.parseColor("#6497B1"));
                    dessert.setTextColor(Color.parseColor("#6497B1"));
                    snack.setTextColor(Color.parseColor("#6497B1"));
                    meal = "Breakfast";
                }
            }
        });

        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lunch.getCurrentTextColor() == Color.parseColor("#FA8072")) {
                    lunch.setTextColor(Color.parseColor("#6497B1"));
                    meal = "";
                }
                else {
                    breakfast.setTextColor(Color.parseColor("#6497B1"));
                    lunch.setTextColor(Color.parseColor("#FA8072"));
                    dinner.setTextColor(Color.parseColor("#6497B1"));
                    dessert.setTextColor(Color.parseColor("#6497B1"));
                    snack.setTextColor(Color.parseColor("#6497B1"));
                    meal = "Lunch";
                }
            }
        });

        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dinner.getCurrentTextColor() == Color.parseColor("#FA8072")) {
                    dinner.setTextColor(Color.parseColor("#6497B1"));
                    meal = "";
                }
                else {
                    breakfast.setTextColor(Color.parseColor("#6497B1"));
                    lunch.setTextColor(Color.parseColor("#6497B1"));
                    dinner.setTextColor(Color.parseColor("#FA8072"));
                    dessert.setTextColor(Color.parseColor("#6497B1"));
                    snack.setTextColor(Color.parseColor("#6497B1"));
                    meal = "Dinner";
                }
            }
        });

        dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dessert.getCurrentTextColor() == Color.parseColor("#FA8072")) {
                    dessert.setTextColor(Color.parseColor("#6497B1"));
                    meal = "";
                }
                else {
                    breakfast.setTextColor(Color.parseColor("#6497B1"));
                    lunch.setTextColor(Color.parseColor("#6497B1"));
                    dinner.setTextColor(Color.parseColor("#6497B1"));
                    dessert.setTextColor(Color.parseColor("#FA8072"));
                    snack.setTextColor(Color.parseColor("#6497B1"));
                    meal = "Dessert";
                }
            }
        });

        snack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(snack.getCurrentTextColor() == Color.parseColor("#FA8072")) {
                    snack.setTextColor(Color.parseColor("#6497B1"));
                    meal = "";
                }
                else {
                    breakfast.setTextColor(Color.parseColor("#6497B1"));
                    lunch.setTextColor(Color.parseColor("#6497B1"));
                    dinner.setTextColor(Color.parseColor("#6497B1"));
                    dessert.setTextColor(Color.parseColor("#6497B1"));
                    snack.setTextColor(Color.parseColor("#FA8072"));
                    meal = "Snack";
                }
            }
        });

        TextView vege = (TextView) findViewById(R.id.vege);
        TextView vegan = (TextView) findViewById(R.id.vegan);
        TextView meat = (TextView) findViewById(R.id.meat);
        TextView lowcarb = (TextView) findViewById(R.id.low);

        vege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vege.getCurrentTextColor() == Color.parseColor("#FA8072")) {
                    vege.setTextColor(Color.parseColor("#6497B1"));
                    diet = "";
                }
                else {
                    vege.setTextColor(Color.parseColor("#FA8072"));
                    vegan.setTextColor(Color.parseColor("#6497B1"));
                    meat.setTextColor(Color.parseColor("#6497B1"));
                    lowcarb.setTextColor(Color.parseColor("#6497B1"));
                    diet = "Vegetarian";}
            }
        });

        vegan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vegan.getCurrentTextColor() == Color.parseColor("#FA8072")) {
                    vegan.setTextColor(Color.parseColor("#6497B1"));
                    diet = "";
                }
                else {
                    vege.setTextColor(Color.parseColor("#6497B1"));
                    vegan.setTextColor(Color.parseColor("#FA8072"));
                    meat.setTextColor(Color.parseColor("#6497B1"));
                    lowcarb.setTextColor(Color.parseColor("#6497B1"));
                    diet = "Vegan";
                }
            }
        });

        meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(meat.getCurrentTextColor() == Color.parseColor("#FA8072")) {
                    meat.setTextColor(Color.parseColor("#6497B1"));
                    diet = "";
                }
                else {
                    vege.setTextColor(Color.parseColor("#6497B1"));
                    vegan.setTextColor(Color.parseColor("#6497B1"));
                    meat.setTextColor(Color.parseColor("#FA8072"));
                    lowcarb.setTextColor(Color.parseColor("#6497B1"));
                    diet = "Meat";
                }
            }
        });

        lowcarb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lowcarb.getCurrentTextColor() == Color.parseColor("#FA8072")) {
                    lowcarb.setTextColor(Color.parseColor("#6497B1"));
                    diet = "";
                }
                else {
                    vege.setTextColor(Color.parseColor("#6497B1"));
                    vegan.setTextColor(Color.parseColor("#6497B1"));
                    meat.setTextColor(Color.parseColor("#6497B1"));
                    lowcarb.setTextColor(Color.parseColor("#FA8072"));
                    diet = "Low Carb";
                }
            }
        });

        TextView apply = (TextView) findViewById(R.id.apply);
        apply.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    apply.setBackgroundResource(R.drawable.round_border);
                }
                else {
                    apply.setBackgroundResource(R.drawable.rounded_green);
                }
                return false;
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchIng = (EditText) findViewById(R.id.search);
                String searched = searchIng.getText().toString();
                List<String> Filtered = new ArrayList<String>();
                int j = 0;
                if(searched.equals("")) {
                    String names = db.getNames();
                    Names = Arrays.asList(names.split(","));
                    j = 1;
                }

                if(meal.equals("")) {
                    for (int i = 1-j; i < Names.size(); i++) {
                        if(db.filter1(Names.get(i), diet)) {
                            Filtered.add(Names.get(i));
                        }
                    }
                }

                else {
                    if(diet.equals("")) {
                        for (int i = 1-j; i < Names.size(); i++) {
                            if(db.filter2(Names.get(i), meal)) {
                                Filtered.add(Names.get(i));
                            }
                        }
                    }
                    else {
                        for (int i = 1-j; i < Names.size(); i++) {
                            if(db.filter(Names.get(i), meal, diet)) {
                                Filtered.add(Names.get(i));
                            }
                        }
                    }
                }
                addLL(Filtered);
            }
        });
    }

    public void addLL(List<String> Names) {
        LinearLayout recLL = (LinearLayout) findViewById(R.id.recipes);
        recLL.removeAllViews();
        if(!Names.isEmpty()) {
            not.setVisibility(View.GONE);
            recipes.setVisibility(View.VISIBLE);
            for (int i = 0; i < Names.size(); i++) {
                LinearLayout newLL = new LinearLayout(this);
                newLL.setOrientation(LinearLayout.HORIZONTAL);
                newLL.setClickable(true);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 15, 5, 0);
                newLL.setLayoutParams(params);
                newLL.setBackgroundResource(R.drawable.back);

                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams wh = new LinearLayout.LayoutParams(260, 200);
                wh.setMargins(15, 0, 0, 0);
                imageView.setLayoutParams(wh);
                int id = db.getID(Names.get(i)) - 1;
                String photo = "r" + id;
                Resources resources = getResources();
                final int resourceId = resources.getIdentifier(photo, "drawable", getPackageName());
                imageView.setImageResource(resourceId);

                TextView text = new TextView(this);
                text.setText(Names.get(i));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.gravity = Gravity.CENTER;
                text.setGravity(Gravity.CENTER);
                text.setLayoutParams(lp);
                text.setTextColor(Color.parseColor("#25523B"));
                text.setTextSize((float) 19);

                newLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), RecipeInfo.class);
                        intent.putExtra("name", text.getText().toString());
                        v.getContext().startActivity(intent);
                    }
                });

                newLL.addView(imageView);
                newLL.addView(text);
                recLL.addView(newLL);
            }
        }
        else {
            not.setVisibility(View.VISIBLE);
            recipes.setVisibility(View.GONE);
        }
    }

    public void toSaved(View view) {
        Intent intent = new Intent(this, SavedRecipes.class);
        startActivity(intent);
    }

    public void searching(View view) {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        EditText searchIng = (EditText) findViewById(R.id.search);
        String searched = searchIng.getText().toString();
        String name = db.search(searched);
        if(!name.equals("")) {
            Names = Arrays.asList(name.split(","));
            List<String> Filtered = new ArrayList<String>();
            if(!searched.equals("")) {
                for (int i = 1; i < Names.size(); i++) {
                    Filtered.add(Names.get(i));
                }
            }
            else {
                String names = db.getNames();
                Filtered = Arrays.asList(names.split(","));
            }
            addLL(Filtered);
        }
        else {
            not.setVisibility(View.VISIBLE);
            recipes.setVisibility(View.GONE);
        }
    }
}