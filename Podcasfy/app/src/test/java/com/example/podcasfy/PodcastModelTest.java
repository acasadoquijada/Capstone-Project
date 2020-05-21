package com.example.podcasfy;

import com.example.podcasfy.model.Podcast;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PodcastModelTest {

    private String name = "name";
    private String description = "description";
    private String url = "url";
    private String mediaURL = "mediaURL";

    @Test
    public void PodcastModelWorkAsExpected(){

        Podcast podcast = new Podcast();

        podcast.setName(name);
        podcast.setDescription(description);
        podcast.setUrl(url);
        podcast.setMediaURL(mediaURL);

        assertEquals(name,podcast.getName());
        assertEquals(description,podcast.getDescription());
        assertEquals(url, podcast.getUrl());
        assertEquals(mediaURL,podcast.getMediaURL());
        
    }
}
