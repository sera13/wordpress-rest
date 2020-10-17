package com.serafeim.agia.zoni.agiazoni.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Sound extends Article{
    public Integer file;
    public String soundLink;
    public Integer imagePreview;
    public String editor;
    public String duration;
    public String numReadings;
    public String iframe;

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getSoundLink() {
        return soundLink;
    }

    public void setSoundLink(String soundLink) {
        this.soundLink = soundLink;
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

    public Integer getFile() {
        return file;
    }

    public void setFile(Integer file) {
        this.file = file;
    }

    @Override
    public String getNumReadings() {
        return numReadings;
    }

    @Override
    public void setNumReadings(String numReadings) {
        this.numReadings = numReadings;
    }

    public String getIframe() {
        return iframe;
    }

    public void setIframe(String iframe) {
        this.iframe = iframe;
    }
}
