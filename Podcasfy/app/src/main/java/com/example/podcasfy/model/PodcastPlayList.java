package com.example.podcasfy.model;


import java.util.ArrayList;
import java.util.List;



public class PodcastPlayList {

    private int currentEpisode;
    private List<String> podcastEpisodeId;

    public PodcastPlayList(){
        podcastEpisodeId = new ArrayList<>();
        currentEpisode = 0;
    }

    public int getPlayListSize(){
        return podcastEpisodeId.size();
    }

    public void addEpisode(String episodeId){
        podcastEpisodeId.add(episodeId);
    }

    public void removeEpisode(String episodeId){
        podcastEpisodeId.remove(episodeId);
    }

    public String getCurrentEpisode(){
        return podcastEpisodeId.get(currentEpisode);
    }

    public void setCurrentEpisode(int pos){
        if(pos > 0 && pos < podcastEpisodeId.size()){
            currentEpisode = pos;
        }
    }

    public void setCurrentEpisode(String episodeId){
        if(podcastEpisodeId.contains(episodeId)){
            currentEpisode = podcastEpisodeId.indexOf(episodeId);
        }
    }

    /**
     * Returns the next episode in the PlayList.
     * @return podcastEpisodeId of the next episode. "" if the current episode is the last one.
     */
    public String getNextEpisode(){

        // Check if current is last episode
        if(currentEpisode+1 == podcastEpisodeId.size()){
            return "";
        }
        currentEpisode = currentEpisode + 1;
        return podcastEpisodeId.get(currentEpisode);
    }

    /**
     * Returns the previous episode in the PlayList.
     * @return podcastEpisodeId of the previous episode. "" if the current episode is the first one.
     */
    public String getPreviousEpisode(){
        // Check if current is first episode
        if(currentEpisode-1 == -1){
            return "";
        }

        currentEpisode = currentEpisode - 1;

        return podcastEpisodeId.get(currentEpisode);
    }
}
