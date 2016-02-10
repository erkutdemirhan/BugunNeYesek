package com.erkutdemirhan.bugunneyesek.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.domain.Ingredient;
import com.erkutdemirhan.bugunneyesek.domain.Recipe;
import com.erkutdemirhan.bugunneyesek.application.BugunNeYesek;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Erkut Demirhan on 03.06.2015.
 */
public class RecipeActivity extends AppCompatActivity {

    public static final String RECIPE_KEY    = "recipe_key";
    public static final String INGR_LIST_KEY = "ingr_list_key";

    private static final String TAG          = "RecipeActivity";

    private CollapsingToolbarLayout mCollapsingToolbar;
    private Bundle                  mActivityState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        if(savedInstanceState != null) {
            mActivityState = savedInstanceState;
        } else {
            mActivityState = getIntent().getExtras();
        }
        initToolbar();
        initImage();
        initRecipeContents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.recipeactivity_showshoppinglist:
                Intent intent = new Intent(this, ShoppingListActivity.class);
                startActivity(intent);
                return true;
            case R.id.recipeactivity_addshoppinglist:
                addMissingIngrToShoppingList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putAll(mActivityState);
    }

    private void initToolbar() {
        Recipe recipe      = (Recipe) mActivityState.getSerializable(RecipeActivity.RECIPE_KEY);
        Toolbar toolBar    = (Toolbar) findViewById(R.id.recipe_toolbar);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.recipe_collapsingtoolbar);
        if(toolBar != null && mCollapsingToolbar != null) {
            setSupportActionBar(toolBar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mCollapsingToolbar.setTitle(recipe.getRecipeName());
        }
    }

    private void initImage() {
        Recipe recipe         = (Recipe) mActivityState.getSerializable(RecipeActivity.RECIPE_KEY);
        ImageView recipeImage = (ImageView) findViewById(R.id.recipe_image);
        try {
            InputStream ims = BugunNeYesek.getInstance().getAssets().open("images/" + recipe.getImageFileName());
            Drawable d      = Drawable.createFromStream(ims, null);
            recipeImage.setImageDrawable(d);
            ims.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initRecipeContents() {
        Recipe recipe                             = (Recipe) mActivityState.getSerializable(RecipeActivity.RECIPE_KEY);
        ArrayList<Ingredient> selectedIngredients = (ArrayList<Ingredient>) mActivityState.getSerializable(RecipeActivity.INGR_LIST_KEY);
        ArrayList<Ingredient> ingredientList      = recipe.getIngredientList();
        StringBuilder availableIngr               = new StringBuilder();
        StringBuilder unavailableIngr             = new StringBuilder();
        for(Ingredient ingr:ingredientList) {
            String line = ingr.getIngredientName();
            line       += (ingr.getIngredientAmount().equalsIgnoreCase("")) ? "\n" : " (" + ingr.getIngredientAmount() + ")\n";
            if (selectedIngredients != null && selectedIngredients.contains(ingr)) {
                availableIngr.append(line);
            } else {
                unavailableIngr.append(line);
            }
        }
        TextView availableIngrText   = (TextView) findViewById(R.id.recipe_availableingr);
        TextView unavailableIngrText = (TextView) findViewById(R.id.recipe_unavailableingr);
        TextView instructionText     = (TextView) findViewById(R.id.recipe_instructions);
        if(availableIngr.length() <= 0)   availableIngr.append("-");
        if(unavailableIngr.length() <= 0) unavailableIngr.append("-");
        availableIngrText.setText(availableIngr.toString());
        unavailableIngrText.setText(unavailableIngr.toString());
        instructionText.setText(recipe.getInstructions());
    }

    private void addMissingIngrToShoppingList() {
        ArrayList<Ingredient> missingIngredients   = new ArrayList<>();
        Recipe recipe                              = (Recipe) mActivityState.getSerializable(RecipeActivity.RECIPE_KEY);
        ArrayList<Ingredient> availableIngredients = (ArrayList<Ingredient>) mActivityState.getSerializable(RecipeActivity.INGR_LIST_KEY);
        if(availableIngredients != null) {
            for (Ingredient ingr : recipe.getIngredientList()) {
                if (!availableIngredients.contains(ingr)) {
                    missingIngredients.add(ingr);
                }
            }
        } else {
            missingIngredients = recipe.getIngredientList();
        }
        BugunNeYesek.getInstance().addToShoppingList(missingIngredients);
        Toast.makeText(this, getString(R.string.add_shopping_list_message), Toast.LENGTH_SHORT).show();
    }
}
