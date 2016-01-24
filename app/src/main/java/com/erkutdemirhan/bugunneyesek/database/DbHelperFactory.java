package com.erkutdemirhan.bugunneyesek.database;

import android.content.Context;

/**
 * Used for creating instances of the database helper class being used.
 *
 * Created by Erkut on 25/01/16.
 */
public class DbHelperFactory {

    private DbHelperFactory() {}

    public static DbHelperInterface getDatabaseHelper(Context context) {
        return new DbHelper(context);
    }
}
