package com.example.virat.shukla.androidproficiency.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {ResponseModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    private static final String DATABASE_NAME = "response.db";

    public abstract DAO getDao();

    public static synchronized AppDatabase getInMemoryDatabase(Context context) {
        if (instance == null) {
            instance =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
