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
import android.view.Menu;
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

    private Bundle                mActivityState;
    private Toolbar               mToolbar;
    private RecipeListViewAdapter mRecipeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipelist);
        if(savedInstanceState != null) {
            mActivityState = savedInstanceState;
        } else {
            mActivityState = getIntent().getExtras();
        }
        initToolbar();
        initRecipeList();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putAll(mActivityState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipelist_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.recipelist_showshoppinglist:
                Intent intent = new Intent(this, ShoppingListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initToolbar() {
        mToolbar            = (Toolbar) findViewById(R.id.recipelist_toolbar);
        String toolbarTitle = "";
        if(mActivityState.containsKey(RecipeListActivity.RECIPE_TYPE_KEY)) {
            toolbarTitle    = getString(R.string.all_recipes);
            RecipeType type = (RecipeType) mActivityState.getSerializable(RecipeListActivity.RECIPE_TYPE_KEY);
            toolbarTitle    = (type.getTypeId()==-1) ?
                    toolbarTitle:
                    toolbarTitle + " (" + type.getTypeName() + ")" ;
        } else if(mActivityState.containsKey(RecipeListActivity.INGR_LIST_KEY)) {
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
        if(mActivityState.containsKey(RecipeListActivity.RECIPE_TYPE_KEY)) {
            RecipeType type  = (RecipeType) mActivityState.getSerializable(RecipeListActivity.RECIPE_TYPE_KEY);
            if(type.getTypeId() == -1) {
                recipeList   = DbHelperFactory.getDatabaseHelper(this).getAllRecipes();
            } else {
                recipeList   = DbHelperFactory.getDatabaseHelper(this).getRecipesOfGivenType(type.getTypeId());
            }
            mRecipeListAdapter = new RecipeListViewAdapter(recipeList, null);
        } else if(mActivityState.containsKey(RecipeListActivity.INGR_LIST_KEY)) {
            ArrayList<Ingredient> ingrList = (ArrayList<Ingredient>) mActivityState.getSerializable(RecipeListActivity.INGR_LIST_KEY);
            recipeList                     = DbHelperFactory.getDatabaseHelper(this).getAllRecipesFromIngrList(ingrList);
            mRecipeListAdapter             = new RecipeListViewAdapter(recipeList, ingrList);
        }
        recipeListView.setAdapter(mRecipeListAdapter);
    }
}
