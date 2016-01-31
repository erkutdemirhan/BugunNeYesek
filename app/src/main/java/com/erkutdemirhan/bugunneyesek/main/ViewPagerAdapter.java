package com.erkutdemirhan.bugunneyesek.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.ingredients.SelectIngredients;
import com.erkutdemirhan.bugunneyesek.recipes.RecipesList;

/**
 * Created by Erkut Demirhan on 12.05.2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private CharSequence[] mTitles;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mTitles    = new CharSequence[] {context.getString(R.string.tab_select_ingr),
                                           context.getString(R.string.tab_recipe_list) };
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            SelectIngredients selectIngredients = new SelectIngredients();
            return selectIngredients;
        } else {
            RecipesList recipesList = new RecipesList();
            return recipesList;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }
}
