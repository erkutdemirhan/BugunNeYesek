package com.erkutdemirhan.bugunneyesek.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Erkut Demirhan on 19.05.2015.
 * Class to represent an ingredient
 */
public class Ingredient implements Comparable<Ingredient>, Serializable {

    public static final String KEY = "Ingredient";

    private final int    mIngredientId;
    private final String mIngredientName;
    private final String mIngredientAmount;

    public Ingredient(int id, String name, String  amount) {
        mIngredientId     = id;
        mIngredientName   = name;
        mIngredientAmount = amount;
    }

    public int getIngredientId() {
        return mIngredientId;
    }

    public String getIngredientName() {
        return mIngredientName;
    }

    public String getIngredientAmount() {
        return mIngredientAmount;
    }

    @Override
    public String toString() {
        return mIngredientName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof Ingredient)) return false;
        return ((Ingredient) obj).getIngredientId() == this.getIngredientId();
    }

    @Override
    public int hashCode() {
        int result = 23;
        result = result * 31 + getIngredientId();
        return result;
    }

    @Override
    public int compareTo(Ingredient another) {
        return getIngredientName().compareTo(another.getIngredientName());
    }
}
