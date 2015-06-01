package com.erkutdemirhan.bugunneyesek.domain;

/**
 * Created by Erkut Demirhan on 19.05.2015.
 * Class to represent an ingredient
 */
public class Ingredient implements Comparable<Ingredient> {

    private final String mName;

    public Ingredient(String name) {
        mName = name;
    }

    public String getName() {
        return this.mName;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Ingredient) && (this.getName().equalsIgnoreCase(((Ingredient) obj).getName()));
    }

    @Override
    public int hashCode() {
        return this.getName().toLowerCase().hashCode();
    }

    @Override
    public int compareTo(Ingredient another) {
        return this.getName().compareTo(another.getName());
    }
}
