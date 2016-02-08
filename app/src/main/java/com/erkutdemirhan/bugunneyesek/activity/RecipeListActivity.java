package com.erkutdemirhan.bugunneyesek.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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

    private Intent                mIntent;
    private Toolbar               mToolbar;
    private RecipeListViewAdapter mRecipeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipelist);
        mIntent = getIntent();
        initToolbar();
        initRecipeList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, SelectIngredientsActivity.class);
                if(mIntent.hasExtra(RecipeListActivity.INGR_LIST_KEY)) {
                    intent.putExtra(RecipeListActivity.INGR_LIST_KEY, mIntent.getSerializableExtra(RecipeListActivity.INGR_LIST_KEY));
                }
                NavUtils.navigateUpTo(this, intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initToolbar() {
        mToolbar            = (Toolbar) findViewById(R.id.recipelist_toolbar);
        String toolbarTitle = "";
        if(mIntent.hasExtra(RECIPE_TYPE_KEY)) {
            toolbarTitle    = getString(R.string.all_recipes);
            RecipeType type = (RecipeType) mIntent.getSerializableExtra(RECIPE_TYPE_KEY);
            toolbarTitle    = (type.getTypeId()==-1) ?
                    toolbarTitle:
                    toolbarTitle + " (" + type.getTypeName() + ")" ;
        } else if(mIntent.hasExtra(INGR_LIST_KEY)) {
            toolbarTitle    = getString(R.string.recipelist_toolbar_title_ingr);
        }
        if(mToolbar != null) {
            mToolbar.setTitle(toolbarTitle);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initRecipeList() {
        RecyclerView recipeListView                = (RecyclerView) findViewById(R.id.recipelist_recyclerview);
        recipeListView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutPortrait  = new GridLayoutManager(this, 2);
        RecyclerView.LayoutManager layoutLandscape = new GridLayoutManager(this, 3);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recipeListView.setLayoutManager(layoutPortrait);
        } else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recipeListView.setLayoutManager(layoutLandscape);
        }
        ArrayList<Recipe> recipeList = new ArrayList<>();
        if(mIntent.hasExtra(RECIPE_TYPE_KEY)) {
            RecipeType type  = (RecipeType) mIntent.getSerializableExtra(RECIPE_TYPE_KEY);
            if(type.getTypeId() == -1) {
                recipeList   = DbHelperFactory.getDatabaseHelper(this).getAllRecipes();
            } else {
                recipeList   = DbHelperFactory.getDatabaseHelper(this).getRecipesOfGivenType(type.getTypeId());
            }
            mRecipeListAdapter = new RecipeListViewAdapter(recipeList, null);
        } else if(mIntent.hasExtra(INGR_LIST_KEY)) {
            ArrayList<Ingredient> ingrList = (ArrayList<Ingredient>) mIntent.getSerializableExtra(INGR_LIST_KEY);
            recipeList                     = DbHelperFactory.getDatabaseHelper(this).getAllRecipesFromIngrList(ingrList);
            mRecipeListAdapter             = new RecipeListViewAdapter(recipeList, ingrList);
        }
        recipeListView.setAdapter(mRecipeListAdapter);
    }
}
