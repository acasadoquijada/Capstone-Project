package com.example.podcasfy.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.FtsOptions.Order.DESC;

@Dao
public interface PodcastEpisodeDAO {

    @Insert
    public void insertEpisode(PodcastEpisode podcastEpisode);

    @Query("SELECT * FROM podcast_episode WHERE podcastId = :podcastId")
    public List<PodcastEpisode> getEpisodes(String podcastId);

    @Delete
    public void deleteEpisode(PodcastEpisode podcastEpisode);
}