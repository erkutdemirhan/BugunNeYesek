package com.erkutdemirhan.bugunneyesek.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.erkutdemirhan.bugunneyesek.database.DbHelperFactory;
import com.erkutdemirhan.bugunneyesek.domain.Ingredient;
import com.erkutdemirhan.bugunneyesek.domain.RecipeType;
import java.util.ArrayList;

/**
 * Created by Erkut Demirhan on 21.05.2015.
 * Application class to hold global variables used throughout the application.
 */
public class BugunNeYesek extends Application {

    private static final String RECIPE_TYPE_PREF_KEY = "current_recipe_type";
    private static BugunNeYesek sInstance;

    private ArrayList<RecipeType> mRecipeTypeList;
    private ArrayList<Ingredient> mUserIngredientList;
    private SharedPreferences     mRecipeTypePref;

    public static BugunNeYesek getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mRecipeTypeList     = DbHelperFactory.getDatabaseHelper(getApplicationContext()).getAllRecipeTypes();
        mRecipeTypePref     = getSharedPreferences(RECIPE_TYPE_PREF_KEY, Context.MODE_PRIVATE);
        mUserIngredientList = new ArrayList<>();
    }

    public int getCurrentRecipeType() {
        return mRecipeTypePref.getInt(RECIPE_TYPE_PREF_KEY, -1);
    }

    public void setCurrentRecipeType(int type) {
        SharedPreferences.Editor editor = mRecipeTypePref.edit();
        editor.putInt(RECIPE_TYPE_PREF_KEY, type);
        editor.commit();
    }

    public ArrayList<RecipeType> getRecipeTypeList() {
        return mRecipeTypeList;
    }

    public RecipeType getRecipeTypeById(int id) {
        for(RecipeType recipeType:mRecipeTypeList) {
            if(recipeType.getTypeId()==id) {
                return recipeType;
            }
        }
        return null;
    }

    public ArrayList<Ingredient> getUserIngredientList() {
        return mUserIngredientList;
    }

    public Ingredient getUserIngredient(int index) {
        if(index >= 0 && index < mUserIngredientList.size()) {
            return mUserIngredientList.get(index);
        }
        return null;
    }

    public void addToUserIngredientList(Ingredient ingr) {
        mUserIngredientList.add(ingr);
    }

    public boolean removeFromUserIngredientList(int index) {
        if(index >= 0 && index < mUserIngredientList.size()) {
            mUserIngredientList.remove(index);
            return true;
        }
        return false;
    }

    public void clearUserIngredientList() {
        mUserIngredientList.clear();
    }
}
