package com.serafeim.agia.zoni.agiazoni.model;

public class ArticleAuthor extends Taxonomy {

    public ArticleAuthor(){
        super();
    }
    public ArticleAuthor(String description, String name, String slug) {
        super(description, name, slug);
    }

    public ArticleAuthor(Integer id, String description, String name, String slug, String oldWebisteId, String profession) {
        super(description, name, slug);
        this.id = id;
        this.oldWebisteId = oldWebisteId;
        this.profession = profession;
    }
}
