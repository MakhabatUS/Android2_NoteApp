package com.makhabatusen.noteapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private SharedPreferences preferences;
    private final String SHOWN_KEY = "isShown";

    public Preferences(Context context) {
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }
   public void saveShowStatus() {
        preferences.edit().putBoolean(SHOWN_KEY, true).apply();
   }

   public boolean isShown () {
        return preferences.getBoolean(SHOWN_KEY, false);
   }



}
