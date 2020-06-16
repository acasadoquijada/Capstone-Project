package com.example.podcasfy.utils;

import com.example.podcasfy.model.Podcast;

import java.util.List;

public interface PodcastCallBack {
    public void updatePodcastList(List<Podcast> podcastList);
}