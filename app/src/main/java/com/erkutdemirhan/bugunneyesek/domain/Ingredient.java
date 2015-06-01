package com.erkutdemirhan.bugunneyesek.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Erkut Demirhan on 19.05.2015.
 * Class to represent an ingredient
 */
public class Ingredient implements Comparable<Ingredient>, Parcelable {

    private static final Creator<Ingredient> CREATOR
            = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    private final String mName;

    public Ingredient(String name) {
        mName = name;
    }

    @Override
    public String toString() {
        return mName;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Ingredient) && (mName.equalsIgnoreCase(((Ingredient) obj).mName));
    }

    @Override
    public int hashCode() {
        return mName.toLowerCase().hashCode();
    }

    @Override
    public int compareTo(Ingredient another) {
        return mName.compareTo(another.mName);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
    }

    private Ingredient(Parcel in) {
        mName = in.readString();
    }
}
