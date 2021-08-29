package com.serafeim.agia.zoni.agiazoni.dto;

public class Taxonomy implements Comparable<Taxonomy> {
    Integer id;
    String description;
    String name;
    String slug;
    String meta;
    String profession;
    String oldWebisteId;


    public Taxonomy() {
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getOldWebisteId() {
        return oldWebisteId;
    }

    public void setOldWebisteId(String oldWebisteId) {
        this.oldWebisteId = oldWebisteId;
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
