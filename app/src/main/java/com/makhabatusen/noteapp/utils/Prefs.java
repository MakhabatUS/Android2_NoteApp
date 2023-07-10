package com.makhabatusen.noteapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.makhabatusen.noteapp.App;
import com.makhabatusen.noteapp.MainActivity;

public class Prefs {
    private final SharedPreferences preferences;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void saveShowStatus(){
        preferences.edit().putBoolean("isShown", true).apply();
    }
    public boolean isShown() {
        return preferences.getBoolean("isShown", false);
    }
    public void clearPrefs(){
        preferences
                .edit()
                .clear()
                .apply();
    }



}
