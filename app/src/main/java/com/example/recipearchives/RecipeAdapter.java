package com.example.recipearchives;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter <RecipeAdapter.ViewHolder> {

    private int recipeLayout;
    private Context myContext;
    private List<String> Names;
    private List<String> Meals;
    private List<String> Diets;
    private List<String> Times;
    private DBHelper db;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView meal;
        public TextView diet;
        public TextView time;
        public TextView name;
        public ImageView image;
        public ImageView saved;
        public ImageView unsaved;
        public LinearLayout heart;
        public LinearLayout card;

        public ViewHolder(View itemView) {
            super(itemView);
            meal = (TextView) itemView.findViewById(R.id.meal);
            diet = (TextView) itemView.findViewById(R.id.diet);
            time = (TextView) itemView.findViewById(R.id.time);
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.photo);
            saved = (ImageView) itemView.findViewById(R.id.like);
            unsaved = (ImageView) itemView.findViewById(R.id.unlike);
            heart = (LinearLayout) itemView.findViewById(R.id.heart);
            card = (LinearLayout) itemView.findViewById(R.id.card);
        }
    }

    public RecipeAdapter(int recipeLayout, Context myContext, List<String> Names, List<String> Meals, List<String> Diets, List<String> Times) {
        this.recipeLayout = recipeLayout;
        this.myContext = myContext;
        this.Names = Names;
        this.Meals = Meals;
        this.Diets = Diets;
        this.Times = Times;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(recipeLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        db = new DBHelper(myContext);
        String rName = Names.get(i);
        viewHolder.name.setText(rName);
        String rMeal = Meals.get(i);
        viewHolder.meal.setText(rMeal);
        String rDiet = Diets.get(i);
        viewHolder.diet.setText(rDiet);
        String rTime = Times.get(i);
        viewHolder.time.setText(rTime);

        String photo = "r"+i;
        Resources resources = viewHolder.name.getContext().getResources();
        final int resourceId = resources.getIdentifier(photo, "drawable",
                viewHolder.name.getContext().getPackageName());
        viewHolder.image.setImageResource(resourceId);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(myContext);
        String mail = prefs.getString("mail", "null");

        if(db.checkSaved(rName, mail)) {
            viewHolder.saved.setVisibility(View.VISIBLE);
            viewHolder.unsaved.setVisibility(View.GONE);
        }
        else {
            viewHolder.saved.setVisibility(View.GONE);
            viewHolder.unsaved.setVisibility(View.VISIBLE);
        }

        viewHolder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.saved.getVisibility() == View.GONE) {
                    viewHolder.saved.setVisibility(View.VISIBLE);
                    viewHolder.unsaved.setVisibility(View.GONE);
                    db.addSaved(viewHolder.name.getText().toString(), mail);
                }
                else {
                    viewHolder.saved.setVisibility(View.GONE);
                    viewHolder.unsaved.setVisibility(View.VISIBLE);
                    db.removeSaved(viewHolder.name.getText().toString(), mail);
                }
            }
        });

        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext, RecipeInfo.class);
                intent.putExtra("name", viewHolder.name.getText().toString());
                myContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        db = new DBHelper(myContext);
        int rez = db.count();
        return rez;
    }
}
