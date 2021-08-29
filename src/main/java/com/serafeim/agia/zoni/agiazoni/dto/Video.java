package com.serafeim.agia.zoni.agiazoni.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigInteger;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Video extends Post {
    public String editor;
    public String videoLink;
    public Integer imagePreview;
    public String duration;

    public Video() {
    }

    public Video(BigInteger id, String date, String title, String videoLink) {
        this.setId(id);
        this.setDate(date);
        this.setTitle(title);
        this.setVideoLink(videoLink);
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public Integer getImagePreview() {
        return imagePreview;
    }

    public void setImagePreview(Integer imagePreview) {
        this.imagePreview = imagePreview;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
