package com.erkutdemirhan.bugunneyesek.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.adapter.RecipeListViewAdapter;
import com.erkutdemirhan.bugunneyesek.database.DbHelperFactory;
import com.erkutdemirhan.bugunneyesek.domain.Ingredient;
import com.erkutdemirhan.bugunneyesek.domain.Recipe;
import com.erkutdemirhan.bugunneyesek.domain.RecipeType;
import com.erkutdemirhan.bugunneyesek.utils.StringUtils;

import java.util.ArrayList;

/**
 * Created by Erkut Demirhan on 01/02/16.
 */
public class RecipeListActivity extends AppCompatActivity {

    public static final String RECIPE_TYPE_KEY = "recipe_type";
    public static final String INGR_LIST_KEY   = "ingredient_list";

    private static final String TAG = "RecipeListActivity";

    private Bundle                mActivityState;
    private Toolbar               mToolbar;
    private RecyclerView          mRecipeListView;
    private RecipeListViewAdapter mRecipeListAdapter;
    private ArrayList<Recipe>     mRecipeList;

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
        final MenuItem item         = menu.findItem(R.id.recipelist_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint(getString(R.string.search_recipe));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && newText.length() > 1) {
                    ArrayList<Recipe> filteredList = filter(mRecipeList, newText);
                    mRecipeListAdapter.updateRecipeList(filteredList);
                    mRecipeListView.scrollToPosition(0);
                } else if (newText != null && newText.length() == 0) {
                    mRecipeListAdapter.updateRecipeList(mRecipeList);
                    mRecipeListView.scrollToPosition(0);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
            RecipeType type = (RecipeType) mActivityState.getSerializable(RecipeListActivity.RECIPE_TYPE_KEY);
            if(type.getTypeId() == -1) {
                toolbarTitle = getString(R.string.all_recipes);
            } else if(type.getTypeId() == -2) {
                toolbarTitle = getString(R.string.my_favorite_recipes);
            } else {
                toolbarTitle = toolbarTitle + " (" + type.getTypeName() + ")" ;
            }
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
        mRecipeListView                            = (RecyclerView) findViewById(R.id.recipelist_recyclerview);
        mRecipeListView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutPortrait  = new GridLayoutManager(this, 2);
        RecyclerView.LayoutManager layoutLandscape = new GridLayoutManager(this, 3);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecipeListView.setLayoutManager(layoutPortrait);
        } else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mRecipeListView.setLayoutManager(layoutLandscape);
        }
        mRecipeList = new ArrayList<>();
        if(mActivityState.containsKey(RecipeListActivity.RECIPE_TYPE_KEY)) {
            RecipeType type  = (RecipeType) mActivityState.getSerializable(RecipeListActivity.RECIPE_TYPE_KEY);
            if(type.getTypeId() == -1) {
                mRecipeList = DbHelperFactory.getDatabaseHelper(this).getAllRecipes();
            } else if(type.getTypeId() == -2) {
                mRecipeList = DbHelperFactory.getDatabaseHelper(this).getAllFavoriteRecipes();
            } else {
                mRecipeList = DbHelperFactory.getDatabaseHelper(this).getRecipesOfGivenType(type.getTypeId());
            }
            mRecipeListAdapter = new RecipeListViewAdapter(mRecipeList, null);
        } else if(mActivityState.containsKey(RecipeListActivity.INGR_LIST_KEY)) {
            ArrayList<Ingredient> ingrList = (ArrayList<Ingredient>) mActivityState.getSerializable(RecipeListActivity.INGR_LIST_KEY);
            mRecipeList                    = DbHelperFactory.getDatabaseHelper(this).getAllRecipesFromIngrList(ingrList);
            mRecipeListAdapter             = new RecipeListViewAdapter(mRecipeList, ingrList);
        }
        mRecipeListView.setAdapter(mRecipeListAdapter);
    }

    private ArrayList<Recipe> filter(ArrayList<Recipe> recipeList, String query) {
        if(query.length() < 1) return recipeList;
        final String filterText      = StringUtils.toNonTurkish(query).toLowerCase();
        ArrayList<Recipe> resultList = new ArrayList<>();
        for(Recipe recipe: recipeList) {
            final String filteredText = StringUtils.toNonTurkish(recipe.getRecipeName()).toLowerCase();
            if(filteredText.contains(filterText)) {
                resultList.add(recipe);
            }
        }
        return resultList;
    }
}
