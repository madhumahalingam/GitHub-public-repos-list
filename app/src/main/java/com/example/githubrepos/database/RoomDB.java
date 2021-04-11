package com.example.githubrepos.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Comment.class, exportSchema = false,version =  1)
public abstract class RoomDB extends RoomDatabase {
    private static final String DB_NAME = "repo_db";
    private static RoomDB dbInstance;

    public static synchronized RoomDB getInstance(Context context){
        if(dbInstance == null){
            dbInstance = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DB_NAME).fallbackToDestructiveMigration().build();
        }
        return dbInstance;

    }

    public abstract CommentsDao commentsDao();
}
