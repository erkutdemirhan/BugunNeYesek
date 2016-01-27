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
import com.erkutdemirhan.bugunneyesek.database.DbHelperFactory;
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

    private static final String TAG = "SelectIngredients";
    private static final int RECIPE_TYPE_ITEM_INDEX = 0;

    private RecyclerView           mSelectedIngredientsView;
    private IngredientsViewAdapter mSelectedIngredientsViewAdapter;

    private Spinner                  mIngredientListSpinner;
    private ArrayAdapter<Ingredient> mIngredientListSpinnerAdapter;
    private List<Ingredient>         mIngredientList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView                 = inflater.inflate(R.layout.select_ingredients, container, false);
        int currentRecipeType         = BugunNeYesek.getInstance().getCurrentRecipeType();
        mIngredientListSpinner        = (Spinner) rootView.findViewById(R.id.select_ingredients_spinner);
        if(currentRecipeType == -1) {
            mIngredientList           = DbHelperFactory.getDatabaseHelper(getActivity()).getAllIngredients();
        } else {
            mIngredientList           = DbHelperFactory.getDatabaseHelper(getActivity()).getIngredientsForRecipeType(currentRecipeType);
        }
        mIngredientListSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mIngredientList);
        mIngredientListSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mIngredientListSpinner.setAdapter(mIngredientListSpinnerAdapter);

        mSelectedIngredientsView      = (RecyclerView) rootView.findViewById(R.id.select_ingredients_recyclerview);
        mSelectedIngredientsView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mSelectedIngredientsView.setLayoutManager(layoutManager);

        mSelectedIngredientsViewAdapter = new IngredientsViewAdapter();
        mSelectedIngredientsView.setAdapter(mSelectedIngredientsViewAdapter);

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

    /**
     * Adds the ingredient that is selected by the user, into the ingredient list adapter.
     */
    private void addIngredient() {
        Ingredient ingredient = (Ingredient) mIngredientListSpinner.getSelectedItem();
        if(ingredient != null) {
            if(!mSelectedIngredientsViewAdapter.addIngredient(ingredient)) {
                Toast.makeText(this.getActivity(), "Bu malzemeyi daha önce eklediniz!", Toast.LENGTH_SHORT).show();
            };
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.select_ingredients_menu, menu);
        ArrayList<RecipeType> recipeTypes = BugunNeYesek.getInstance().getRecipeTypeList();
        Menu recipeTypeMenu               = menu.getItem(RECIPE_TYPE_ITEM_INDEX).getSubMenu();
        recipeTypeMenu.add(R.id.recipe_type_group, -1, Menu.NONE, R.string.option_all_recipes);
        for(RecipeType type: recipeTypes) {
            recipeTypeMenu.add(R.id.recipe_type_group, type.getTypeId(), Menu.NONE, type.getTypeName());
        }
        recipeTypeMenu.setGroupCheckable(R.id.recipe_type_group, true, true);
        recipeTypeMenu.findItem(BugunNeYesek.getInstance().getCurrentRecipeType()).setChecked(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        for(RecipeType recipeType:BugunNeYesek.getInstance().getRecipeTypeList()) {
            if(id == -1 || id == recipeType.getTypeId()) {
                Log.d("onOptionsItemSelected", "Recipe type id: " + id);
                mIngredientList.clear();
                if(id == -1) {
                    mIngredientList.addAll(DbHelperFactory.getDatabaseHelper(getActivity()).getAllIngredients());
                } else {
                    mIngredientList.addAll(DbHelperFactory.getDatabaseHelper(getActivity()).getIngredientsForRecipeType(id));
                }
                mIngredientListSpinnerAdapter.notifyDataSetChanged();
                mSelectedIngredientsViewAdapter.removeAllIngredients();
                BugunNeYesek.getInstance().setCurrentRecipeType(id);
                item.setChecked(true);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
