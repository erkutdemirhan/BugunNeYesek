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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.erkutdemirhan.bugunneyesek.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erkut Demirhan on 12.05.2015.
 */
public class SelectIngredients extends Fragment {

    private List<String> mIngredients;
    private AutoCompleteTextView mAutoCompleteTextView;
    private ArrayAdapter<String> mAutoCompleteAdapter;

    private RecyclerView mRecyclerView;
    private IngredientsViewAdapter mIngredientsViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button mAddIngredientButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.select_ingredients, container, false);

        populateIngredientsList();

        mAutoCompleteTextView = (AutoCompleteTextView) rootView.findViewById(R.id.select_ingredients_autocomplete);
        mAutoCompleteAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, mIngredients);
        mAutoCompleteTextView.setAdapter(mAutoCompleteAdapter);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.select_ingredients_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mIngredientsViewAdapter = new IngredientsViewAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mIngredientsViewAdapter);

        mAddIngredientButton = (Button) rootView.findViewById(R.id.select_ingredients_button);
        mAddIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredient();
            }
        });

        return rootView;
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
        String ingredient = mAutoCompleteTextView.getText().toString();
        if(!ingredient.equals("")) {
            if(mIngredientsViewAdapter.isItemAddedBefore(ingredient)) {
                Toast.makeText(this.getActivity(), "Bu malzemeyi listeye daha önceden eklediniz!", Toast.LENGTH_SHORT).show();
            } else {
                mIngredientsViewAdapter.addItem(ingredient);
            }
        }
    }
}
