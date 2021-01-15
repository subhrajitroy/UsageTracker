package com.subhrajit.roy.usagetracker;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Usage.class},version = 4)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract UsageDAO getDAO();

    private static AppDatabase db;

    private static Object Lock = new Object();

    public static AppDatabase instance(Context context){
        if(db == null){
            synchronized (Lock){
                db = Room.databaseBuilder(context, AppDatabase.class, "usage")
                        .fallbackToDestructiveMigration().build();
            }

        }
        return db;
    }

    @Override
    public void close() {
        super.close();
        if(db != null){
            db.close();
        }
    }
}
