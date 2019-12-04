package com.serafeim.agia.zoni.agiazoni.model;

public class Taxonoy implements Comparable<Taxonoy> {
    String description;
    String name;
    String slug;
    String meta;

    public Taxonoy(String description, String name, String slug, String meta) {
        this.description = description;
        this.name = name;
        this.slug = slug;
        this.meta = meta;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    @Override
    public int compareTo(Taxonoy taxonoy) {
        return this.name.compareTo(taxonoy.getName());
    }
}
