package com.makhabatusen.noteapp;

import android.app.Application;

import androidx.room.Room;

import com.makhabatusen.noteapp.room.AppDataBase;

public class App extends Application {
    public static AppDataBase dataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        dataBase = Room
                .databaseBuilder(this, AppDataBase.class, "database")
                .allowMainThreadQueries()
                .build();

    }

}
