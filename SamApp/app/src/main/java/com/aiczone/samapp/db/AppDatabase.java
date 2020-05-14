package com.aiczone.samapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.aiczone.samapp.model.Asset;

@Database(entities = {Asset.class}, version = 4,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AssetDao assetDao();

    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    AppDatabase() {
    }

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(context, AppDatabase.class,
                "samapp.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

}
