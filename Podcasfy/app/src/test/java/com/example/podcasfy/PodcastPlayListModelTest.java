package com.example.podcasfy;

import com.example.podcasfy.model.PodcastPlayList;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PodcastPlayListModelTest {


    /**
     *  In this test we test one thing:
     *
     *  getCurrentEpisode returns the corresponding episode. This method returns a podcastEpisodeId
     *  according to the value in currentEpisode. This value doesn't need extra checking as the methods
     *  that changes the value perform checking to ensure that currentEpisode is in correct range
     */
     @Test  public void getCurrentEpisodeWorkAsExpected(){

        PodcastPlayList podcastPlayList = new PodcastPlayList();

        podcastPlayList.addEpisode("episode0Id");
        podcastPlayList.addEpisode("episode1Id");
        podcastPlayList.addEpisode("episode2Id");

        podcastPlayList.getNextEpisode();

        String current = podcastPlayList.getCurrentEpisode();

        assertEquals("episode1Id",current);
    }


    /**
     * In this test we test two things:
     *
     * - If we are in the last episode and try to get the next one, we should get "".
     * - If there are more episodes, we should get the episodeId
     */

    @Test public void getNextEpisode(){

        String nextEpisode;
        PodcastPlayList podcastPlayList = new PodcastPlayList();

        podcastPlayList.addEpisode("episode0Id");
        podcastPlayList.addEpisode("episode1Id");

        nextEpisode = podcastPlayList.getNextEpisode();

        assertEquals("episode1Id", nextEpisode);

        nextEpisode = podcastPlayList.getNextEpisode();

        assertEquals("", nextEpisode);

    }

    /**
     * In this test we test two things:
     *
     * - If we are in the first episode and try to get the previous one, we should get "".
     * - If there are more episodes, we should get the episodeId
     */
    @Test public void getPreviousEpisode(){

        String prevEpisode;

        PodcastPlayList podcastPlayList = new PodcastPlayList();

        podcastPlayList.addEpisode("episode0Id");
        podcastPlayList.addEpisode("episode1Id");

        // Set current episode to the last one

        podcastPlayList.setCurrentEpisode(1);

        // After calling getPreviousEpisode currentEpisode = episode0Id
        prevEpisode = podcastPlayList.getPreviousEpisode();

        assertEquals("episode0Id", prevEpisode);

        // After getPreviousEpisode currentEpisode is still episode0Id but we return ""
        // as is the first episode
        prevEpisode = podcastPlayList.getPreviousEpisode();

        assertEquals("", prevEpisode);
    }

    /**
     * In this test we test two things:
     *
     * - If the argument pass to setCurrentEpisode (int position) is not in the PlayList range,
     * currentEpisode is not updated
     * - If the argument pass to setCurrentEpisode is in the PlayList range, currentEpisode is
     * updated
     */
    @Test public void setCurrentEpisodeWithIndex(){

        PodcastPlayList podcastPlayList = new PodcastPlayList();

        podcastPlayList.addEpisode("episode0Id");
        podcastPlayList.addEpisode("episode1Id");
        podcastPlayList.addEpisode("episode2Id");

        // 100 is not in the playlist range. Then we don't update the currentEpisode
        podcastPlayList.setCurrentEpisode(100);

        assertEquals("episode0Id",podcastPlayList.getCurrentEpisode());

        // 2 is  in the playlist range. Then we update the currentEpisode
        podcastPlayList.setCurrentEpisode(2);

        assertEquals("episode2Id",podcastPlayList.getCurrentEpisode());
    }

    /**
     * In this test we test two things:
     *
     * - If the argument pass to setCurrentEpisode (String episodeId) is not in the PlayList,
     * currentEpisode is not updated
     * - If the argument pass to setCurrentEpisode is in the PlayList, currentEpisode is
     * updated
     */
    @Test public void setCurrentEpisodeWithPodcastEpisodeId(){
        PodcastPlayList podcastPlayList = new PodcastPlayList();

        podcastPlayList.addEpisode("episode0Id");
        podcastPlayList.addEpisode("episode1Id");
        podcastPlayList.addEpisode("episode2Id");

        // episode100Id is not in the playlist. Then we don't update the currentEpisode
        podcastPlayList.setCurrentEpisode("episode100Id");

        assertEquals("episode0Id",podcastPlayList.getCurrentEpisode());

        // episode2Id is in the playlist. Then we update the currentEpisode
        podcastPlayList.setCurrentEpisode("episode2Id");

        assertEquals("episode2Id",podcastPlayList.getCurrentEpisode());
    }


        @Test public void deleteEpisode(){
        PodcastPlayList podcastPlayList = new PodcastPlayList();

        podcastPlayList.addEpisode("episode0Id");
        podcastPlayList.addEpisode("episode1Id");
        podcastPlayList.addEpisode("episode2Id");

        podcastPlayList.removeEpisode("episode1Id");

        assertEquals(2,podcastPlayList.getPlayListSize());

    }
}
