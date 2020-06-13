package com.example.podcasfy.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Podcast.class, Episode.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "podcastfy_database";
    private static AppDataBase sInstance;

    public static AppDataBase getInstance(Context context){

        if(sInstance == null){
            sInstance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDataBase.class,
                    AppDataBase.DATABASE_NAME)
                    .build();
        }

        return sInstance;
    }

    public abstract PodcastDAO podcastDAO();
    public abstract EpisodeDAO podcastEpisodeDAO();

}
