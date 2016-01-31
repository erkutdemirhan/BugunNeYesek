package com.erkutdemirhan.bugunneyesek.recipes;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.database.DbHelperFactory;
import com.erkutdemirhan.bugunneyesek.domain.Ingredient;
import com.erkutdemirhan.bugunneyesek.domain.Recipe;
import com.erkutdemirhan.bugunneyesek.main.BugunNeYesek;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Erkut Demirhan on 17.05.2015.
 * Adapter class for the list of recipes
 */
public class RecipesViewAdapter extends RecyclerView.Adapter<RecipesViewAdapter.ViewHolder> {

    private String mAvailableIngr  = "Mevcut malzemeler";
    private String mUnavailableIng = "Mevcut olmayan malzemeler";

    private ArrayList<Recipe> mRecipeList;
    private Context           mContext;

    public RecipesViewAdapter(Context context) {
        mContext    = context;
        mRecipeList = new ArrayList<>();
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
//        ArrayList<Ingredient> userIngr = BugunNeYesek.getInstance().getUserIngredientList();
        holder.mRecipeTitleView    .setText(recipe.getRecipeName());
//        StringBuilder availableIngr   = new StringBuilder();
//        StringBuilder unavailableIngr = new StringBuilder();
//        for(Ingredient ingr:recipe.getIngredientList()) {
//            if(userIngr.contains(ingr)) {
//                availableIngr.append(ingr.getIngredientName());
//                availableIngr.append(", ");
//            } else {
//                unavailableIngr.append(ingr.getIngredientName());
//                unavailableIngr.append(", ");
//            }
//        }
//        holder.mAvailableIngrView  .setText(availableIngr.toString());
//        holder.mUnavailableIngrView.setText(unavailableIngr.toString());
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

    public void updateRecipes() {
        ArrayList<Ingredient> userIngrList = BugunNeYesek.getInstance().getUserIngredientList();
        int currentRecipeType              = BugunNeYesek.getInstance().getCurrentRecipeType();
        if(currentRecipeType == -1) {
            mRecipeList = DbHelperFactory.getDatabaseHelper(mContext).getAllRecipesFromIngrList(userIngrList);
        } else {
            mRecipeList = DbHelperFactory.getDatabaseHelper(mContext).getRecipesFromIngrListGivenType(userIngrList, currentRecipeType);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView  mRecipeTitleView;
        public TextView  mAvailableIngrView;
        public TextView  mUnavailableIngrView;
        public ImageView mRecipeImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mRecipeTitleView     = (TextView)  itemView.findViewById(R.id.recipeitem_name);
            //mAvailableIngrView   = (TextView)  itemView.findViewById(R.id.recipeitem_availableingr);
            //mUnavailableIngrView = (TextView)  itemView.findViewById(R.id.recipeitem_unavailableingr);
            mRecipeImageView     = (ImageView) itemView.findViewById(R.id.recipeitem_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Recipe recipe = mRecipeList.get(getPosition());
            Intent intent = new Intent(v.getContext(), RecipeActivity.class);
            intent.putExtra(Recipe.KEY, recipe);
            v.getContext().startActivity(intent);
        }
    }
}
