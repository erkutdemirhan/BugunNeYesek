package com.erkutdemirhan.bugunneyesek.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.erkutdemirhan.bugunneyesek.domain.Ingredient;
import com.erkutdemirhan.bugunneyesek.domain.Recipe;
import com.erkutdemirhan.bugunneyesek.domain.RecipeType;
import com.erkutdemirhan.bugunneyesek.main.BugunNeYesek;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Erkut on 25/01/16.
 */
public class DbHelper extends SQLiteAssetHelper implements DbHelperInterface {
    private static final String TAG = "DbHelper";
    private static final String DATABASE_NAME    = "recipe_database.db";
    private static final int    DATABASE_VERSION = 1;

    private static DbHelper sInstance;

    public static DbHelper getsInstance(Context context) {
        if(sInstance == null) {
            sInstance = new DbHelper(context);
        }
        return sInstance;
    }

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public ArrayList<Recipe> getAllRecipes() {
        SQLiteDatabase db     = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect    = {"id", "name", "type", "instructions", "image_name"};
        qb.setTables("recipes");
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
        return getRecipes(c);
    }

    @Override
    public ArrayList<Recipe> getRecipesOfGivenType(int recipeType) {
        SQLiteDatabase db     = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect    = {"id", "name", "type", "instructions", "image_name"};
        qb.setTables("recipes");
        Cursor c = qb.query(db, sqlSelect, "type=?", new String[]{String.valueOf(recipeType)}, null, null, null);
        return getRecipes(c);
    }

    @Override
    public ArrayList<Recipe> getAllRecipesFromIngrList(ArrayList<Ingredient> ingrList) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT recipes.* ");
        sb.append("FROM recipes INNER JOIN recipeingredients INNER JOIN ingredients ");
        sb.append("ON recipes.id=recipeingredients.recipe_id AND recipeingredients.ingr_id=ingredients.id ");
        sb.append("WHERE ingredients.id " + prepareInStatement(ingrList.size()) + " ");
        sb.append("GROUP BY recipes.name ORDER BY COUNT(recipes.id) DESC");
        String[] args     = new String[ingrList.size()];
        for(int i=0; i<ingrList.size(); i++) {
            args[i] = String.valueOf(ingrList.get(i).getIngredientId());
        }
        SQLiteDatabase db = getReadableDatabase();
        Cursor c          = db.rawQuery(sb.toString(), args);
        return getRecipes(c);
    }

    @Override
    public ArrayList<Recipe> getRecipesFromIngrListGivenType(ArrayList<Ingredient> ingrList, int recipeType) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT recipes.* ");
        sb.append("FROM recipes INNER JOIN recipeingredients INNER JOIN ingredients ");
        sb.append("ON recipes.id=recipeingredients.recipe_id AND recipeingredients.ingr_id=ingredients.id ");
        sb.append("WHERE ingredients.id " + prepareInStatement(ingrList.size()) + " AND recipes.type=? ");
        sb.append("GROUP BY recipes.name ORDER BY COUNT(recipes.id) DESC");
        String[] args       = new String[ingrList.size()+1];
        for(int i=0; i<ingrList.size(); i++) {
            args[i] = String.valueOf(ingrList.get(i).getIngredientId());
        }
        args[args.length-1] = String.valueOf(recipeType);
        SQLiteDatabase db   = getReadableDatabase();
        Cursor c            = db.rawQuery(sb.toString(), args);
        return getRecipes(c);
    }

    @Override
    public ArrayList<Ingredient> getAllIngredients() {
        SQLiteDatabase db     = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect    = {"id", "name"};
        qb.setTables("ingredients");
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
        ArrayList<Ingredient> ingrList = new ArrayList<>();
        while(c.moveToNext()) {
            int id          = c.getInt(c.getColumnIndex("id"));
            String name     = c.getString(c.getColumnIndex("name"));
            String amount   = "";
            ingrList.add(new Ingredient(id, name, amount));
        }
        c.close();
        Collections.sort(ingrList);
        return ingrList;
    }

    @Override
    public ArrayList<Ingredient> getIngredientsForRecipeType(int recipeType) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ingredients.id, ingredients.name ");
        sb.append("FROM recipes INNER JOIN recipeingredients INNER JOIN ingredients ");
        sb.append("ON recipes.id=recipeingredients.recipe_id AND recipeingredients.ingr_id=ingredients.id ");
        sb.append("WHERE recipes.type=? ");
        sb.append("GROUP BY ingredients.id");
        ArrayList<Ingredient> ingrList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c          = db.rawQuery(sb.toString(), new String[]{String.valueOf(recipeType)});
        while(c.moveToNext()) {
            int id        = c.getInt(c.getColumnIndex("id"));
            String name   = c.getString(c.getColumnIndex("name"));
            String amount = "";
            ingrList.add(new Ingredient(id, name, amount));
        }
        c.close();
        Collections.sort(ingrList);
        return ingrList;
    }

    @Override
    public ArrayList<RecipeType> getAllRecipeTypes() {
        SQLiteDatabase db     = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect    = {"id", "name"};
        qb.setTables("recipe_types");
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
        ArrayList<RecipeType> recipeTypeList = new ArrayList<>();
        while(c.moveToNext()) {
            int id      = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            recipeTypeList.add(new RecipeType(id, name));
        }
        c.close();
        return recipeTypeList;
    }

    private ArrayList<Recipe> getRecipes(Cursor c) {
        ArrayList<Recipe> recipeList = new ArrayList<>();
        while(c.moveToNext()) {
            int id                = c.getInt(c.getColumnIndex("id"));
            String name           = c.getString(c.getColumnIndex("name"));
            int type              = c.getInt(c.getColumnIndex("type"));
            String instructions   = c.getString(c.getColumnIndex("instructions"));
            String imageName      = c.getString(c.getColumnIndex("image_name"));
            ArrayList<Ingredient> ingredientList = getRecipeIngredients(id);
            recipeList.add(new Recipe(id, name, instructions, imageName, type, ingredientList));
        }
        c.close();
        return recipeList;
    }

    private ArrayList<Ingredient> getRecipeIngredients(int recipeId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ingredients.id, ingredients.name, recipeingredients.amount ");
        sb.append("FROM recipeingredients INNER JOIN ingredients ");
        sb.append("ON recipeingredients.ingr_id=ingredients.id ");
        sb.append("WHERE recipeingredients.recipe_id=?");
        ArrayList<Ingredient> ingrList = new ArrayList<>();
        SQLiteDatabase db     = getReadableDatabase();
        Cursor c = db.rawQuery(sb.toString(), new String[]{String.valueOf(recipeId)});
        while(c.moveToNext()) {
            int id        = c.getInt(c.getColumnIndex("id"));
            String name   = c.getString(c.getColumnIndex("name"));
            String amount = c.getString(c.getColumnIndex("amount"));
            ingrList.add(new Ingredient(id, name, amount));
        }
        c.close();
        return ingrList;
    }

    private String prepareInStatement(int numArgs) {
        StringBuilder sb = new StringBuilder();
        sb.append("IN (");
        for(int i=0; i<numArgs; i++) {
            sb.append("?");
            if(i<numArgs-1) sb.append(", ");
        }
        sb.append(")");
        return sb.toString();
    }
}
