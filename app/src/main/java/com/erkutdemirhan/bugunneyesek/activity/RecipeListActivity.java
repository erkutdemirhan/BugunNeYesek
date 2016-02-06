package com.erkutdemirhan.bugunneyesek.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.adapter.RecipeListViewAdapter;
import com.erkutdemirhan.bugunneyesek.database.DbHelperFactory;
import com.erkutdemirhan.bugunneyesek.domain.Ingredient;
import com.erkutdemirhan.bugunneyesek.domain.Recipe;
import com.erkutdemirhan.bugunneyesek.domain.RecipeType;

import java.util.ArrayList;

/**
 * Created by Erkut on 01/02/16.
 */
public class RecipeListActivity extends AppCompatActivity {

    public static final String RECIPE_TYPE_KEY = "recipe_type";
    public static final String INGR_LIST_KEY   = "ingredient_list";

    private static final String TAG = "RecipeListActivity";

    private Toolbar               mToolbar;
    private RecipeListViewAdapter mRecipeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipelist);
        Intent intent = getIntent();
        initToolbar(intent);
        initRecipeList(intent);
    }

    private void initToolbar(Intent intent) {
        mToolbar            = (Toolbar) findViewById(R.id.recipelist_toolbar);
        String toolbarTitle = "";
        if(intent != null && intent.hasExtra(RECIPE_TYPE_KEY)) {
            toolbarTitle    = getString(R.string.all_recipes);
            RecipeType type = (RecipeType) intent.getSerializableExtra(RECIPE_TYPE_KEY);
            toolbarTitle    = (type.getTypeId()==-1) ?
                    toolbarTitle:
                    toolbarTitle + " (" + type.getTypeName() + ")" ;
        } else if(intent != null && intent.hasExtra(INGR_LIST_KEY)) {
            toolbarTitle    = getString(R.string.recipelist_toolbar_title_ingr);
        }
        if(mToolbar != null) {
            mToolbar.setTitle(toolbarTitle);
            setSupportActionBar(mToolbar);
        }
    }

    private void initRecipeList(Intent intent) {
        RecyclerView recipeListView               = (RecyclerView) findViewById(R.id.recipelist_recyclerview);
        recipeListView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager  = new GridLayoutManager(this, 2);
        recipeListView.setLayoutManager(layoutManager);
        ArrayList<Recipe> recipeList = new ArrayList<>();
        if(intent != null && intent.hasExtra(RECIPE_TYPE_KEY)) {
            RecipeType type  = (RecipeType) intent.getSerializableExtra(RECIPE_TYPE_KEY);
            if(type.getTypeId() == -1) {
                recipeList   = DbHelperFactory.getDatabaseHelper(this).getAllRecipes();
            } else {
                recipeList   = DbHelperFactory.getDatabaseHelper(this).getRecipesOfGivenType(type.getTypeId());
            }
            mRecipeListAdapter = new RecipeListViewAdapter(recipeList, null);
        } else if(intent != null && intent.hasExtra(INGR_LIST_KEY)) {
            ArrayList<Ingredient> ingrList = (ArrayList<Ingredient>) intent.getSerializableExtra(INGR_LIST_KEY);
            recipeList                     = DbHelperFactory.getDatabaseHelper(this).getAllRecipesFromIngrList(ingrList);
            mRecipeListAdapter             = new RecipeListViewAdapter(recipeList, ingrList);
        }
        recipeListView.setAdapter(mRecipeListAdapter);
    }
}
