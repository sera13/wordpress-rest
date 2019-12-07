package com.serafeim.agia.zoni.agiazoni.model;

import java.util.Set;

public class Article {
    public String date;
    public String status;
    public String type;
    public String link;
    public String title;
    public String content;
    public String excerpt;
    public String author;
    public String featured_media;
    public String comment_status;
    public String ping_status;
    public String sticky;
    public String format;
    public String meta;
    public Set<Integer> categories;
    public Set<Integer> tags;
    public Set<Integer> article_author;
    public String ennoima;
    public Set<Integer> source;

    public Article() {
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return this.excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFeatured_media() {
        return this.featured_media;
    }

    public void setFeatured_media(String featured_media) {
        this.featured_media = featured_media;
    }

    public String getComment_status() {
        return this.comment_status;
    }

    public void setComment_status(String comment_status) {
        this.comment_status = comment_status;
    }

    public String getPing_status() {
        return this.ping_status;
    }

    public void setPing_status(String ping_status) {
        this.ping_status = ping_status;
    }

    public String getSticky() {
        return this.sticky;
    }

    public void setSticky(String sticky) {
        this.sticky = sticky;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getMeta() {
        return this.meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public Set<Integer> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Integer> categories) {
        this.categories = categories;
    }

    public Set<Integer> getTags() {
        return this.tags;
    }

    public void setTags(Set<Integer> tags) {
        this.tags = tags;
    }

    public Set<Integer> getArticle_author() {
        return this.article_author;
    }

    public void setArticle_author(Set<Integer> article_author) {
        this.article_author = article_author;
    }

    public String getEnnoima() {
        return this.ennoima;
    }

    public void setEnnoima(String ennoima) {
        this.ennoima = ennoima;
    }

    public Set<Integer> getSource() {
        return this.source;
    }

    public void setSource(Set<Integer> source) {
        this.source = source;
    }
}
