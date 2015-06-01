package com.erkutdemirhan.bugunneyesek.recipes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.domain.RecipeType;
import com.erkutdemirhan.bugunneyesek.main.BugunNeYesek;

/**
 * Created by Erkut Demirhan on 12.05.2015.
 * Fragment class that holds the list of recipe objects
 */
public class RecipesList extends Fragment {

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipes_list, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recipes_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        BugunNeYesek bugunNeYesek = (BugunNeYesek) getActivity().getApplication();
        RecipesViewAdapter recipesViewAdapter = new RecipesViewAdapter(bugunNeYesek.getRecipeListMap().get(RecipeType.MAIN_COURSE));
        mRecyclerView.setAdapter(recipesViewAdapter);

        return rootView;
    }
}
