package com.erkutdemirhan.bugunneyesek.recipes;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.domain.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Erkut Demirhan on 17.05.2015.
 * Adapter class for the list of recipes
 */
public class RecipesViewAdapter extends RecyclerView.Adapter<RecipesViewAdapter.ViewHolder> {

    private String mAvailableIngr = "Mevcut malzemeler";
    private String mUnavailableIng = "Mevcut olmayan malzemeler";

    private ArrayList<Recipe> mRecipeList;

    public RecipesViewAdapter(ArrayList<Recipe> recipeList) {
        mRecipeList = recipeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipes_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mRecipeTitleView.setText(mRecipeList.get(position).getRecipeName());
        holder.mAvailableIngrView.setText(mAvailableIngr);
        holder.mUnavailableIngrView.setText(mUnavailableIng);
        Context context = holder.mRecipeImageView.getContext();
        Uri uri = Uri.parse(mRecipeList.get(position).getImageUrl());
        Picasso.with(context).load(uri)
                .error(R.drawable.recipe_image)
                .placeholder(R.drawable.recipe_image)
                .into(holder.mRecipeImageView);
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mRecipeTitleView;
        public TextView mAvailableIngrView;
        public TextView mUnavailableIngrView;
        public ImageView mRecipeImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mRecipeTitleView = (TextView) itemView.findViewById(R.id.recipe_title);
            mAvailableIngrView = (TextView) itemView.findViewById(R.id.available_ingredients);
            mUnavailableIngrView = (TextView) itemView.findViewById(R.id.unavailable_ingredients);
            mRecipeImageView = (ImageView) itemView.findViewById(R.id.recipe_picture);
        }
    }
}
