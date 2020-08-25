package com.serafeim.agia.zoni.agiazoni.model;

public class Taxonomy implements Comparable<Taxonomy> {
    String description;
    String name;
    String slug;
    String meta;

    public Taxonomy(String description, String name, String slug) {
        this.description = description;
        this.name = name;
        this.slug = slug;
        this.meta = null;
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
    public int compareTo(Taxonomy taxonomy) {
        return this.slug.compareTo(taxonomy.getSlug());
    }

    @Override
    public String toString() {
        return String.format("[description: %s name: %s slug: %s]", this.description, this.name, this.slug);
    }
}
