package com.serafeim.agia.zoni.agiazoni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Article implements Serializable {

    public String date;
    public String status;
    public String title;
    public String content;
    public String excerpt;
    public Integer featuredMedia;
    public String commentStatus;
    public String pingStatus;
    public String sticky;
    public String format;
    public String meta;
    public Set<Integer> categories = new HashSet<>();
    public Set<Integer> tags = new HashSet<>();
    public String template;
    // those 4 fields are for the posts
    public String ennoima;
    public Set<Integer> articleAuthors = new HashSet<>();
    public Set<Integer> sources = new HashSet<>();
    public String numReadings;

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

    public Integer getFeaturedMedia() {
        return this.featuredMedia;
    }

    public void setFeaturedMedia(Integer featuredMedia) {
        this.featuredMedia = featuredMedia;
    }

    public String getCommentStatus() {
        return this.commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getPingStatus() {
        return this.pingStatus;
    }

    public void setPingStatus(String pingStatus) {
        this.pingStatus = pingStatus;
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

    public Set<Integer> getArticleAuthors() {
        return this.articleAuthors;
    }

    public void setArticleAuthors(Set<Integer> articleAuthors) {
        this.articleAuthors = articleAuthors;
    }

    public String getEnnoima() {
        return this.ennoima;
    }

    public void setEnnoima(String ennoima) {
        this.ennoima = ennoima;
    }

    public Set<Integer> getSources() {
        return this.sources;
    }

    public void setSources(Set<Integer> sources) {
        this.sources = sources;
    }

    public String getNumReadings() {
        return numReadings;
    }

    public void setNumReadings(String numReadings) {
        this.numReadings = numReadings;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
