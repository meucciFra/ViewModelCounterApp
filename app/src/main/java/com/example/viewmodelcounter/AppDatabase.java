package com.example.viewmodelcounter;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Score.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    // Database name to be used and INSTANCE
    private static final int NUMBER_OF_THREADS = 4;
    public static final String DATABASE_NAME = "AppDataBase";
    private static volatile AppDatabase INSTANCE;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getInstance(final Context context){
        if (INSTANCE == null) {
            synchronized (AppDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).
                            fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

    // Abstract method to get the DAO
    public abstract ScoreDao getScoreDao();

}
