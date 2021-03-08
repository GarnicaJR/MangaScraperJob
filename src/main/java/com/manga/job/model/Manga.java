package com.manga.job.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Manga {

    private String name;
    private String description;
    private boolean ongoing;
    private transient  Image picture;
    private transient  List<Chapter> chapters;

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOngoing() {
        return ongoing;
    }

    public void setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
    }


    @Override
    public String toString() {
        return "Manga{" +
                "name='" + name + '\'' +
                '}';
    }
}
