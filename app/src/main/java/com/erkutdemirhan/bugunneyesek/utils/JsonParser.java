package com.erkutdemirhan.bugunneyesek.utils;

import android.util.Log;

import com.erkutdemirhan.bugunneyesek.domain.Ingredient;
import com.erkutdemirhan.bugunneyesek.domain.Recipe;
import com.erkutdemirhan.bugunneyesek.domain.RecipeType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Erkut Demirhan on 21.05.2015.
 * Parses the json file that includes the recipe list.
 */
public class JsonParser {

    private static final String RECIPE_LIST_KEY = "recipeList";
    private static final String RECIPE_TITLE_KEY = "title";
    private static final String RECIPE_TYPE_KEY = "type";
    private static final String INGREDIENTS_LIST_KEY = "ingredients";
    private static final String RECIPE_TEXT_KEY = "recipe";
    private static final String IMAGE_URL_KEY = "imageUrl";

    /**
     * Parses the json object that holds the recipe list, and returns it as a list of recipes.
     *
     * @param rootObject
     * @return
     */
    public static ArrayList<Recipe> jsonToRecipeList(JSONObject rootObject) {
        ArrayList<Recipe> recipeList = new ArrayList<>();
        if(rootObject != null) {
            try {
                JSONArray recipeJSONList = (JSONArray) rootObject.get(RECIPE_LIST_KEY);
                for (int i = 0; i < recipeJSONList.length(); i++) {
                    Recipe recipe = jsonToRecipe(recipeJSONList.getJSONObject(i));
                    recipeList.add(recipe);
                }
            } catch (JSONException e) {
                Log.e("Json parser error", "Error parsing the json object " + e.toString());
            }
        }
        return recipeList;
    }

    /**
     * Parses the json object that holds a recipe, and returns it as a recipe object.
     * @param recipeObject
     * @return
     */
    private static Recipe jsonToRecipe(JSONObject recipeObject) {
        Recipe recipe = null;

        try {
            String recipeTitle = (String) recipeObject.get(RECIPE_TITLE_KEY);
            String recipeTypeString = (String) recipeObject.get(RECIPE_TYPE_KEY);
            RecipeType recipeType = RecipeType.stringToRecipeType(recipeTypeString);
            ArrayList<Ingredient> ingredients = jsonToIngredientList((JSONArray) recipeObject.get(INGREDIENTS_LIST_KEY));
            String recipeText = (String) recipeObject.get(RECIPE_TEXT_KEY);
            String recipeImageUrl = (String) recipeObject.get(IMAGE_URL_KEY);
            recipe = new Recipe(recipeTitle, recipeText, recipeImageUrl, recipeType, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipe;
    }

    /**
     * Parses the json object that holds the ingredients list, and returns it as a list of ingredients.
     *
     * @param jsonIngredientsArray
     * @return
     */
    private static ArrayList<Ingredient> jsonToIngredientList(JSONArray jsonIngredientsArray) {
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        try {
            for(int i=0; i<jsonIngredientsArray.length(); i++) {
                String ingrString = jsonIngredientsArray.getString(i);
                Ingredient ingredient = new Ingredient(ingrString);
                ingredientList.add(ingredient);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ingredientList;
    }

}
