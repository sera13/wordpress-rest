package com.serafeim.agia.zoni.agiazoni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigInteger;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {
    private BigInteger id;
    private String date;
    private String title;
    private String ennoima;
    private String videoLink;
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
    private String age;


    public Post(){}
    public Post(BigInteger id, String date, String title, String videoLink, String ennoima){
        this.id = id;
        this.date = date;
        this.title = title;
        this.videoLink = videoLink;
        this.ennoima = ennoima;
    }
    public Post(BigInteger id, String date, String title) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.videoLink = null;
    }


    public Post(BigInteger id, String date, String title, String ennoima, String videoLink, String idees1, String idees2, String idees3, String idees4, String idees5, String idees6, String idees7, String idees8, String idees9, String idees10) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.ennoima = ennoima;
        this.videoLink = videoLink;
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
        this.videoLink = videoLink;
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

    public Post(BigInteger id, String date, String title, String age) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.age = age;
    }

    public String getDate() {
        return date;
    }



    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
