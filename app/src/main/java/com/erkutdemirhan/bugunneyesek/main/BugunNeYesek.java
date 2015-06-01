package com.erkutdemirhan.bugunneyesek.main;

import android.app.Application;

import com.erkutdemirhan.bugunneyesek.domain.Ingredient;
import com.erkutdemirhan.bugunneyesek.domain.Recipe;
import com.erkutdemirhan.bugunneyesek.domain.RecipeType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Erkut Demirhan on 21.05.2015.
 * Application class to hold global variables used throughout the application.
 */
public class BugunNeYesek extends Application {

    private HashMap<RecipeType, ArrayList<Recipe>> mRecipeListMap;
    private HashMap<RecipeType, ArrayList<Ingredient>> mIngredientListMap;

    @Override
    public void onCreate() {
        super.onCreate();
        mRecipeListMap = new HashMap<>();
        mIngredientListMap = new HashMap<>();
    }

    public HashMap<RecipeType, ArrayList<Recipe>> getRecipeListMap() {
        return this.mRecipeListMap;
    }

    public HashMap<RecipeType, ArrayList<Ingredient>> getIngredientListMap() {
        return this.mIngredientListMap;
    }

    /**
     * Initializes the recipe and the ingredient maps, using a recipe list as the input.
     * @param list
     */
    public void initializeMaps(ArrayList<Recipe> list) {
        for(Recipe recipe:list) {
            if(mRecipeListMap.containsKey(recipe.getRecipeType())) {
                mRecipeListMap.get(recipe.getRecipeType()).add(recipe);
            } else {
                ArrayList<Recipe> recipeList = new ArrayList<>();
                recipeList.add(recipe);
                mRecipeListMap.put(recipe.getRecipeType(), recipeList);
            }
        }
        for(RecipeType recipeType:mRecipeListMap.keySet()) {
            initializeIngredientListMap(recipeType, mRecipeListMap.get(recipeType));
        }
    }

    /**
     * Initializes the hashmap of ingredient lists by using the all of the recipe types as the keyset.
     *
     * @param recipeType
     * @param list
     */
    private void initializeIngredientListMap(RecipeType recipeType, ArrayList<Recipe> list) {
        HashSet<Ingredient> ingrSet = new HashSet<>();
        ArrayList<Ingredient> ingrList = new ArrayList<>();
        for(Recipe recipe:list) {
            ingrSet.addAll(recipe.getIngredientList());
        }
        ingrList.addAll(ingrSet);
        Collections.sort(ingrList);
        mIngredientListMap.put(recipeType, ingrList);
    }

}
