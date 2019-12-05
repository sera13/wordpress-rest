package com.serafeim.agia.zoni.agiazoni.model;

public class Category extends Taxonomy {
    Integer parent;

    public Category(String description, String name, String slug, Integer parent) {
        super(description, name, slug);
        this.setParent(parent);
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }
}
