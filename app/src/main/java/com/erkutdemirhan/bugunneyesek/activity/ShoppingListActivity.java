package com.erkutdemirhan.bugunneyesek.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.adapter.IngredientListViewAdapter;
import com.erkutdemirhan.bugunneyesek.application.BugunNeYesek;
import com.erkutdemirhan.bugunneyesek.domain.Ingredient;

import java.util.ArrayList;

/**
 * Created by Erkut Demirhan on 10/02/16.
 */
public class ShoppingListActivity extends AppCompatActivity {

    private static final String SHOPPING_LIST = "shopping_list";

    private IngredientListViewAdapter mShoppingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist);
        initToolbar();
        initIngredientList(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shoppinglist_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                BugunNeYesek.getInstance().updateShoppingList(mShoppingListAdapter.getIngredientList());
                finish();
                return true;
            case R.id.shoppinglist_delete:
                mShoppingListAdapter.clearIngredientList();
                return true;
            case R.id.shoppinglist_share:
                String text = shoppingListToString();
                if(text == null) {
                    Toast.makeText(this, getString(R.string.no_item_shopping_list), Toast.LENGTH_SHORT).show();
                } else {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, text);
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, getString(R.string.shopping_list_share_message)));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BugunNeYesek.getInstance().updateShoppingList(mShoppingListAdapter.getIngredientList());
        outState.putSerializable(ShoppingListActivity.SHOPPING_LIST, mShoppingListAdapter.getIngredientList());
    }

    private void initIngredientList(Bundle savedInstanceState) {
        RecyclerView shoppingListView            = (RecyclerView) findViewById(R.id.shoppinglist_recyclerview);
        shoppingListView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        shoppingListView.setLayoutManager(layoutManager);
        ArrayList<Ingredient> shoppingList;
        if(savedInstanceState != null && savedInstanceState.containsKey(ShoppingListActivity.SHOPPING_LIST)) {
            shoppingList = (ArrayList<Ingredient>) savedInstanceState.getSerializable(ShoppingListActivity.SHOPPING_LIST);
        } else {
            shoppingList = BugunNeYesek.getInstance().getShoppingList();
        }
        mShoppingListAdapter = new IngredientListViewAdapter(shoppingList);
        shoppingListView.setAdapter(mShoppingListAdapter);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.shoppinglist_toolbar);
        if(toolbar != null) {
            toolbar.setTitle(R.string.shopping_list);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private String shoppingListToString() {
        StringBuilder sb = new StringBuilder();
        if(mShoppingListAdapter.isEmpty()) return null;
        sb.append(getString(R.string.shopping_list) + ":\n");
        for(Ingredient ingr:mShoppingListAdapter.getIngredientList()) {
            sb.append(ingr.getIngredientName());
            sb.append("\n");
        }
        return sb.toString();
    }
}
