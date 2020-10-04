package com.serafeim.agia.zoni.agiazoni.model;

/**
 * In many cases we just need the id and name of the taxonomies json
 */
public class WPPostDTO {
    TitleDTO title;
    Integer id;
    String date;

    public WPPostDTO(){

    }
    public WPPostDTO(TitleDTO title, Integer id, String date) {
        this.title = title;
        this.id = id;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TitleDTO getTitle() {
        return title;
    }

    public void setTitle(TitleDTO title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
