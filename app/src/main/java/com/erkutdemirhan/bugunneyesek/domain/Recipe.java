package com.erkutdemirhan.bugunneyesek.domain;

import java.util.ArrayList;

/**
 * Created by Erkut Demirhan on 19.05.2015.
 * Class to represent cooking recipes
 */
public class Recipe {

    private final int                   mRecipeId;
    private final String                mRecipeName;
    private final String                mInstructions;
    private final String                mImageFileName;
    private final RecipeType            mRecipeType;
    private final ArrayList<Ingredient> mIngredientList;

    public Recipe(int id,
                  String name,
                  String instructions,
                  String imageFileName,
                  RecipeType type,
                  ArrayList<Ingredient> ingredientList) {
        mRecipeId       = id;
        mRecipeName     = name;
        mInstructions   = instructions;
        mRecipeType     = type;
        mImageFileName  = imageFileName;
        mIngredientList = ingredientList;
    }

    public int getRecipeId() {
        return mRecipeId;
    }

    public String getRecipeName() {
        return mRecipeName;
    }

    public String getInstructions() {
        return mInstructions;
    }

    public String getImageFileName() {
        return mImageFileName;
    }

    public RecipeType getRecipeType() {
        return mRecipeType;
    }

    public ArrayList<Ingredient> getIngredientList() {
        return this.mIngredientList;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof Recipe)) return false;
        return ((Recipe) obj).getRecipeId() == this.getRecipeId();
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + getRecipeId();
        return result;
    }
}
