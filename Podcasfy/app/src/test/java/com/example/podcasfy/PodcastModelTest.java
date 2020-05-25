package com.example.podcasfy;

import com.example.podcasfy.model.Podcast;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PodcastModelTest {


    @Test
    public void workAsExpected(){

        String name = "name";
        String description = "description";
        String url = "url";
        String mediaURL = "mediaURL";
        String provider = "ivoox";

        Podcast podcast = new Podcast();

        podcast.setName(name);
        podcast.setDescription(description);
        podcast.setUrl(url);
        podcast.setMediaURL(mediaURL);
        podcast.setProvider(provider);

        assertEquals(name,podcast.getName());
        assertEquals(description,podcast.getDescription());
        assertEquals(url, podcast.getUrl());
        assertEquals(mediaURL,podcast.getMediaURL());
        assertEquals(provider,podcast.getProvider());
        
    }
}
