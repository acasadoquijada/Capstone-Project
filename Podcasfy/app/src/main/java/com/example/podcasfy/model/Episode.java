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

    // Podcast Episode name
    private String name;

    // Podcast Episode description
    private String description;

    // Podcast Episode image
    private String imageURL;

    // Podcast Episode media (audio)
    private String mediaURL;


    public Episode(){}

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

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }
}
