package com.erkutdemirhan.bugunneyesek.recipes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.main.BugunNeYesek;

/**
 * Created by Erkut Demirhan on 12.05.2015.
 * Fragment class that holds the list of recipe objects
 */
public class RecipesList extends Fragment {
    private static final String TAG = "RecipesList";

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipes_list, container, false);
        Log.d(TAG, "onCreateView");
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recipes_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        RecipesViewAdapter recipesViewAdapter = new RecipesViewAdapter(getActivity());
        mRecyclerView.setAdapter(recipesViewAdapter);
        return rootView;
    }
}
