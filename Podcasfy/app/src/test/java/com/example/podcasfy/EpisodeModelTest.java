package com.example.podcasfy;

import com.example.podcasfy.model.Episode;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class EpisodeModelTest {

    @Test public void workAsExpected(){

        String storePathDevice = "folder/episode.mp3";
        String name = "planos and tests";
        String description = "description";
        String imageURL = "image.jpg";
        String mediaURL = "mediaURL";
        String podcastId = "podcastId";

        Episode episode = new Episode(
                name,
                description,
                imageURL,
                mediaURL,
                podcastId
        );

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String date = formatter.format(calendar.getTime());

        episode.setDate(date);
        episode.setStorePathDevice(storePathDevice);

        assertEquals(storePathDevice, episode.getStorePathDevice());
        assertEquals(name, episode.getName());
        assertEquals(description, episode.getDescription());
        assertEquals(mediaURL, episode.getMediaURL());
        assertEquals(imageURL, episode.getImageURL());
        assertEquals(date, episode.getDate());

    }
}
