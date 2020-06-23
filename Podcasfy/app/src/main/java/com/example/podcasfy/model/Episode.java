package com.example.podcasfy.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "episode")
public class Episode {

    @PrimaryKey(autoGenerate = true)
    private int id;

    // This is the path where the mp3 file is stored in the device
    private String storePathDevice;

    // Podcast Episode name
    private String name;

    // Podcast Episode description
    private String description;

    // Podcast Episode image
    private String imageURL;

    // Podcast Episode media (audio)
    private String mediaURL;

    // Date when the Podcast Episode was listened to
    private String date;

    // Foreign key to identify the podcast where the episode belongs
    private String podcastId;

    public Episode(){}

    public Episode(
            String name,
            String description,
            String imageURL,
            String mediaURL,
            String podcastId){

        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.mediaURL = mediaURL;
        this.podcastId = podcastId;
    }

    public void setStorePathDevice(String storePathDevice) {
        this.storePathDevice = storePathDevice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL = mediaURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStorePathDevice() {
        return storePathDevice;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getDate() {
        return date;
    }

    public String getPodcastId() {
        return podcastId;
    }

    void setPodcastId(String podcastId) {
        this.podcastId = podcastId;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }
}
