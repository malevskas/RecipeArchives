package com.example.recipearchives;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class SavedRecipesAdapter extends RecyclerView.Adapter <SavedRecipesAdapter.ViewHolder> {

    private int savedLayout;
    private Context myContext;
    private List<String> Recipes;
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
            card = (LinearLayout) itemView.findViewById(R.id.card1);
        }
    }

    public SavedRecipesAdapter(int savedLayout, Context myContext, List<String> Recipes, List<String> Names, List<String> Meals, List<String> Diets, List<String> Times) {
        this.savedLayout = savedLayout;
        this.myContext = myContext;
        this.Recipes = Recipes;
        this.Names = Names;
        this.Meals = Meals;
        this.Diets = Diets;
        this.Times = Times;
    }

    @Override
    public SavedRecipesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(savedLayout, viewGroup, false);
        return new SavedRecipesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SavedRecipesAdapter.ViewHolder viewHolder, int i) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(myContext);
        String mail = prefs.getString("mail", "null");
        db = new DBHelper(myContext);
        String rName = Names.get(i);
        viewHolder.name.setText(rName);
        String rMeal = Meals.get(i);
        viewHolder.meal.setText(rMeal);
        String rDiet = Diets.get(i);
        viewHolder.diet.setText(rDiet);
        String rTime = Times.get(i);
        viewHolder.time.setText(rTime);

        int id = db.getID(rName)-1;
        String photo = "r"+id;
        Resources resources = viewHolder.name.getContext().getResources();
        final int resourceId = resources.getIdentifier(photo, "drawable",
                viewHolder.name.getContext().getPackageName());
        viewHolder.image.setImageResource(resourceId);

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
        int rez = db.countSaved();
        return rez;
    }
}
