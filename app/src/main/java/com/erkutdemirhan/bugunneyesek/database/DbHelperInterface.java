package com.erkutdemirhan.bugunneyesek.database;

import com.erkutdemirhan.bugunneyesek.domain.Ingredient;
import com.erkutdemirhan.bugunneyesek.domain.Recipe;
import com.erkutdemirhan.bugunneyesek.domain.RecipeType;

import java.util.ArrayList;

/**
 * Created by Erkut on 25/01/16.
 *
 * Provides specific methods for getting recipe and ingredient data
 * from the application's database.
 */
public interface DbHelperInterface {

    public ArrayList<Recipe> getAllRecipes();
    public ArrayList<Recipe> getRecipesOfGivenType(int recipeType);
    public ArrayList<Recipe> getAllRecipesFromIngrList(ArrayList<Ingredient> ingrList);
    public ArrayList<Recipe> getRecipesFromIngrListGivenType(ArrayList<Ingredient> ingrList, int recipeType);
    public ArrayList<Ingredient> getAllIngredients();
    public ArrayList<Ingredient> getIngredientsForRecipeType(int recipeType);
    public ArrayList<RecipeType> getAllRecipeTypes();
}
