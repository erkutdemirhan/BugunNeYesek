package com.erkutdemirhan.bugunneyesek.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

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
    private Intent                  mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        mIntent       = getIntent();
        Recipe recipe = (Recipe) mIntent.getSerializableExtra(RecipeActivity.RECIPE_KEY);
        initToolbar(recipe);
        initImage(recipe);
        ArrayList<Ingredient> selectedIngredients = (ArrayList<Ingredient>) mIntent.getSerializableExtra(RecipeActivity.INGR_LIST_KEY);
        initRecipeContents(recipe, selectedIngredients);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, RecipeListActivity.class);
                if(mIntent.hasExtra(RecipeActivity.INGR_LIST_KEY)) {
                    intent.putExtra(RecipeListActivity.INGR_LIST_KEY, mIntent.getSerializableExtra(RecipeActivity.INGR_LIST_KEY));
                } else if(mIntent.hasExtra(RecipeActivity.RECIPE_KEY)) {
                    Recipe recipe = (Recipe) mIntent.getSerializableExtra(RecipeActivity.RECIPE_KEY);
                    intent.putExtra(RecipeListActivity.RECIPE_TYPE_KEY, BugunNeYesek.getInstance().getRecipeTypeById(recipe.getRecipeTypeId()));
                }
                NavUtils.navigateUpTo(this, intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initToolbar(Recipe recipe) {
        Toolbar toolBar    = (Toolbar) findViewById(R.id.recipe_toolbar);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.recipe_collapsingtoolbar);
        if(toolBar != null && mCollapsingToolbar != null) {
            setSupportActionBar(toolBar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mCollapsingToolbar.setTitle(recipe.getRecipeName());
        }
    }

    private void initImage(Recipe recipe) {
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

    private void initRecipeContents(Recipe recipe, ArrayList<Ingredient> selectedIngredients) {
        ArrayList<Ingredient> ingredientList = recipe.getIngredientList();
        StringBuilder availableIngr          = new StringBuilder();
        StringBuilder unavailableIngr        = new StringBuilder();
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
}
