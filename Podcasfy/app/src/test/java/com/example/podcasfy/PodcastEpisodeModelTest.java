package com.example.podcasfy;

import com.example.podcasfy.model.PodcastEpisode;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class PodcastEpisodeModelTest {

    private String url = "folder/episode.mp3";
    private String name = "planos and tests";
    private String description = "description";
    private String imageURL = "image.jpg";
    private String mediaURL = "mediaURL";

    @Test public void PodcastEpisodeModelWorkAsExpected(){

        PodcastEpisode podcastEpisode = new PodcastEpisode();

        podcastEpisode.setPath(url);
        podcastEpisode.setName(name);
        podcastEpisode.setDescription(description);
        podcastEpisode.setImageURL(imageURL);
        podcastEpisode.setMediaURL(mediaURL);

        assertEquals(url,podcastEpisode.getPath());
        assertEquals(name,podcastEpisode.getName());
        assertEquals(description,podcastEpisode.getDescription());
        assertEquals(mediaURL,podcastEpisode.getMediaURL());
        assertEquals(imageURL,podcastEpisode.getImageURL());

    }
}
