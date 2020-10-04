package com.serafeim.agia.zoni.agiazoni.model;

/**
 * In many cases we just need the id and name of the taxonomies json
 */
public class WPTaxonomyDTO {
    TitleDTO title;
    Integer id;
    String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
