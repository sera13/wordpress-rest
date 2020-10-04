package com.serafeim.agia.zoni.agiazoni.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    Integer id;
    public String date;
    public String title;

    public Post(){}
    public Post(Integer id, String date, String title) {
        this.id = id;
        this.date = date;
        this.title = title;
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
}
