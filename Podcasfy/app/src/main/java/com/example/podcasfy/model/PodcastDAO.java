package com.example.podcasfy.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PodcastDAO {

    @Insert()
    void insertPodcast(Podcast podcast);

    @Query("SELECT * FROM podcast")
    LiveData<List<Podcast>> getPodcasts();

    @Query("SELECT * FROM podcast WHERE provider = :provider")
    List<Podcast> getPodcastsByProvider(String provider);

    @Query("SELECT * FROM podcast WHERE name = :name")
    List<Podcast> getPodcastsByName(String name);

    @Delete
    void delete(Podcast podcast);

    @Query("DELETE FROM podcast WHERE id = :id")
    void delete(String id);

    @Query("DELETE FROM podcast")
    void deleteTable();
}
