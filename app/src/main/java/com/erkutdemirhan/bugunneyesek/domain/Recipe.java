package com.erkutdemirhan.bugunneyesek.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Erkut Demirhan on 19.05.2015.
 * Class to represent cooking recipes
 */
public class Recipe implements Comparable<Recipe>, Serializable {

    public static final String KEY = "Recipe";

    private final int                   mRecipeId;
    private final int                   mRecipeTypeId;
    private final String                mRecipeName;
    private final String                mInstructions;
    private final String                mImageFileName;
    private final ArrayList<Ingredient> mIngredientList;
    private boolean                     mIsFavorite;

    public Recipe(int id,
                  String name,
                  String instructions,
                  String imageFileName,
                  int type,
                  ArrayList<Ingredient> ingredientList,
                  boolean isFavorite) {
        mRecipeId       = id;
        mRecipeName     = name;
        mInstructions   = instructions;
        mRecipeTypeId   = type;
        mImageFileName  = imageFileName;
        mIngredientList = ingredientList;
        mIsFavorite     = isFavorite;
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

    public int getRecipeTypeId() {
        return mRecipeTypeId;
    }

    public ArrayList<Ingredient> getIngredientList() {
        return this.mIngredientList;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean bool) {
        mIsFavorite = bool;
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

    @Override
    public int compareTo(Recipe another) {
        return getRecipeName().compareTo(another.getRecipeName());
    }
}
