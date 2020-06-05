package com.example.podcasfy;

import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.podcasfy.model.AppDataBase;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.model.PodcastDAO;
import com.example.podcasfy.model.PodcastEpisode;
import com.example.podcasfy.model.PodcastEpisodeDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@Config(manifest= Config.NONE)

public class PodcastEpisodeDataBaseTest {

    private AppDataBase db;
    private PodcastDAO podcastDAO;
    private PodcastEpisodeDAO podcastEpisodeDAO;

    private String provider1 = "provider1";

    private String podcastId;


    @Before
    public void setup(){

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        // For testing purpose we are going to allow db queries in the main thread as
        // explained in this StackOverflow post
        // https://stackoverflow.com/questions/44270688/unit-testing-room-and-livedata
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase.class).allowMainThreadQueries().build();

        podcastDAO = db.podcastDAO();
        podcastEpisodeDAO = db.podcastEpisodeDAO();

        addPodcasts();
    }

    private void addPodcasts(){
        String podcastName = "Podcast name";
        Podcast p1 = new Podcast(
                podcastName,
                "description",
                "url",
                "mediaURL",
                provider1
        );

        podcastDAO.insertPodcast(p1);
    }


    @Test public void insertPodcastEpisode() throws InterruptedException {

        int episodeNumber = 2;

        // The podcastId will be calculated in the repository (MVVM) and then provided to
        // podcastEpisodeDAO. For testing purposes we query for getting it

        podcastId = podcastDAO.getPodcastsByProvider(provider1).get(0).getId();

        PodcastEpisode podcastEpisode1 = new PodcastEpisode(
                "Episode 1",
                "This is a description",
                "url_to_the_image.jpg",
                "url_to_audio.mp3",
                podcastId
        );

        PodcastEpisode podcastEpisode2 = new PodcastEpisode(
                "Episode 2",
                "This is another description",
                "url_to_the_image.jpg",
                "url_to_audio.mp3",
                podcastId
        );

        podcastEpisodeDAO.insertEpisode(podcastEpisode1);
        podcastEpisodeDAO.insertEpisode(podcastEpisode2);

        List<PodcastEpisode> podcastEpisodes = podcastEpisodeDAO.getEpisodes(podcastId);

        assertNotNull(podcastEpisodes);

        assertEquals(episodeNumber,podcastEpisodes.size());
    }

    @Test public void deleteEpisode(){

        PodcastEpisode podcastEpisode3 = new PodcastEpisode(
                "Episode 3",
                "This is a description",
                "url_to_the_image.jpg",
                "url_to_audio.mp3",
                podcastId
        );

        podcastEpisodeDAO.insertEpisode(podcastEpisode3);
        podcastEpisodeDAO.deleteEpisode(podcastEpisode3);

        List<PodcastEpisode> podcastEpisodes = podcastEpisodeDAO.getEpisodes(podcastId);

        assertNotNull(podcastEpisodes);
        assertEquals(0,podcastEpisodes.size());

    }

    @After public void finishTest(){
        db.close();
    }
}
