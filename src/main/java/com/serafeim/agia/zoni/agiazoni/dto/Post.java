package com.serafeim.agia.zoni.agiazoni.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {
    private BigInteger id;
    private String date;
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
    public Set<Integer> articleAuthors = new HashSet<>();
    public Set<Integer> sources = new HashSet<>();
    private String age;
    public String ennoima;
    private String idees1;
    private String idees2;
    private String idees3;
    private String idees4;
    private String idees5;
    private String idees6;
    private String idees7;
    private String idees8;
    private String idees9;
    private String idees10;
    public String numReadings;
    String oldWebisteId;



    public Post(){}
    public Post(BigInteger id, String date, String title, String ennoima){
        this.id = id;
        this.date = date;
        this.title = title;
        this.ennoima = ennoima;
    }
    public Post(BigInteger id, String date, String title) {
        this.id = id;
        this.date = date;
        this.title = title;
    }


    public Post(BigInteger id, String date, String title, String ennoima, String idees1, String idees2, String idees3, String idees4, String idees5, String idees6, String idees7, String idees8, String idees9, String idees10) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.ennoima = ennoima;
        this.idees1 = idees1;
        this.idees2 = idees2;
        this.idees3 = idees3;
        this.idees4 = idees4;
        this.idees5 = idees5;
        this.idees6 = idees6;
        this.idees7 = idees7;
        this.idees8 = idees8;
        this.idees9 = idees9;
        this.idees10 = idees10;
    }

    public Post(BigInteger id, String date, String title, String ennoima, String videoLink, String idees1, String idees2, String idees3, String idees4, String idees5, String idees6, String idees7, String idees8, String idees9, String idees10, String age) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.ennoima = ennoima;
        this.idees1 = idees1;
        this.idees2 = idees2;
        this.idees3 = idees3;
        this.idees4 = idees4;
        this.idees5 = idees5;
        this.idees6 = idees6;
        this.idees7 = idees7;
        this.idees8 = idees8;
        this.idees9 = idees9;
        this.idees10 = idees10;
        this.age = age;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public Integer getFeaturedMedia() {
        return featuredMedia;
    }

    public void setFeaturedMedia(Integer featuredMedia) {
        this.featuredMedia = featuredMedia;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getPingStatus() {
        return pingStatus;
    }

    public void setPingStatus(String pingStatus) {
        this.pingStatus = pingStatus;
    }

    public String getSticky() {
        return sticky;
    }

    public void setSticky(String sticky) {
        this.sticky = sticky;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public Set<Integer> getCategories() {
        return categories;
    }

    public void setCategories(Set<Integer> categories) {
        this.categories = categories;
    }

    public Set<Integer> getTags() {
        return tags;
    }

    public void setTags(Set<Integer> tags) {
        this.tags = tags;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Set<Integer> getArticleAuthors() {
        return articleAuthors;
    }

    public void setArticleAuthors(Set<Integer> articleAuthors) {
        this.articleAuthors = articleAuthors;
    }

    public Set<Integer> getSources() {
        return sources;
    }

    public void setSources(Set<Integer> sources) {
        this.sources = sources;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEnnoima() {
        return ennoima;
    }

    public void setEnnoima(String ennoima) {
        this.ennoima = ennoima;
    }

    public String getIdees1() {
        return idees1;
    }

    public void setIdees1(String idees1) {
        this.idees1 = idees1;
    }

    public String getIdees2() {
        return idees2;
    }

    public void setIdees2(String idees2) {
        this.idees2 = idees2;
    }

    public String getIdees3() {
        return idees3;
    }

    public void setIdees3(String idees3) {
        this.idees3 = idees3;
    }

    public String getIdees4() {
        return idees4;
    }

    public void setIdees4(String idees4) {
        this.idees4 = idees4;
    }

    public String getIdees5() {
        return idees5;
    }

    public void setIdees5(String idees5) {
        this.idees5 = idees5;
    }

    public String getIdees6() {
        return idees6;
    }

    public void setIdees6(String idees6) {
        this.idees6 = idees6;
    }

    public String getIdees7() {
        return idees7;
    }

    public void setIdees7(String idees7) {
        this.idees7 = idees7;
    }

    public String getIdees8() {
        return idees8;
    }

    public void setIdees8(String idees8) {
        this.idees8 = idees8;
    }

    public String getIdees9() {
        return idees9;
    }

    public void setIdees9(String idees9) {
        this.idees9 = idees9;
    }

    public String getIdees10() {
        return idees10;
    }

    public void setIdees10(String idees10) {
        this.idees10 = idees10;
    }

    public String getNumReadings() {
        return numReadings;
    }

    public void setNumReadings(String numReadings) {
        this.numReadings = numReadings;
    }

    public String getOldWebisteId() {
        return oldWebisteId;
    }

    public void setOldWebisteId(String oldWebisteId) {
        this.oldWebisteId = oldWebisteId;
    }
}
