package com.erkutdemirhan.bugunneyesek.ingredients;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erkutdemirhan.bugunneyesek.R;

import java.util.ArrayList;

/**
 * Created by Erkut Demirhan on 13.05.2015.
 */
public class IngredientsViewAdapter extends RecyclerView.Adapter<IngredientsViewAdapter.ViewHolder> {

    private ArrayList<String> mList;
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

    public IngredientsViewAdapter(RecyclerView recyclerView, ArrayList<String> ingredientsList) {
        mRecyclerView = recyclerView;
        mList = ingredientsList;
    }

    @Override
    public IngredientsViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(IngredientsViewAdapter.ViewHolder holder, int position) {
        holder.setTextView(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void removeAt(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(String item) {
        mList.add(item);
        notifyItemInserted(mList.size()-1);
    }

    public boolean isItemAddedBefore(String item) {
        for(String ingredient:mList) {
            if(ingredient.equalsIgnoreCase(item)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getIngredientsList() {
        return mList;
    }
}
