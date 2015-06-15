package com.erkutdemirhan.bugunneyesek.ingredients;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.domain.Ingredient;
import com.erkutdemirhan.bugunneyesek.main.BugunNeYesek;

import java.util.ArrayList;

/**
 * Created by Erkut Demirhan on 13.05.2015.
 * Adapter class to hold the ingredient view items
 */
public class IngredientsViewAdapter extends RecyclerView.Adapter<IngredientsViewAdapter.ViewHolder> {

    private BugunNeYesek mBugunNeYesek;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.ingredient_name);
            mImageView = (ImageView) v.findViewById(R.id.remove_ingredient_icon);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getPosition();
                    removeIngredient(position);
                    Log.d("onClick", "remove ingredient at position: " + position);
                }
            });
        }

        public void setTextView(String text) {
            mTextView.setText(text);
        }
    }

    public IngredientsViewAdapter(Context context) {
        mBugunNeYesek = (BugunNeYesek) context.getApplicationContext();
    }

    @Override
    public IngredientsViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(IngredientsViewAdapter.ViewHolder holder, int position) {
        holder.setTextView(mBugunNeYesek.getUserIngredientList().get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mBugunNeYesek.getUserIngredientList().size();
    }

    /**
     * Removes the ingredient at given position from the user ingredient list.
     * @param position
     */
    public void removeIngredient(int position) {
        if(position >= 0 && position < getItemCount()) {
            mBugunNeYesek.getUserIngredientList().remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * Removes all ingredients from the user ingredient list.
     */
    public void removeAllIngredients() {
        mBugunNeYesek.getUserIngredientList().clear();
        notifyItemRangeChanged(0, getItemCount());
    }

    /**
     * Adds given ingredient to the user ingredient list, if it was not added before.
     * Returns true for successful addition, false otherwise.
     *
     * @param ingredient
     * @return
     */
    public boolean addIngredient(Ingredient ingredient) {
        for(Ingredient ingr:mBugunNeYesek.getUserIngredientList()) {
            if(ingr.equals(ingredient)) {
                return false;
            }
        }
        mBugunNeYesek.getUserIngredientList().add(ingredient);
        notifyItemInserted(getItemCount()-1);
        return true;
    }
}
