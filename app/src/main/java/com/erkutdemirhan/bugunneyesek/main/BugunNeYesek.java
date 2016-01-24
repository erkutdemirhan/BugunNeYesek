package com.erkutdemirhan.bugunneyesek.main;

import android.app.Application;
import com.erkutdemirhan.bugunneyesek.database.DbHelperFactory;
import com.erkutdemirhan.bugunneyesek.domain.Ingredient;
import com.erkutdemirhan.bugunneyesek.domain.RecipeType;
import java.util.ArrayList;

/**
 * Created by Erkut Demirhan on 21.05.2015.
 * Application class to hold global variables used throughout the application.
 */
public class BugunNeYesek extends Application {

    private static BugunNeYesek sInstance;

    private ArrayList<RecipeType> mRecipeTypeList;
    private ArrayList<Ingredient> mUserIngredientList;

    public static BugunNeYesek getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mRecipeTypeList     = DbHelperFactory.getDatabaseHelper(getApplicationContext()).getAllRecipeTypes();
        mUserIngredientList = new ArrayList<>();
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
