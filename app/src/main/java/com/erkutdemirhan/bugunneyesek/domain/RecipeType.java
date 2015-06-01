package com.erkutdemirhan.bugunneyesek.domain;

/**
 * Created by Erkut Demirhan on 19.05.2015.
 * Class to represent the type of the recipe
 */
public enum RecipeType {
    DESSERT("Tatlı"),
    DRINK("İçecek"),
    MAIN_COURSE("Ana yemek"),
    SNACKS("Atıştırmalık");

    private String text;

    private RecipeType(String input) {
        this.text = input;
    }

    /**
     * Returns the textual representation of the recipe type.
     */
    public String getText() {
        return this.text;
    }

    /**
     * Compares the input string with string representations of the recipe types.
     * If there is a matching recipe type, returns it. Throws an IllegalArgumentException otherwise.
     */
    public static RecipeType stringToRecipeType(String input) {
        if(input != null) {
            for(RecipeType type:RecipeType.values()) {
                if(type.getText().equalsIgnoreCase(input)) {
                    return type;
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
        return null;
    }
}
