package com.serafeim.agia.zoni.agiazoni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * In many cases we just need the id and name of the taxonomies json
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)


public class WPPostDTO {
    TitleDTO title;
    Integer id;
    String date;
    String videoLink;
    String ennoima;


    public WPPostDTO() {

    }

    public WPPostDTO(TitleDTO title, Integer id, String date, String videoLink) {
        this.title = title;
        this.id = id;
        this.date = date;
        this.videoLink = videoLink;
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

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getEnnoima() {
        return ennoima;
    }

    public void setEnnoima(String ennoima) {
        this.ennoima = ennoima;
    }
}
