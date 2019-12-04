package com.serafeim.agia.zoni.agiazoni.model;

public class Category extends Taxonoy {
    String parent;

    public Category(String description, String name, String slug, String meta) {
        super(description, name, slug, meta);
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
