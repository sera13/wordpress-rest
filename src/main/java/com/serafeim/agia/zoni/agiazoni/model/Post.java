package com.serafeim.agia.zoni.agiazoni.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    private Integer id;
    private String date;
    private String title;
    private String videoLink;


    public Post(){}
    public Post(Integer id, String date, String title) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.videoLink = null;
    }

    public Post(Integer id, String date, String title, String videoLink) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.videoLink = videoLink;
    }

    public String getDate() {
        return date;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}
