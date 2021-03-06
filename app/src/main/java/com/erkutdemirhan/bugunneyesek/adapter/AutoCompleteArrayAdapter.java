package com.erkutdemirhan.bugunneyesek.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.domain.Ingredient;
import com.erkutdemirhan.bugunneyesek.utils.StringUtils;

import java.util.ArrayList;

/**
 * Created by Erkut on 31/01/16.
 */
public class AutoCompleteArrayAdapter extends ArrayAdapter<Ingredient> implements Filterable {

    private ArrayList<Ingredient> mIngredients;
    private ArrayList<Ingredient> mFilteredIngredients;
    private Context               mContext;
    private ItemFilter            mFilter = new ItemFilter();
    private final int             mResource;

    public AutoCompleteArrayAdapter(Context context, final int resource, ArrayList<Ingredient> ingredients) {
        super(context, resource);
        mIngredients         = ingredients;
        mFilteredIngredients = new ArrayList<>();
        mContext             = context;
        mFilter              = new ItemFilter();
        mResource            = resource;
    }

    @Override
    public int getCount() {
        return mFilteredIngredients.size();
    }

    @Override
    public Ingredient getItem(int position) {
        return mFilteredIngredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView             = inflater.inflate(mResource, null);
        }
        final TextView textView     = (TextView) convertView.findViewById(R.id.ingredient_select_text);
        textView.setText(mFilteredIngredients.get(position).getIngredientName());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Ingredient> filteredList = new ArrayList<>();
            FilterResults results              = new FilterResults();
            if(constraint != null) {
                String filterString            = StringUtils.toNonTurkish(constraint.toString()).toLowerCase();
                String filterableString;
                for(Ingredient ingr:mIngredients) {
                    filterableString = StringUtils.toNonTurkish(ingr.getIngredientName()).toLowerCase();
                    if(filterableString.contains(filterString)) {
                        filteredList.add(ingr);
                    }
                }
            }
            results.values = filteredList;
            results.count  = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mFilteredIngredients = (ArrayList<Ingredient>) results.values;
            notifyDataSetChanged();
        }
    }

}
