package com.erkutdemirhan.bugunneyesek.adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.activity.RecipeActivity;
import com.erkutdemirhan.bugunneyesek.domain.Ingredient;
import com.erkutdemirhan.bugunneyesek.domain.Recipe;
import com.erkutdemirhan.bugunneyesek.application.BugunNeYesek;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Erkut on 01/02/16.
 */
public class RecipeListViewAdapter extends RecyclerView.Adapter<RecipeListViewAdapter.ViewHolder> {

    private ArrayList<Recipe>     mRecipeList;
    private ArrayList<Ingredient> mSelectedIngredientList;

    public RecipeListViewAdapter(ArrayList<Recipe> recipeList, ArrayList<Ingredient> ingredientList) {
        mRecipeList             = recipeList;
        mSelectedIngredientList = ingredientList;
    }

    public ArrayList<Recipe> getRecipeList() {
        return mRecipeList;
    }

    public ArrayList<Ingredient> getSelectedIngredientList() {
        return mSelectedIngredientList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v        = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        holder.mRecipeTitleView.setText(recipe.getRecipeName());
        try {
            InputStream ims = BugunNeYesek.getInstance().getAssets().open("images/"+recipe.getImageFileName());
            Drawable d      = Drawable.createFromStream(ims, null);
            holder.mRecipeImageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView  mRecipeTitleView;
        public ImageView mRecipeImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mRecipeTitleView     = (TextView)  itemView.findViewById(R.id.recipeitem_name);
            mRecipeImageView     = (ImageView) itemView.findViewById(R.id.recipeitem_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Recipe recipe = mRecipeList.get(getPosition());
            Intent intent = new Intent(v.getContext(), RecipeActivity.class);
            intent.putExtra(RecipeActivity.RECIPE_KEY, recipe);
            if(mSelectedIngredientList != null) {
                intent.putExtra(RecipeActivity.INGR_LIST_KEY, mSelectedIngredientList);
            }
            v.getContext().startActivity(intent);
        }
    }
}
