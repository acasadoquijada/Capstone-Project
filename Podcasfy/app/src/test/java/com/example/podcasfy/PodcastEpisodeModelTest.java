package com.example.podcasfy;

import com.example.podcasfy.model.PodcastEpisode;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class PodcastEpisodeModelTest {

    @Test public void workAsExpected(){

        String storePathDevice = "folder/episode.mp3";
        String name = "planos and tests";
        String description = "description";
        String imageURL = "image.jpg";
        String mediaURL = "mediaURL";
        String podcastId = "podcastId";

        PodcastEpisode podcastEpisode = new PodcastEpisode(
                name,
                description,
                imageURL,
                mediaURL,
                podcastId
        );

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String date = formatter.format(calendar.getTime());

        podcastEpisode.setDate(date);
        podcastEpisode.setStorePathDevice(storePathDevice);

        assertEquals(storePathDevice,podcastEpisode.getStorePathDevice());
        assertEquals(name,podcastEpisode.getName());
        assertEquals(description,podcastEpisode.getDescription());
        assertEquals(mediaURL,podcastEpisode.getMediaURL());
        assertEquals(imageURL,podcastEpisode.getImageURL());
        assertEquals(date,podcastEpisode.getDate());

    }
}
