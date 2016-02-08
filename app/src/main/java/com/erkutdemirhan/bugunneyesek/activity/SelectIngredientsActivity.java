package com.erkutdemirhan.bugunneyesek.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.adapter.IngredientListViewAdapter;
import com.erkutdemirhan.bugunneyesek.database.DbHelperFactory;
import com.erkutdemirhan.bugunneyesek.domain.Ingredient;
import com.erkutdemirhan.bugunneyesek.domain.RecipeType;
import com.erkutdemirhan.bugunneyesek.application.BugunNeYesek;
import com.erkutdemirhan.bugunneyesek.adapter.AutoCompleteArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Erkut on 01/02/16.
 */
public class SelectIngredientsActivity extends AppCompatActivity {

    private static final String TAG                  = "SelectIngredients";
    private static final String SELECTED_INGREDIENTS = "selected_ingredients";

    private Intent                    mIntent;
    private Toolbar                   mToolbar;
    private AutoCompleteTextView      mTextView;
    private TextInputLayout           mTextInputLayout;
    private IngredientListViewAdapter mSelectedIngredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectingredients);
        mIntent = getIntent();
        getWindow().setBackgroundDrawableResource(R.drawable.ingredients_background);
        initToolbar();
        initDrawer();
        initIngredientInputView();
        initIngredientList(savedInstanceState);
        initButton();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(!mSelectedIngredientsAdapter.isEmpty()) {
            outState.putSerializable(SELECTED_INGREDIENTS, mSelectedIngredientsAdapter.getIngredientList());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.selectingredients_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.selectingredients_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addIngredient(Ingredient ingredient) {
        if(ingredient != null) {
            if(!mSelectedIngredientsAdapter.addIngredient(ingredient)) {
                String alert = getString(R.string.selectingredients_already_selected_ingr);
                Toast.makeText(this, alert, Toast.LENGTH_SHORT).show();
            };
        }
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.selectingredients_toolbar);
        if(mToolbar != null) {
            mToolbar.setTitle(getString(R.string.selectingredients_toolbar_title));
            setSupportActionBar(mToolbar);
        }
    }

    private void initButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.selectingredients_showrecipes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mSelectedIngredientsAdapter.isEmpty()) {
                    Intent intent = new Intent(v.getContext(), RecipeListActivity.class);
                    intent.putExtra(RecipeListActivity.INGR_LIST_KEY, mSelectedIngredientsAdapter.getIngredientList());
                    startActivity(intent);
                } else {
                    String alert = getString(R.string.selectingredients_alert_addingr);
                    Toast.makeText(v.getContext(), alert, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initDrawer() {
        final DrawerLayout drawerLayout             = (DrawerLayout) findViewById(R.id.selectingredients_drawer);
        ActionBarDrawerToggle drawerToggle          = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        ArrayList<RecipeType> allRecipeTypes        = new ArrayList<>();
        allRecipeTypes.add(new RecipeType(-1, getString(R.string.all_recipes)));
        allRecipeTypes.addAll(BugunNeYesek.getInstance().getRecipeTypeList());
        ArrayAdapter<RecipeType> drawerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allRecipeTypes);
        ListView drawerListView                     = (ListView) findViewById(R.id.selectingredients_drawerlistview);
        drawerListView.setAdapter(drawerArrayAdapter);
        drawerListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), RecipeListActivity.class);
                intent.putExtra(RecipeListActivity.RECIPE_TYPE_KEY, (RecipeType) parent.getItemAtPosition(position));
                view.getContext().startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    private void initIngredientInputView() {
        mTextInputLayout                      = (TextInputLayout) findViewById(R.id.selectingredients_textinput);
        mTextView                             = (AutoCompleteTextView) findViewById(R.id.selectingredients_autocomplete);
        ArrayList<Ingredient> allIngredients  = DbHelperFactory.getDatabaseHelper(this).getAllIngredients();
        AutoCompleteArrayAdapter arrayAdapter = new AutoCompleteArrayAdapter(this, R.layout.ingredient_select_item, allIngredients);
        mTextView.setAdapter(arrayAdapter);
        mTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ingredient ingr = (Ingredient) parent.getItemAtPosition(position);
                addIngredient(ingr);
                removeIngredientInputFocus();
            }
        });
    }

    private void initIngredientList(Bundle savedInstanceState) {
        RecyclerView ingredientListView          = (RecyclerView) findViewById(R.id.selectingredients_recyclerview);
        ingredientListView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ingredientListView.setLayoutManager(layoutManager);
        if(savedInstanceState != null && savedInstanceState.containsKey(SELECTED_INGREDIENTS)) {
            ArrayList<Ingredient> selectedIngredientList = (ArrayList<Ingredient>) savedInstanceState.getSerializable(SELECTED_INGREDIENTS);
            mSelectedIngredientsAdapter                  = new IngredientListViewAdapter(selectedIngredientList);
        } else if (mIntent.hasExtra(RecipeListActivity.INGR_LIST_KEY)) {
            ArrayList<Ingredient> selectedIngredientList = (ArrayList<Ingredient>) mIntent.getSerializableExtra(RecipeListActivity.INGR_LIST_KEY);
            mSelectedIngredientsAdapter                  = new IngredientListViewAdapter(selectedIngredientList);
        } else {
            mSelectedIngredientsAdapter                  = new IngredientListViewAdapter();
        }
        ingredientListView.setAdapter(mSelectedIngredientsAdapter);
    }

    private void removeIngredientInputFocus() {
        if (mTextView != null && mTextInputLayout != null) {
            // clears the text from autocompletetextview
            if (mTextView.length() > 0) {
                mTextView.getText().clear();
            }
            // removes keyboard input
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mTextView.getWindowToken(), 0);
            // removes focus from the textinputlayout
            mTextInputLayout.clearFocus();
        }
    }
}
