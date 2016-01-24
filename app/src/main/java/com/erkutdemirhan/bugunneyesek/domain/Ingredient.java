package com.erkutdemirhan.bugunneyesek.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Erkut Demirhan on 19.05.2015.
 * Class to represent an ingredient
 */
public class Ingredient implements Comparable<Ingredient>, Parcelable {

    private static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {

        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

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
        return mIngredientName.compareTo(another.mIngredientName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{getIngredientName(), getIngredientAmount()});
        dest.writeInt(getIngredientId());
    }

    private Ingredient(Parcel in) {
        String[] args     = new String[2];
        mIngredientId     = in.readInt();
        in.readStringArray(args);
        mIngredientName   = args[0];
        mIngredientAmount = args[1];
    }
}
