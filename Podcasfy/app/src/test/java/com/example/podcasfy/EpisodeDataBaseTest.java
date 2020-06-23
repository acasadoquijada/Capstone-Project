package com.example.podcasfy;

import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.podcasfy.model.AppDataBase;
import com.example.podcasfy.model.Episode;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.model.PodcastDAO;
import com.example.podcasfy.model.EpisodeDAO;

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

public class EpisodeDataBaseTest {

    private AppDataBase db;
    private PodcastDAO podcastDAO;
    private EpisodeDAO episodeDAO;

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
        episodeDAO = db.podcastEpisodeDAO();

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

        Episode episode1 = new Episode(
                "Episode 1",
                "This is a description",
                "url_to_the_image.jpg",
                "url_to_audio.mp3",
                podcastId
        );

        Episode episode2 = new Episode(
                "Episode 2",
                "This is another description",
                "url_to_the_image.jpg",
                "url_to_audio.mp3",
                podcastId
        );

        episodeDAO.insertEpisode(episode1);
        episodeDAO.insertEpisode(episode2);

        List<Episode> episodes = episodeDAO.getEpisodes(podcastId);

        assertNotNull(episodes);

        assertEquals(episodeNumber, episodes.size());
    }

    @Test public void deleteEpisode(){

        Episode episode3 = new Episode(
                "Episode 3",
                "This is a description",
                "url_to_the_image.jpg",
                "url_to_audio.mp3",
                podcastId
        );

        episodeDAO.insertEpisode(episode3);
        episodeDAO.deleteEpisode(episode3);

        List<Episode> episodes = episodeDAO.getEpisodes(podcastId);

        assertNotNull(episodes);
        assertEquals(0, episodes.size());

    }

    @After public void finishTest(){
        db.close();
    }
}
