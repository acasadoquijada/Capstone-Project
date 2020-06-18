package com.example.podcasfy.utils;

import com.example.podcasfy.model.Episode;
import com.example.podcasfy.model.Podcast;

import java.util.List;

public interface EpisodeCallBack {
    public void updateEpisodeList(List<Episode> episodeList, String option, String url);

}
