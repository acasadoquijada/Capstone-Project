package com.example.podcasfy.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EpisodeDAO {

    @Insert
    void insert(Episode episode);

    @Query("SELECT * FROM Episode ORDER BY id DESC")
    LiveData<List<Episode>> getAll();

    @Query("SELECT * FROM Episode WHERE podcastId = :podcastId")
    List<Episode> get(String podcastId);

    @Delete
    void delete(Episode episode);
}