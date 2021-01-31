package com.serafeim.agia.zoni.agiazoni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * In many cases we just need the id and name of the taxonomies json
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class WPPostDTO {
    TitleDTO title;
    Integer id;
    String date;
    String videoLink;
    String ennoima;
    String idees1;
    String idees2;
    String idees3;
    String idees4;
    String idees5;
    String idees6;
    String idees7;
    String idees8;
    String idees9;
    String idees10;


    public WPPostDTO() {

    }

    public WPPostDTO(TitleDTO title, Integer id, String date, String videoLink) {
        this.title = title;
        this.id = id;
        this.date = date;
        this.videoLink = videoLink;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TitleDTO getTitle() {
        return title;
    }

    public void setTitle(TitleDTO title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
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

    public String getEnnoima() {
        return ennoima;
    }

    public void setEnnoima(String ennoima) {
        this.ennoima = ennoima;
    }
}
