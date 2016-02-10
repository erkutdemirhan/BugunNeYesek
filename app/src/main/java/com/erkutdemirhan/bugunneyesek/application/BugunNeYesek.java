package com.erkutdemirhan.bugunneyesek.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.database.DbHelperFactory;
import com.erkutdemirhan.bugunneyesek.domain.Ingredient;
import com.erkutdemirhan.bugunneyesek.domain.RecipeType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by Erkut Demirhan on 21.05.2015.
 * Application class to hold global variables used throughout the application.
 */
public class BugunNeYesek extends Application {

    private static BugunNeYesek sInstance;

    private ArrayList<RecipeType> mRecipeTypeList;
    private HashSet<Ingredient>   mShoppingList;

    public static BugunNeYesek getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance           = this;
        mShoppingList       = new HashSet<>();
        mRecipeTypeList     = new ArrayList<>();
        mRecipeTypeList.add(new RecipeType(-1, getString(R.string.all_recipes)));
        mRecipeTypeList.addAll(DbHelperFactory.getDatabaseHelper(getApplicationContext()).getAllRecipeTypes());
    }

    public ArrayList<RecipeType> getRecipeTypeList() {
        return mRecipeTypeList;
    }

    public ArrayList<Ingredient> getShoppingList() {
        ArrayList<Ingredient> ingrList = new ArrayList<>();
        for(Ingredient ingr:mShoppingList) {
            ingrList.add(ingr);
        }
        Collections.sort(ingrList);
        return ingrList;
    }

    public void addToShoppingList(ArrayList<Ingredient> ingrList) {
        for(Ingredient ingr:ingrList) {
            mShoppingList.add(ingr);
        }
    }

    public void updateShoppingList(ArrayList<Ingredient> ingrList) {
        mShoppingList.clear();
        for(Ingredient ingr:ingrList) {
            mShoppingList.add(ingr);
        }
    }

}
