package com.manga.job.model;


import java.awt.*;
import java.util.List;

public class Chapter {
    private String name;
    private List<Image> pictures;

    public List<Image> getPictures() {
        return pictures;
    }

    public void setPictures(List<Image> pictures) {
        this.pictures = pictures;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
