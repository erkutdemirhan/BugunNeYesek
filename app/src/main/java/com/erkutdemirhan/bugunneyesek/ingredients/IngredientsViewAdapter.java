package com.erkutdemirhan.bugunneyesek.ingredients;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.domain.Ingredient;

import java.util.ArrayList;

/**
 * Created by Erkut Demirhan on 13.05.2015.
 * Adapter class to hold the ingredient view items
 */
public class IngredientsViewAdapter extends RecyclerView.Adapter<IngredientsViewAdapter.ViewHolder> {

    private ArrayList<Ingredient> mIngredientList;
    private RecyclerView mRecyclerView;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView mImageView;
        private View mView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            mTextView = (TextView) v.findViewById(R.id.ingredient_name);
            mImageView = (ImageView) v.findViewById(R.id.remove_ingredient_icon);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = mRecyclerView.getChildPosition(mView);
                    removeAt(position);
                }
            });
        }

        public void setTextView(String text) {
            mTextView.setText(text);
        }
    }

    public IngredientsViewAdapter(RecyclerView recyclerView, ArrayList<Ingredient> ingredientList) {
        mRecyclerView = recyclerView;
        mIngredientList = ingredientList;
    }

    @Override
    public IngredientsViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(IngredientsViewAdapter.ViewHolder holder, int position) {
        holder.setTextView(mIngredientList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mIngredientList.size();
    }

    public void removeAt(int position) {
        mIngredientList.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(Ingredient ingredient) {
        mIngredientList.add(ingredient);
        notifyItemInserted(mIngredientList.size()-1);
    }

    public boolean isItemAddedBefore(Ingredient ingredient) {
        for(Ingredient element: mIngredientList) {
            if(element.equals(ingredient)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Ingredient> getIngredientsList() {
        return mIngredientList;
    }
}
