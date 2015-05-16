package com.erkutdemirhan.bugunneyesek.ingredients;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.erkutdemirhan.bugunneyesek.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erkut Demirhan on 12.05.2015.
 */
public class SelectIngredients extends Fragment {

    private static final String INGR_LIST_KEY = "ingredients_list_key";

    private List<String> mIngredients;

    private RecyclerView mRecyclerView;
    private IngredientsViewAdapter mIngredientsViewAdapter;

    private Spinner mSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.select_ingredients, container, false);

        populateIngredientsList();

        mSpinner = (Spinner) rootView.findViewById(R.id.select_ingredients_spinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mIngredients);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinnerAdapter);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.select_ingredients_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        ArrayList<String> ingredientsList;
        if(savedInstanceState != null) {
            ingredientsList = savedInstanceState.getStringArrayList(INGR_LIST_KEY);
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

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(INGR_LIST_KEY, mIngredientsViewAdapter.getIngredientsList());
    }

    private void populateIngredientsList() {
        mIngredients = new ArrayList<>();
        mIngredients.add("Domates");
        mIngredients.add("Patates");
        mIngredients.add("Limon");
        mIngredients.add("Ayçiçek Yağı");
        mIngredients.add("Tuz");
        mIngredients.add("Karabiber");
        mIngredients.add("Patlıcan");
        mIngredients.add("Havuç");
        mIngredients.add("Ispanak");
        mIngredients.add("Salatalık");
    }

    private void addIngredient() {
        String ingredient = String.valueOf(mSpinner.getSelectedItem());
        if(!ingredient.equals("")) {
            if(mIngredientsViewAdapter.isItemAddedBefore(ingredient)) {
                Toast.makeText(this.getActivity(), "Bu malzemeyi listeye daha önceden eklediniz!", Toast.LENGTH_SHORT).show();
            } else {
                mIngredientsViewAdapter.addItem(ingredient);
            }
        }
    }
}
