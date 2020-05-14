package com.aiczone.bookapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.aiczone.bookapp.model.Book;

@Database(entities = {Book.class}, version = 2,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookDao bookDao();

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
                "books.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

}
