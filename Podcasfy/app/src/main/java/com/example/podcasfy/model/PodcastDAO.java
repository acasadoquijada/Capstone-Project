package com.example.podcasfy.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PodcastDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPodcast(Podcast podcast);

    @Query("SELECT * FROM podcast")
    List<Podcast> getPodcasts();

    @Query("SELECT * FROM podcast WHERE provider = :provider")
    List<Podcast> getPodcastsByProvider(String provider);

    @Query("SELECT * FROM podcast WHERE name = :name")
    List<Podcast> getPodcastsByName(String name);

    @Delete
    void delete(Podcast podcast);

    @Query("DELETE FROM podcast")
    void deleteTable();
}
