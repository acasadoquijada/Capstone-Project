package com.example.podcasfy;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.podcasfy.model.AppDataBase;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.model.PodcastDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;


@RunWith(AndroidJUnit4.class)
@Config(manifest= Config.NONE)
public class PodcastDataBaseTest {

    private AppDataBase db;
    private PodcastDAO podcastDAO;

    private String provider1 = "provider1";
    private String provider2 = "provider2";
    private String name = "name";

    private int podcastNumber = 2;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before public void setup(){

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        // For testing purpose we are going to allow db queries in the main thread as
        // explained in this StackOverflow post
        // https://stackoverflow.com/questions/44270688/unit-testing-room-and-livedata
        db = Room.inMemoryDatabaseBuilder(context,AppDataBase.class).allowMainThreadQueries().build();

        podcastDAO = db.podcastDAO();

        addPodcasts();
    }

    public void addPodcasts(){

        Podcast p1 = new Podcast(
                name,
                "description",
                "url",
                "mediaURL",
                provider1
        );

        Podcast p2 = new Podcast(
                name,
                "description",
                "url",
                "mediaURL",
                provider2
        );

        podcastDAO.insertPodcast(p1);
        podcastDAO.insertPodcast(p2);
    }


    @Test public void getAllPodcasts() {

        List<Podcast> podcasts;
        podcasts = podcastDAO.getPodcasts();

        assertNotNull(podcasts);
        assertEquals(podcastNumber,podcasts.size());
    }

    @Test public void getPodcastDifferentProviders(){

        List<Podcast> podcasts;
        podcasts = podcastDAO.getPodcastsByProvider(provider1);

        assertNotNull(podcasts);
        assertEquals(1,podcasts.size());
        assertEquals(provider1,podcasts.get(0).getProvider());

        podcasts.clear();

        podcasts = podcastDAO.getPodcastsByProvider(provider2);
        assertNotNull(podcasts);
        assertEquals(1,podcasts.size());
        assertEquals(provider2,podcasts.get(0).getProvider());
    }

    @Test public void getPodcastByName(){

        List<Podcast> podcasts;

        podcasts = podcastDAO.getPodcastsByName(name);
        assertNotNull(podcasts);
        assertEquals(podcastNumber,podcasts.size());
    }

    @Test public void deletePodcast(){

        List<Podcast> podcasts;

       Podcast p3 = new Podcast(
                "my_name",
                "description",
                "url",
                "mediaURL",
                provider2
        );

        podcastDAO.insertPodcast(p3);
        podcastDAO.delete(p3);

        podcasts = podcastDAO.getPodcasts();

        assertNotNull(podcasts);
        assertEquals(podcastNumber,podcasts.size());
    }

    @After public void deleteTestData(){

        podcastDAO.deleteTable();
        db.close();

    }
}
