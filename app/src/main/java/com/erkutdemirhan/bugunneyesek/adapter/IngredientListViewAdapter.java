package com.erkutdemirhan.bugunneyesek.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.domain.Ingredient;

import java.util.ArrayList;


/**
 * Created by Erkut on 01/02/16.
 */
public class IngredientListViewAdapter extends RecyclerView.Adapter<IngredientListViewAdapter.ViewHolder> {

    private ArrayList<Ingredient> mIngredientList;

    public IngredientListViewAdapter() {
        mIngredientList = new ArrayList<>();
    }

    public IngredientListViewAdapter(ArrayList<Ingredient> ingredientList) {
        mIngredientList = ingredientList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView  mTextView;
        private ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mTextView  = (TextView)  v.findViewById(R.id.ingredient_name);
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

    @Override
    public IngredientListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(IngredientListViewAdapter.ViewHolder holder, int position) {
        String text = mIngredientList.get(position).getIngredientName();
        holder.setTextView(text);
    }

    @Override
    public int getItemCount() {
        return mIngredientList.size();
    }

    /**
     * Adds given ingredient to the ingredient list, if it was not added before.
     * Returns true for successful addition, false otherwise.
     *
     * @param ingredient
     * @return
     */
    public boolean addIngredient(Ingredient ingredient) {
        for(Ingredient ingr:mIngredientList) {
            if(ingr.equals(ingredient)) {
                return false;
            }
        }
        mIngredientList.add(ingredient);
        notifyItemInserted(getItemCount()-1);
        return true;
    }

    public ArrayList<Ingredient> getIngredientList() {
        return mIngredientList;
    }

    public boolean isEmpty() {
        return mIngredientList.isEmpty();
    }

    private void removeIngredient(int position) {
        if(0<=position && getItemCount()>position) {
            mIngredientList.remove(position);
            notifyItemRemoved(position);
        }
    }
}
