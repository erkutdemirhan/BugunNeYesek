package com.erkutdemirhan.bugunneyesek.recipes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erkutdemirhan.bugunneyesek.R;

/**
 * Created by Erkut Demirhan on 12.05.2015.
 */
public class RecipesList extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recipes_list, container, false);
        return v;
    }
}