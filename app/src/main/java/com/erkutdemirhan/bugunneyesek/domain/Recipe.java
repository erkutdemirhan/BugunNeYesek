package com.erkutdemirhan.bugunneyesek.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Erkut Demirhan on 19.05.2015.
 * Class to represent cooking recipes
 */
public class Recipe {

    private final String mRecipeName;
    private final String mRecipeContent;
    private final String mImageUrl;
    private final RecipeType mRecipeType;
    private final ArrayList<Ingredient> mIngredientList;

    public Recipe(String name, String content, String imageUrl, RecipeType type, ArrayList<Ingredient> ingredientList) {
        mRecipeName = name;
        mRecipeContent = content;
        mRecipeType = type;
        mImageUrl = imageUrl;
        mIngredientList = ingredientList;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Recipe) &&
                (this.getRecipeName().equalsIgnoreCase(((Recipe) obj).getRecipeName())) &&
                (this.getRecipeType() == ((Recipe) obj).getRecipeType());
    }

    public String getRecipeName() {
        return this.mRecipeName;
    }

    public String getRecipeContent() {
        return this.mRecipeContent;
    }

    public String getImageUrl() {
        return this.mImageUrl;
    }

    public RecipeType getRecipeType() {
        return this.mRecipeType;
    }

    public ArrayList<Ingredient> getIngredientList() {
        return this.mIngredientList;
    }
}
