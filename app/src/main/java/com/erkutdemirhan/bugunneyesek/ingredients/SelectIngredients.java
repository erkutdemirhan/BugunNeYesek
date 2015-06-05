package com.erkutdemirhan.bugunneyesek.ingredients;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.domain.Ingredient;
import com.erkutdemirhan.bugunneyesek.domain.RecipeType;
import com.erkutdemirhan.bugunneyesek.main.BugunNeYesek;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erkut Demirhan on 12.05.2015.
 * Implements the fragment that the user creates an ingredient list
 */
public class SelectIngredients extends Fragment {

    /** Key to save user defined ingredient list into the saved instance state bundle  */
    private static final String INGR_LIST_KEY = "ingredients_list_key";

    private static final int RECIPE_TYPE_ITEM_INDEX = 0;

    private BugunNeYesek mBugunNeYesek;

    private RecyclerView mRecyclerView;
    private IngredientsViewAdapter mIngredientsViewAdapter;

    private Spinner mSpinner;
    private ArrayAdapter<Ingredient> mSpinnerAdapter;
    private List<Ingredient> mSpinnerList;

    private Menu mRecipeTypeMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.select_ingredients, container, false);

        mBugunNeYesek = (BugunNeYesek) getActivity().getApplication();

        mSpinner = (Spinner) rootView.findViewById(R.id.select_ingredients_spinner);
        mSpinnerList = new ArrayList<>();
        mSpinnerList.addAll(mBugunNeYesek.getIngredientListMap().get(mBugunNeYesek.getCurrentRecipeType()));
        mSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mSpinnerList);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mSpinnerAdapter);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.select_ingredients_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        ArrayList<Ingredient> ingredientsList;
        if(savedInstanceState != null) {
            ingredientsList = savedInstanceState.getParcelableArrayList(INGR_LIST_KEY);
        } else {
            ingredientsList = new ArrayList<>();
        }

        mIngredientsViewAdapter = new IngredientsViewAdapter(mRecyclerView, ingredientsList);
        mRecyclerView.setAdapter(mIngredientsViewAdapter);

        Button addIngredientsButton = (Button) rootView.findViewById(R.id.select_ingredients_button);
        addIngredientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredient();
            }
        });

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(INGR_LIST_KEY, mIngredientsViewAdapter.getIngredientsList());
    }

    /**
     * Adds the ingredient that is selected by the user, into the ingredient list adapter.
     */
    private void addIngredient() {
        Ingredient ingredient = (Ingredient) mSpinner.getSelectedItem();
        if(ingredient != null) {
            if(mIngredientsViewAdapter.isItemAddedBefore(ingredient)) {
                Toast.makeText(this.getActivity(), "Bu malzemeyi listeye daha önceden eklediniz!", Toast.LENGTH_SHORT).show();
            } else {
                mIngredientsViewAdapter.addItem(ingredient);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.select_ingredients_menu, menu);

        mRecipeTypeMenu = menu.getItem(RECIPE_TYPE_ITEM_INDEX).getSubMenu();
        for(RecipeType type:RecipeType.values()) {
            mRecipeTypeMenu.add(R.id.recipe_type_group, Menu.FIRST + type.ordinal(), type.ordinal(), type.getText());
        }
        mRecipeTypeMenu.setGroupCheckable(R.id.recipe_type_group, true, true);
        mRecipeTypeMenu.getItem(mBugunNeYesek.getCurrentRecipeType().ordinal()).setChecked(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        for(RecipeType type:RecipeType.values()) {
            if(Menu.FIRST + type.ordinal() == id) {
                Log.d("onOptionsItemSelected", "Recipe type: "+type.getText());
                mBugunNeYesek.setCurrentRecipeType(type);
                mBugunNeYesek.getRecipesViewAdapter().removeAllRecipes();
                mBugunNeYesek.getRecipesViewAdapter().addRecipes(mBugunNeYesek.getRecipeListMap().get(mBugunNeYesek.getCurrentRecipeType()));
                mSpinnerList.clear();
                mSpinnerList.addAll(mBugunNeYesek.getIngredientListMap().get(mBugunNeYesek.getCurrentRecipeType()));
                mSpinnerAdapter.notifyDataSetChanged();

                mIngredientsViewAdapter.removeAll();
                item.setChecked(true);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
