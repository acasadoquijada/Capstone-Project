package com.example.podcasfy;

import com.example.podcasfy.model.PodcastEpisode;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class PodcastEpisodeModelTest {

    @Test public void workAsExpected(){

        String url = "folder/episode.mp3";
        String name = "planos and tests";
        String description = "description";
        String imageURL = "image.jpg";
        String mediaURL = "mediaURL";

        PodcastEpisode podcastEpisode = new PodcastEpisode();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String date = formatter.format(calendar.getTime());

        podcastEpisode.setPath(url);
        podcastEpisode.setName(name);
        podcastEpisode.setDescription(description);
        podcastEpisode.setImageURL(imageURL);
        podcastEpisode.setMediaURL(mediaURL);
        podcastEpisode.setDate(date);

        assertEquals(url,podcastEpisode.getPath());
        assertEquals(name,podcastEpisode.getName());
        assertEquals(description,podcastEpisode.getDescription());
        assertEquals(mediaURL,podcastEpisode.getMediaURL());
        assertEquals(imageURL,podcastEpisode.getImageURL());
        assertEquals(date,podcastEpisode.getDate());

    }
}
