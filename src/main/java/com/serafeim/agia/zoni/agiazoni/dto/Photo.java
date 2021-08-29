package com.serafeim.agia.zoni.agiazoni.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.HashSet;
import java.util.Set;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Photo extends Post {
    private Integer photo;
    private String editor;
    public Set<Integer> sectionPhoto = new HashSet<>();


    public Integer getPhoto() {
        return photo;
    }

    public void setPhoto(Integer photo) {
        this.photo = photo;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public Set<Integer> getSectionPhoto() {
        return sectionPhoto;
    }

    public void setSectionPhoto(Set<Integer> sectionPhoto) {
        this.sectionPhoto = sectionPhoto;
    }
}
