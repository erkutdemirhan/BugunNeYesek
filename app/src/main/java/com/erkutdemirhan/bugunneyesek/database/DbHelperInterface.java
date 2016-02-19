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
    public ArrayList<Recipe> getAllFavoriteRecipes();
    public ArrayList<Recipe> getRecipesOfGivenType(int recipeType);
    public ArrayList<Recipe> getAllRecipesFromIngrList(ArrayList<Ingredient> ingrList);
    public ArrayList<Ingredient> getAllIngredients();
    public ArrayList<RecipeType> getAllRecipeTypes();
    public void updateRecipeFavorite(Recipe recipe);
}
