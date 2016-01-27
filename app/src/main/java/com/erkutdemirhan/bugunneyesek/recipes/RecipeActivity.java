package com.erkutdemirhan.bugunneyesek.recipes;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.domain.Ingredient;
import com.erkutdemirhan.bugunneyesek.domain.Recipe;
import com.erkutdemirhan.bugunneyesek.main.BugunNeYesek;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Erkut Demirhan on 03.06.2015.
 */
public class RecipeActivity extends AppCompatActivity {

    private ImageView mRecipeImage;
    private Toolbar   mToolbar;
    private TextView  mRecipeTitle;
    private TextView  mAvailableIngr;
    private TextView  mUnavailableIngr;
    private TextView  mRecipeInstructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mRecipeImage        = (ImageView) findViewById(R.id.recipe_activity_image);
        mToolbar            = (Toolbar)   findViewById(R.id.recipe_activity_tool_bar);
        mRecipeTitle        = (TextView)  findViewById(R.id.recipe_activity_header);
        mAvailableIngr      = (TextView)  findViewById(R.id.recipe_activity_available_ingredients);
        mUnavailableIngr    = (TextView)  findViewById(R.id.recipe_activity_unavailable_ingredients);
        mRecipeInstructions = (TextView)  findViewById(R.id.recipe_activity_content_text);

        Recipe recipe    = (Recipe) getIntent().getSerializableExtra(Recipe.KEY);

        mRecipeTitle       .setText(recipe.getRecipeName());
        mRecipeInstructions.setText(recipe.getInstructions());
        try {
            InputStream ims = BugunNeYesek.getInstance().getAssets().open("images/"+recipe.getImageFileName());
            Drawable d      = Drawable.createFromStream(ims, null);
            mRecipeImage.setImageDrawable(d);
            ims.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        initIngredientLists(recipe);

        if(mToolbar != null) {
            mToolbar.setTitle("Tarif Listesi");
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    private void initIngredientLists(Recipe recipe) {
        ArrayList<Ingredient> userIngredients = BugunNeYesek.getInstance().getUserIngredientList();
        StringBuilder availableIngr   = new StringBuilder();
        StringBuilder unavailableIngr = new StringBuilder();
        for(Ingredient ingr:recipe.getIngredientList()) {
            String amount = ingr.getIngredientAmount();
            if(userIngredients.contains(ingr)) {
                availableIngr.append("* " + ingr.getIngredientName());
                if(!amount.equalsIgnoreCase("")) availableIngr.append(" (" + amount + ")");
                availableIngr.append("\n");
            } else {
                unavailableIngr.append("* " + ingr.getIngredientName());
                if(!amount.equalsIgnoreCase("")) unavailableIngr.append(" (" + amount + ")");
                unavailableIngr.append("\n");
            }
        }
        mAvailableIngr  .setText(availableIngr.toString());
        mUnavailableIngr.setText(unavailableIngr.toString());
    }

}
