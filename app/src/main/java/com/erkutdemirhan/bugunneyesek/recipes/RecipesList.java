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

/**
 * Created by Erkut Demirhan on 12.05.2015.
 * Fragment class that holds the list of recipe objects
 */
public class RecipesList extends Fragment {
    private static final String TAG = "RecipesList";

    private RecyclerView       mSelectedRecipesView;
    private RecipesViewAdapter mSelectedRecipesViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipes_list, container, false);
        Log.d(TAG, "onCreateView");
        mSelectedRecipesView = (RecyclerView) rootView.findViewById(R.id.recipes_recyclerview);
        mSelectedRecipesView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mSelectedRecipesView.setLayoutManager(layoutManager);
        mSelectedRecipesViewAdapter              = new RecipesViewAdapter(getActivity());
        mSelectedRecipesView.setAdapter(mSelectedRecipesViewAdapter);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Log.d(TAG, "Fragment is visible");
            mSelectedRecipesViewAdapter.updateRecipes();
        } else {
            Log.d(TAG, "Fragment is invisible");
        }
    }

}
