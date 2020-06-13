package com.example.podcasfy.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EpisodeDAO {

    @Insert
    void insertEpisode(Episode episode);

    @Query("SELECT * FROM Episode WHERE podcastId = :podcastId")
    List<Episode> getEpisodes(String podcastId);

    @Delete
    void deleteEpisode(Episode episode);
}