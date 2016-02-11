package com.erkutdemirhan.bugunneyesek.utils;

/**
 * Created by Erkut Demirhan on 11/02/16.
 */
public class StringUtils {

    public static String toNonTurkish(String original) {
        original = original.replace("Ç", "C");
        original = original.replace("Ş", "S");
        original = original.replace("Ğ", "G");
        original = original.replace("İ", "I");
        original = original.replace("Ö", "O");
        original = original.replace("Ü", "U");

        original = original.replace("ç", "c");
        original = original.replace("ş", "s");
        original = original.replace("ğ", "g");
        original = original.replace("ı", "i");
        original = original.replace("ö", "o");
        original = original.replace("ü", "u");

        return original;
    }
}
