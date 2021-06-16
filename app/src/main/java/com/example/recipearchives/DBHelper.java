package com.example.recipearchives;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "databaza.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createRecipeTable = "CREATE TABLE recipes(rID INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(100), ingredients VARCHAR(500), " +
                "instructions VARCHAR(1500), meal VARCHAR(10), diet VARCHAR(15), time VARCHAR(25), servings INT(2), info VARCHAR(100))";
        db.execSQL(createRecipeTable);

        String createUserTable = "CREATE TABLE users(userID INTEGER PRIMARY KEY AUTOINCREMENT, email VARCHAR(60), password VARCHAR(30), " +
                "first VARCHAR(30), last VARCHAR(30))";
        db.execSQL(createUserTable);

        String createSavedTable = "CREATE TABLE saved(sID INTEGER PRIMARY KEY AUTOINCREMENT, email VARCHAR(60), recipe VARCHAR(100), " +
                "FOREIGN KEY(email) REFERENCES users(email), FOREIGN KEY(recipe) REFERENCES recipes(name))";
        db.execSQL(createSavedTable);
    }

    public boolean checkLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email=? AND password=?", new String[]{email, password});
        if(cursor.getCount() > 0) {
            cursor.close();
            return true;
        }else{
            return false;
        }
    }

    public boolean registerUser(String first, String last, String mail, String pass) {
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor cursor = read.rawQuery("SELECT * FROM users WHERE email=?", new String[]{mail});
        if(!(cursor.getCount() > 0)) {
            SQLiteDatabase write = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("email", mail);
            cv.put("password", pass);
            cv.put("first", first);
            cv.put("last", last);
            long insert = write.insert("users", null, cv);
            if(insert == -1) {
                return false;
            }
            else {
                cursor.close();
                return true;
            }
        }
        else
            return false;
    }

    public int count() {
        SQLiteDatabase read = this.getReadableDatabase();
        String query = "SELECT rID FROM recipes";
        Cursor c = read.rawQuery(query, null);
        return c.getCount();
    }

    public int countSaved() {
        SQLiteDatabase read = this.getReadableDatabase();
        String query = "SELECT sID FROM saved";
        Cursor c = read.rawQuery(query, null);
        return c.getCount();
    }

    public String getName(String mail) {
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor cursor = read.rawQuery("SELECT first FROM users WHERE email=?", new String[]{mail});
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public String getNames() {
        SQLiteDatabase read = this.getReadableDatabase();
        String query = "SELECT name FROM recipes";
        Cursor c = read.rawQuery(query, null);
        c.moveToFirst();
        String result = c.getString(0);
        while(c.moveToNext()) {
            result = result + "," + c.getString(0);
        }
        c.close();
        return result;
    }

    public String getMeals() {
        SQLiteDatabase read = this.getReadableDatabase();
        String query = "SELECT meal FROM recipes";
        Cursor c = read.rawQuery(query, null);
        c.moveToFirst();
        String result = c.getString(0);
        while(c.moveToNext()) {
            result = result + "," + c.getString(0);
        }
        c.close();
        return result;
    }

    public String getDiets() {
        SQLiteDatabase read = this.getReadableDatabase();
        String query = "SELECT diet FROM recipes";
        Cursor c = read.rawQuery(query, null);
        c.moveToFirst();
        String result = c.getString(0);
        while(c.moveToNext()) {
            result = result + "," + c.getString(0);
        }
        c.close();
        return result;
    }

    public String getTimes() {
        SQLiteDatabase read = this.getReadableDatabase();
        String query = "SELECT time FROM recipes";
        Cursor c = read.rawQuery(query, null);
        c.moveToFirst();
        String result = c.getString(0);
        while(c.moveToNext()) {
            result = result + "," + c.getString(0);
        }
        c.close();
        return result;
    }

    public boolean checkSaved(String name, String mail) {
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor cursor = read.rawQuery("SELECT * FROM saved WHERE email=? AND recipe=?", new String[]{mail, name});
        if(cursor.moveToFirst()) {
            return true;
        }
        else {
            cursor.close();
            return false;
        }
    }

    public void addSaved(String name, String mail) {
        SQLiteDatabase write = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("email", mail);
        cv.put("recipe", name);
        write.insert("saved", null, cv);
    }

    public void removeSaved(String name, String mail) {
        SQLiteDatabase write = this.getWritableDatabase();
        write.delete("saved", "email=? AND recipe=?", new String[]{mail, name});
    }

    public String getSaved(String mail) {
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor c = read.rawQuery("SELECT recipe FROM saved WHERE email=?", new String[]{mail});
        if(c.moveToFirst()) {
            String result = c.getString(0);
            while (c.moveToNext()) {
                result = result + "," + c.getString(0);
            }
            c.close();
            return result;
        }
        else
            return "0";
    }

    public String getSavedNames(String mail) {
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor c = read.rawQuery("SELECT recipes.name FROM saved, recipes WHERE saved.recipe=recipes.name AND saved.email=?", new String[]{mail});
        c.moveToFirst();
        String result = c.getString(0);
        while(c.moveToNext()) {
            result = result + "," + c.getString(0);
        }
        c.close();
        return result;
    }

    public String getSavedMeals(String mail) {
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor c = read.rawQuery("SELECT recipes.meal FROM saved, recipes WHERE saved.recipe=recipes.name AND saved.email=?", new String[]{mail});
        c.moveToFirst();
        String result = c.getString(0);
        while(c.moveToNext()) {
            result = result + "," + c.getString(0);
        }
        c.close();
        return result;
    }

    public String getSavedDiets(String mail) {
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor c = read.rawQuery("SELECT recipes.diet FROM saved, recipes WHERE saved.recipe=recipes.name AND saved.email=?", new String[]{mail});
        c.moveToFirst();
        String result = c.getString(0);
        while(c.moveToNext()) {
            result = result + "," + c.getString(0);
        }
        c.close();
        return result;
    }

    public String getSavedTimes(String mail) {
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor c = read.rawQuery("SELECT recipes.time FROM saved, recipes WHERE saved.recipe=recipes.name AND saved.email=?", new String[]{mail});
        c.moveToFirst();
        String result = c.getString(0);
        while(c.moveToNext()) {
            result = result + "," + c.getString(0);
        }
        c.close();
        return result;
    }

    public int getID(String name) {
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor c = read.rawQuery("SELECT rID FROM recipes WHERE name=?", new String[]{name});
        c.moveToFirst();
        return c.getInt(0);
    }

    public String getMeal(String name) {
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor c = read.rawQuery("SELECT meal FROM recipes WHERE name=?", new String[]{name});
        c.moveToFirst();
        return c.getString(0);
    }

    public String getDiet(String name) {
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor c = read.rawQuery("SELECT diet FROM recipes WHERE name=?", new String[]{name});
        c.moveToFirst();
        return c.getString(0);
    }

    public String getTime(String name) {
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor c = read.rawQuery("SELECT time FROM recipes WHERE name=?", new String[]{name});
        c.moveToFirst();
        return c.getString(0);
    }

    public String getServings(String name) {
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor c = read.rawQuery("SELECT servings FROM recipes WHERE name=?", new String[]{name});
        c.moveToFirst();
        return c.getString(0);
    }

    public String getIngredients(String name) {
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor c = read.rawQuery("SELECT ingredients FROM recipes WHERE name=?", new String[]{name});
        c.moveToFirst();
        return c.getString(0);
    }

    public String getInstructions(String name) {
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor c = read.rawQuery("SELECT instructions FROM recipes WHERE name=?", new String[]{name});
        c.moveToFirst();
        return c.getString(0);
    }

    public String getInfo(String name) {
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor c = read.rawQuery("SELECT info FROM recipes WHERE name=?", new String[]{name});
        c.moveToFirst();
        return c.getString(0);
    }

    public String search(String ing) {
        SQLiteDatabase read = this.getReadableDatabase();
        String query = "SELECT name, ingredients FROM recipes";
        Cursor c = read.rawQuery(query, null);
        c.moveToFirst();
        String ings;
        String name = "";
        do {
            ings = c.getString(1);
            if(ings.contains(ing)) {
                name = name + "," + c.getString(0);
            }
        } while(c.moveToNext());
        c.close();
        return name;
    }

    public boolean filter(String name, String meal, String diet) {
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor c = read.rawQuery("SELECT * FROM recipes WHERE name=? AND meal=? AND diet=?", new String[]{name, meal, diet});
        if(c.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean filter1(String name, String diet) {
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor c = read.rawQuery("SELECT * FROM recipes WHERE name=? AND diet=?", new String[]{name, diet});
        if(c.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean filter2(String name, String meal) {
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor c = read.rawQuery("SELECT * FROM recipes WHERE name=? AND meal=?", new String[]{name, meal});
        if(c.getCount()>0)
            return true;
        else
            return false;
    }

    public void popolni() {
        SQLiteDatabase read = this.getReadableDatabase();
        String query = "SELECT * FROM recipes";
        Cursor c = read.rawQuery(query, null);
        if (!(c.getCount() > 0)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put("name", "Berry Me in Pancakes!");
            cv.put("ingredients", "130g whole wheat flour\n½ tsp baking soda\n1 tbsp vanilla extract\n250ml nut milk\n1 ripe banana\n" +
                    "nut oil of choice\ndrizzle of honey\n20g blueberries");
            cv.put("instructions", "Mash the banana in a bowl with a fork. Grab 2 bowls. In bowl 1, mix together the flour and baking soda. In the second bowl, combine the vanilla extract, " +
                    "milk and banana and mix carefully. Add the wet ingredients to the dry ingredients and lightly mix together.\nPrep a non-stick skillet with oil and bring up to temperature " +
                    "over a medium heat. Spoon half the pancake mixture onto the skillet, flipping when you see bubbles, approx 2 mins, to cook on both each side.\nTop with a drizzle of sweet honey and blueberries");
            cv.put("meal", "Breakfast"); cv.put("diet", "Vegan"); cv.put("time", "Under 30 minutes"); cv.put("servings", "2");
            cv.put("info", "Calories: 328 Kcal, Carbs: 61g, Fat: 4g, Sugar: 20g, Protein: 13g");
            db.insert("recipes", null, cv);

            cv.put("name", "Berry Sorbet");
            cv.put("ingredients", "300g frozen blueberries\n2 tbsp honey\n3 tbsp lemon juice\n1 tsp lemon zest\n85ml water");
            cv.put("instructions", "Process berries, honey, lemon juice and zest and water until creamy in a food processor. Press mixture down with spoon as needed. Freeze for 4 hours in an airtight container, or serve straight away.");
            cv.put("meal", "Dessert"); cv.put("diet", "Vegan"); cv.put("time", "Under 15 minutes"); cv.put("servings", "4");
            cv.put("info", "Calories: 103 Kcal, Carbs: 28g, Fat: 0.5g, Sugar: 20g, Protein: 1g");
            db.insert("recipes", null, cv);

            cv.put("name", "Boss Burrito");
            cv.put("ingredients", "4 eggs, 2 whole wheat tortillas\n2 diced tomatoes\n1 avocado\n2 tbsp yogurt\n2 tbsp chopped parsley\n½ sliced red onion\n1 lime\nnut oil");
            cv.put("instructions", "Break the eggs into a bowl and whisk with a fork until fluffy. Cook the eggs in a hot skillet for 2-3 mins.\nIn a separate skillet, lightly drizzle the oil and heat the tortilla on each side. " +
                    "Whilst still over a medium heat, top the tortilla with half of the eggs and remaining ingredients and cook through for 3-4 mins.\nSqueeze of lime juice over the filling, wrap up the burrito and cook on both sides until golden brown.");
            cv.put("meal", "Breakfast"); cv.put("diet", "Vegetarian"); cv.put("time", "Under 15 minutes"); cv.put("servings", "2");
            cv.put("info", "Calories: 286 Kcal, Carbs: 20g, Fat: 17g, Sugar: 5g, Protein: 13g");
            db.insert("recipes", null, cv);

            cv.put("name", "Caprese Zoodles");
            cv.put("ingredients", "4 large zucchini\n2 tbsp olive oil\n10 cherry tomatoes\n1 mozzarella ball\nfresh basil leaves\n2 tbsp balsamic vinegar");
            cv.put("instructions", "Using a spiralizer, create zoodles out of zucchini. Add zoodles to a large bowl, toss with olive oil and season with salt and pepper. Let marinate 10 minutes. " +
                    "Add tomatoes, mozzarella and basil to zoodles and toss until combined. Drizzle with balsamic and serve.");
            cv.put("meal", "Dinner"); cv.put("diet", "Low Carb"); cv.put("time", "Under 15 minutes"); cv.put("servings", "4");
            cv.put("info", "Calories: 285 Kcal, Carbs: 16g, Fat: 19g, Sugar: 4g, Protein: 14g");
            db.insert("recipes", null, cv);

            cv.put("name", "Cauliflower Grilled Cheese");
            cv.put("ingredients", "1 head cauliflower\n1 pinch salt\n2 eggs\n55g grated parmesan cheese\nfresh basil\n1 teaspoon garlic powder\n" +
                    "1 tsp oregano\n½ tsp red pepper flakes\n100g cheese of your choice");
            cv.put("instructions", "Grate the cauliflower over a large bowl. Sprinkle some salt and mix evenly. Leave to sit for 20 minutes.\nPour the " +
                    "cauliflower into a clean kitchen towel and wring the water out. In a large bowl, combine the cauliflower with the remaining ingredients " +
                    "(except your cheese and toppings) and stir until well mixed. Using a spoon transfer two scoops of mixture to form each “bread slice” onto a " +
                    "skillet on medium-low heat for about 10 minutes, each side.\nAdd your cheese and any other toppings and allow a couple minutes for cheese to melt.");
            cv.put("meal", "Dinner"); cv.put("diet", "Vegetarian"); cv.put("time", "Under 30 minutes"); cv.put("servings", "2");
            cv.put("info", "Calories: 278 Kcal, Carbs: 20g, Fat: 13g, Sugar: 7g, Protein: 23g");
            db.insert("recipes", null, cv);

            cv.put("name", "Chickpea Burgers");
            cv.put("ingredients", "200g chickpeas\n170g corn\nhandful of cilantro\n¼ tsp paprika\n¼ tsp ground cumin\n2 tbsp whole wheat flour\n½ lemon\n4 lettuce leaves\n1 sliced tomato\n2 whole wheat burger buns");
            cv.put("instructions", "Add chickpeas and corn to a food processor. Add paprika, cumin, flour, salt and lemon zest. Pulse until mixture combines.\nDust a cutting board with flour, then shape the veggie mix into 2 even patties. " +
                    "Pop on a plate in the fridge for 20 minutes to chill.\nHeat nut oil in a large skillet on medium heat. Add patties and cook until nice and golden, about 5 minutes on each side. Add a dollop of ketchup to your bun (toasted " +
                    "or fresh) and construct your burger, topping with some cilantro leaves.");
            cv.put("meal", "Dinner"); cv.put("diet", "Vegan"); cv.put("time", "Under 30 minutes"); cv.put("servings", "2");
            cv.put("info", "Calories: 490 Kcal, Carbs: 82g, Fat: 8g, Sugar: 13g, Protein: 23g");
            db.insert("recipes", null, cv);

            cv.put("name", "Choc-Hazelnut Cakes");
            cv.put("ingredients", "300g hazelnuts\n175g pitted dates\n75g cocoa powder\n125ml honey\n1 avocado\ncrushed hazelnuts for garnish");
            cv.put("instructions", "Tip hazelnuts into a food processor and pulse until a flour forms. Add dates, half of the cocoa powder and half of the honey and process until a thick, sticky dough forms.\nSpoon dough onto 2 sheets of " +
                    "parchment paper. Using a rolling pin, roll dough until 1 cm thick. Cut dough into 8 or 10 rectangles of the same size.\nTo make the frosting, blend remaining cacao powder and honey with the avocado until it reaches an even " +
                    "texture and color. Taste, and add extra honey if needed.\nFrost the cake rectangles, using about 2 tablespoons of frosting. Sprinkle on some crushed hazelnuts, then dig in!");
            cv.put("meal", "Dessert"); cv.put("diet", "Low Carb"); cv.put("time", "Under 15 minutes"); cv.put("servings", "");
            cv.put("info", "Calories:  Kcal, Carbs: g, Fat: g, Sugar: g, Protein: g");
            db.insert("recipes", null, cv);

            cv.put("name", "Coco-Cranberry Bars");
            cv.put("ingredients", "80g rolled oats\n2 tbsp chopped nuts\n1 tbsp chia seeds\n3 tbsp shredded coconut\n30g dried cranberries\n6 dates\n65g peanut butter\n3 tbsp honey\n2 tbsp melted coconut oil\n1 tsp vanilla extract");
            cv.put("instructions", "Line a 20x20cm baking tray with parchment paper. Throw oats, nuts, chia seeds and coconut into a bowl and combine. In a blender, blitz the cranberries and dates until well combined and sticky. Pour in the oat " +
                    "mix and blend again until ingredients are well combined.\nIn a large bowl, combine the peanut butter, honey, coconut oil and vanilla and mix well. Add the mixture from the blender to wet ingredients and combine." +
                    "Transfer the mix to the baking tray and press into a layer about 1 cm thick. Place in the fridge to chill for 3 hours.\nRemove the baking tray from the fridge and slice into 14 bars.");
            cv.put("meal", "Snack"); cv.put("diet", "Vegan"); cv.put("time", "3 hours"); cv.put("servings", "14");
            cv.put("info", "Calories: 140 Kcal, Carbs: 17g, Fat: 6g, Sugar: 11g, Protein: 2g");
            db.insert("recipes", null, cv);

            cv.put("name", "Creamy Avo Hummus");
            cv.put("ingredients", "200g chickpeas\n1 avocado\n1 tbsp nut oil\n¾ tbsp tahini\n1½ tbsp lime juice\n½ garlic clove\nsalt and pepper to taste");
            cv.put("instructions", "In a food processor, pulse chickpeas, nut oil, tahini, lime juice and garlic until nice and smooth. Add salt, pepper and the avocado and pulse for a few more " +
                    "minutes until silky smooth. Spoon into a dish and garnish with cilantro and red pepper flakes, along with a dsh more oil. Enjoy with rice crackers");
            cv.put("meal", "Snack"); cv.put("diet", "Low Carb"); cv.put("time", "Under 15 minutes"); cv.put("servings", "2");
            cv.put("info", "Calories: 196 Kcal, Carbs: 8g, Fat: 15g, Sugar: 0g, Protein: 5g");
            db.insert("recipes", null, cv);

            cv.put("name", "Green Machine Smoothie");
            cv.put("ingredients", "30g baby spinach\n85ml apple juice\nhandful of mint leaves\n½ avocado\n100g frozen green grapes\n½ tsp matcha powder\n⅓ tbsp of chia seeds");
            cv.put("instructions", "Blend the spinach, apple juice and mint leaves together. Add in the avocado, grapes, matcha and chia seeds ans continue blending until nice and smooth. Pour into a glass and enjoy!");
            cv.put("meal", "Snack"); cv.put("diet", "Vegan"); cv.put("time", "Under 15 minutes"); cv.put("servings", "1");
            cv.put("info", "Calories: 256 Kcal, Carbs: 37g, Fat: 12g, Sugar: 25g, Protein: 4g");
            db.insert("recipes", null, cv);

            cv.put("name", "Keto Broccoli Salad");
            cv.put("ingredients", "3 heads broccoli\n100g shredded cheddar\n¼ thinly sliced red onion\na few toasted sliced almonds\n3 slices cooked bacon\n2 tbsp freshly chopped chives\n3 tbsp mayonnaise\n3 tbsp apple cider vinegar\n1 tbsp mustard");
            cv.put("instructions", "In a medium pot, bring 6 cups of salted water to a boil. Add broccoli florets to the boiling water and cook until tender, for 1 to 2 minutes. Drain florets and wait to cool.\nIn a medium bowl, combine dressing ingredients. Season to taste " +
                    "with salt and pepper. Combine all salad ingredients in a large bowl and pour dressing.\nToss until ingredients are combined and fully coated in dressing. Refrigerate until ready to serve.");
            cv.put("meal", "Lunch"); cv.put("diet", "Low Carb"); cv.put("time", "Under 30 minutes"); cv.put("servings", "4");
            cv.put("info", "Calories: 266 Kcal, Carbs: 4g, Fat:25 g, Sugar: 2g, Protein: 8g");
            db.insert("recipes", null, cv);

            cv.put("name", "Keto Chocolate Mousse");
            cv.put("ingredients", "2 ripe avocados\n¾ cup heavy cream\n100g melted dark chocolate chips\n2 tbsp honey\n3 tbsp unsweetened cocoa powder\n1 tsp vanilla\n½ tsp kosher salt");
            cv.put("instructions", "In a food processor or blender, blend all ingredients until smooth. Transfer to serving glasses and refrigerate for 30 minutes or up to 1 hour. Garnish with chocolate curls.");
            cv.put("meal", "Dessert"); cv.put("diet", "Vegetarian"); cv.put("time", "Under 30 minutes"); cv.put("servings", "3");
            cv.put("info", "Calories: 146 Kcal, Carbs: 11g, Fat: 13g, Sugar: 4g, Protein: 3g");
            db.insert("recipes", null, cv);

            cv.put("name", "Kickin’ Sesame Chicken Bowls");
            cv.put("ingredients", "130g quinoa\n500g broccoli\n450g snap peas\n500g chicken breast cut into small pieces\n65ml chicken stock or water\n65ml honey\n1 tbsp sesame oil\n3 tbsp whole wheat flour");
            cv.put("instructions", "Whisk together all of the sauce ingredients. Cook quinoa in 1½ cups of water, letting it simmer for about 15 minutes until it's fluffy, then spoon evenly into 4 bowls.\nHeat up oil over medium heat, then cook broccoli " +
                    "and snap peas for approximately 5 minutes until tender. Divide the greens between your bowls.\nAdd an additional drizzle of oil to the skillet, season the chicken and throw it into the pan. Cook chicken until golden, approximately 7 " +
                    "minutes. Pour in the sauce, stir, and allow to simmer for about 2 minutes until sauce thickens.\nSpoon chicken and sauce on top of the quinoa and greens, then sprinkle some sesame seeds.");
            cv.put("meal", "Lunch"); cv.put("diet", "Meat"); cv.put("time", "Under 30 minutes"); cv.put("servings", "4");
            cv.put("info", "Calories: 445 Kcal, Carbs: 54g, Fat: 20g, Sugar: 25g, Protein: 33g");
            db.insert("recipes", null, cv);

            cv.put("name", "Mini Banana Burgers");
            cv.put("ingredients", "1¼ tbsp peanut butter\n190g yogurt\n3 medium bananas");
            cv.put("instructions", "In a bowl, mix together the peanut butter and yogurt. Slice the bananas. " +
                    "Spread half the banana slices with the nutty mixture before placing the plain banana ‘lids’ on top. You should now have a bunch of mini banana burgers. " +
                    "Put your banana burgers into 4 freezer proof plastic boxes and freeze. Dig in any time you need a sweet, creamy treat!");
            cv.put("meal", "Snack"); cv.put("diet", "Vegetarian"); cv.put("time", "Under 15 minutes"); cv.put("servings", "4");
            cv.put("info", "Calories: 130 Kcal, Carbs: 18g, Fat: 3g, Sugar: 16g, Protein: 7.3g");
            db.insert("recipes", null, cv);

            cv.put("name", "Nutty ‘Nana Bowl");
            cv.put("ingredients", "2 bananas\n2½ tbsp solid coconut oil\n115g honey\n2 tsp cinnamon powder\n300g rolled oats\n100g walnut halves");
            cv.put("instructions", "Preheat the oven to 200ºC. With their peel still on, put the bananas in the oven and roast until the outside is brown. Once cooked, allow them to cool slightly before peeling and popping into the electric mixer.\n" +
                    "Melt the coconut oil in a small saucepan before adding it to the mixer along with the honey, cinnamon and salt, then mix on a medium setting. Add the oats and walnuts to the mixing bowl and lightly mix until combined. " +
                    "Spread the mixture onto 2 baking trays covered with baking paper and bake in the oven for 30 mins.\nHalf way through cooking, give your granola a shake to ensure even cooking. Once cooked, turn the oven off but leave the trays " +
                    "inside for a further 15 minutes. Once the granola is crispy, remove it from the oven and leave it to cool and then transfer it into a mason jar.\nBowl up 1 serving and top with Greek yogurt and chopped fresh fruit.");
            cv.put("meal", "Breakfast"); cv.put("diet", "Vegetarian"); cv.put("time", "Under 60 minutes"); cv.put("servings", "10");
            cv.put("info", "Calories: 284 Kcal, Carbs: 30g, Fat: 15g, Sugar: 14g, Protein: 6g");
            db.insert("recipes", null, cv);

            cv.put("name", "Peanut Butter Keto Cookies");
            cv.put("ingredients", "250g almond flour\n6 tbsp powdered sugar substitute\n1 large egg\n½ tsp salt\n240g salted crunchy peanut butter\n115g melted unsalted butter");
            cv.put("instructions", "Preheat the oven to 180ºC and line 2 baking sheets with parchment paper. In a large bowl, mix together the almond flour, powdered sugar substitute," +
                    " egg, salt, peanut butter, and melted butter until thoroughly combined.\nScoop the dough into 14 balls and place on the prepared baking sheets. Flatten the cookies by " +
                    "crossing 2 forks on top and pressing down slightly. Bake the cookies for 10–12 minutes, turning the baking sheet halfway, until fragrant and slightly golden. Remove the" +
                    " cookies from the oven and transfer to a wire rack to cool.");
            cv.put("meal", "Dessert"); cv.put("diet", "Vegetarian"); cv.put("time", "Under 30 minutes"); cv.put("servings", "14");
            cv.put("info", "Calories: 315 Kcal, Carbs: 13g, Fat: 27g, Sugar: 6g, Protein: 9g");
            db.insert("recipes", null, cv);

            cv.put("name", "Tasty Chicken Power Pockets");
            cv.put("ingredients", "260g chicken breast\n1 lime\n1 tsp black pepper\n1 tbsp nut oil\n4 whole wheat tortilla wraps\n½ avocado\n1 handful shredded romaine lettuce\n125g yogurt");
            cv.put("instructions", "Slice the chicken into strips. Add to a large bowl with lime juice, pepper, nut oil and a pinch of salt. Toss well. Heat a skillet on medium heat and throw in chicken strips. Cook for 6-8 " +
                    "minutes, turning occasionally, until cooked through and golden brown.\nWarm up the tortillas, then spread with avocado. Add chicken, lettuce and a dollop of yogurt. Roll up your power pocket and enjoy!");
            cv.put("meal", "Lunch"); cv.put("diet", "Meat"); cv.put("time", "Under 30 minutes"); cv.put("servings", "2");
            cv.put("info", "Calories: 703 Kcal, Carbs: 60g, Fat: 32g, Sugar: 8g, Protein: 44g");
            db.insert("recipes", null, cv);

            cv.put("name", "That’s a (Veggie) Wrap!");
            cv.put("ingredients", "100g black beans\n2tbsp hummus\n2 whole wheat tortillas\n1 bell pepper cut into strips\n25g corn\n5 cherry tomatoes\n½ avocado\n4 slices cheddar cheese\n½ lime");
            cv.put("instructions", "To make black bean hummus, add ¾ of the beans to a food processor with the hummus and pulse until smooth. Spread thickly on your wrap. Add cheese, remaining beans and veggies to your wrap, top with salt, pepper and a squeeze of lime.");
            cv.put("meal", "Lunch"); cv.put("diet", "Vegetarian"); cv.put("time", "Under 15 minutes"); cv.put("servings", "2");
            cv.put("info", "Calories: 484 Kcal, Carbs: 47g, Fat: 23g, Sugar: 0g, Protein: 20g");
            db.insert("recipes", null, cv);

            cv.put("name", "Traffic Light Scramble");
            cv.put("ingredients", "2 eggs\n½ tbsp nut oil\n15g baby spinach\n5 cherry tomatoes\n¼ chopped bell pepper\n1 tbsp grated parmesan cheese\n2 slices whole wheat toast");
            cv.put("instructions", "Crack eggs into a bowl and whisk with salt until frothy. Heat nut oil in skillet on medium/high heat. Toss in spinach and chili flakes and cook, stirring occasionally, " +
                    "until spinach is almost wilted.\nAdd in the chopped tomato and bell pepper and cook for another 30 seconds. Add the eggs and cook for another 30-60 seconds, stirring continuously, until egg is almost " +
                    "soft-cooked. Remove from heat, add cheese and stir together. Plate up and garnish with parsley (optional) and add some pepper.");
            cv.put("meal", "Breakfast"); cv.put("diet", "Vegetarian"); cv.put("time", "Under 15 minutes"); cv.put("servings", "1");
            cv.put("info", "Calories: 304 Kcal, Carbs: 5g, Fat: 23g, Sugar: 5g, Protein: 22g");
            db.insert("recipes", null, cv);

            cv.put("name", "Turkey & Pesto Lean Panini");
            cv.put("ingredients", "4 slices whole wheat bread\n1½ tbsp basil pesto\n4 slices deli turkey breast\n½ red bell pepper\n1 sliced tomato\n4 slices cheddar cheese");
            cv.put("instructions", "Preheat the oven to 200˚C. On a lined baking tray, drizzle some oil over the pepper and roast in the oven for 20 mins.\nMake the sandwich by " +
                    "spreading the pesto on the bread and topping with the turkey, red pepper, tomato and cheese. Grease a grill pan with oil on medium heat, before placing the " +
                    "sandwich in the pan.\nTo complete the panini, compress it by placing a large heavy pan on top of bread and cook each side for approx 3 mins.");
            cv.put("meal", "Dinner"); cv.put("diet", "Meat"); cv.put("time", "Under 30 minutes"); cv.put("servings", "2");
            cv.put("info", "Calories: 490 Kcal, Carbs: 33g, Fat: 28g, Sugar: 6g, Protein: 25g");
            db.insert("recipes", null, cv);

            cv.put("name", "Veggie Pasta");
            cv.put("ingredients", "125g whole wheat fusilli\n1 tsp nut oil\n60g grated parmesan cheese\n1 tbsp chopped basil\n1½ tsp lemon juice\n75g cherry tomatoes\n1 zucchini sliced into wedges");
            cv.put("instructions", "Preheat your oven to 200ºC. Toss tomatoes and zucchini with half the nut oil, salt and pepper, before spreading out on a lined baking tray. Sprinkle half the parmesan and basil on top and pop in the oven. " +
                    "Bake for approximately 20 minutes, tossing halfway.\nAs the veggies roast, bring 2 cups of water and a pinch of salt to boil. Add pasta and cook for 10-12 minutes, or until al dente. Save ½ cup of the pasta water before " +
                    "draining pasta.\nIn a large bowl, combine pasta, lemon juice and the remaining nut oil, parmesan and basil. Add about 1 tablespoon of the pasta water and give it a gentle toss.  When the veggies are done, add them to the pasta.");
            cv.put("meal", "Lunch"); cv.put("diet", "Vegan"); cv.put("time", "Under 60 minutes"); cv.put("servings", "2");
            cv.put("info", "Calories: 190 Kcal, Carbs: 18g, Fat: 6g, Sugar: 3g, Protein: 10g");
            db.insert("recipes", null, cv);

            c.close();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
