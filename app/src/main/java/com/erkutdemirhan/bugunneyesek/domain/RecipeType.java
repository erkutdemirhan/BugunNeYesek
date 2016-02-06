package com.erkutdemirhan.bugunneyesek.domain;

import java.io.Serializable;

/**
 * Created by Erkut on 26/01/16.
 */
public class RecipeType implements Serializable {

    private final int    mTypeId;
    private final String mTypeName;

    public RecipeType(int id, String name) {
        mTypeId   = id;
        mTypeName = name;
    }

    public int getTypeId() {
        return mTypeId;
    }

    public String getTypeName() {
        return mTypeName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof RecipeType)) return false;
        return ((RecipeType) obj).getTypeId() == this.getTypeId();
    }

    @Override
    public int hashCode() {
        int result = 19;
        result = result * 31 + getTypeId();
        return result;
    }

    @Override
    public String toString() {
        return getTypeName();
    }
}
