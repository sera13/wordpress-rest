package com.serafeim.agia.zoni.agiazoni.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Edafio extends Post {

    public String contentid;
    public String bookid;
    public String book;
    public String arxaio;
    public String metafrasi;
    public String kef;
    public String edafio;
    public String part;
    public String agbookname;
    public String agcategory;


    public Edafio() {
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

    public String getContentid() {
        return contentid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getArxaio() {
        return arxaio;
    }

    public void setArxaio(String arxaio) {
        this.arxaio = arxaio;
    }

    public String getMetafrasi() {
        return metafrasi;
    }

    public void setMetafrasi(String metafrasi) {
        this.metafrasi = metafrasi;
    }

    public String getKef() {
        return kef;
    }

    public void setKef(String kef) {
        this.kef = kef;
    }

    public String getEdafio() {
        return edafio;
    }

    public void setEdafio(String edafio) {
        this.edafio = edafio;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getAgbookname() {
        return agbookname;
    }

    public void setAgbookname(String agbookname) {
        this.agbookname = agbookname;
    }

    public String getAgcategory() {
        return agcategory;
    }

    public void setAgcategory(String agcategory) {
        this.agcategory = agcategory;
    }
}
