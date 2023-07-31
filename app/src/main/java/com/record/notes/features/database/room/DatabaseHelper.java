package com.record.notes.features.database.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = User.class, exportSchema = false, version = 1)
public abstract class DatabaseHelper extends RoomDatabase {

    private static final String DB_NAME = "BookDatabase";
    private static DatabaseHelper instance;

    // this methods are FIFO data
    public static synchronized DatabaseHelper getInstance(Context context) {
        // check the instance is null object
        if (instance == null) {
            instance = Room.databaseBuilder(context, DatabaseHelper.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    // create the abstract methods;
    public abstract UserDao userDao();
}
