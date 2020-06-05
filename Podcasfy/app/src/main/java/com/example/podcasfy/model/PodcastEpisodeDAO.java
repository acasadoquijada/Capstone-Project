package com.example.podcasfy.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PodcastEpisodeDAO {

    @Insert
    void insertEpisode(PodcastEpisode podcastEpisode);

    @Query("SELECT * FROM podcast_episode WHERE podcastId = :podcastId")
    List<PodcastEpisode> getEpisodes(String podcastId);

    @Delete
    void deleteEpisode(PodcastEpisode podcastEpisode);
}