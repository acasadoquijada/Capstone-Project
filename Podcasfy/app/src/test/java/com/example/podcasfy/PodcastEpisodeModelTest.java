package com.example.podcasfy;

import com.example.podcasfy.model.PodcastEpisode;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class PodcastEpisodeModelTest {

    private String url = "folder/episode.mp3";

    @Test public void PodcastEpisodeModelWorkAsExpected(){

        PodcastEpisode podcastEpisode = new PodcastEpisode();

        podcastEpisode.setPath(url);

        assertEquals(url,podcastEpisode.getPath());

    }
}
