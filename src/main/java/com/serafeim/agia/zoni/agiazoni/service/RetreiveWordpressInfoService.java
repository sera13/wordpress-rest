package com.serafeim.agia.zoni.agiazoni.service;

import com.serafeim.agia.zoni.agiazoni.dto.Media;
import com.serafeim.agia.zoni.agiazoni.dto.Post;
import com.serafeim.agia.zoni.agiazoni.dto.Taxonomy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RetreiveWordpressInfoService {

    Logger logger = LoggerFactory.getLogger(RetreiveWordpressInfoService.class);

    public List<Taxonomy> getWPTaxonomy(String kindOfTaxonomy) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        RestTemplate restTemplate = RestClientUtil.restTemplate();
        List<Taxonomy> posts = new ArrayList<>();
        int index = 1;
        while (true) {
            Taxonomy[] partialPosts = restTemplate.getForObject(RestClientUtil.WEBSITE_URL_SERAFEIMKOURLOS +"/wp-json/wp/v2/"+ kindOfTaxonomy + "?per_page=100&page=" + index, Taxonomy[].class);
            assert partialPosts != null;
            if (partialPosts.length > 0) {
                posts.addAll(List.of(partialPosts));
                index++;
            } else {
                break;
            }
            // Do not make another loop if less than 100
            if (partialPosts.length < 100) {
                break;
            }
        }

        return posts;
    }

    public List<Post> getWPPost(String type, String excludeCategories) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        RestTemplate restTemplate = RestClientUtil.restTemplate();
        List<Post> posts = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(RestClientUtil.WEBSITE_URL_SERAFEIMKOURLOS +"/wp-json/wp/v2/")
                .append(type)
                .append(!StringUtils.isEmpty(excludeCategories) ? "?categories_exclude=" + excludeCategories + "&per_page=100&page=" : "?per_page=100&page=");
        int index = 1;
        while (true) {
            Post[] partialPosts = restTemplate.getForObject(stringBuilder.toString() + index, Post[].class);
            assert partialPosts != null;
            if (partialPosts.length > 0) {
                posts.addAll(List.of(partialPosts));
                index++;
            } else {
                break;
            }
            // Do not make another loop if less than 100
            if (partialPosts.length < 100) {
                break;
            }
        }

        return posts;
    }

    public List<Media> getAllMedia(String fromDate) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        RestTemplate restTemplate = RestClientUtil.restTemplate();
        List<Media> mediaList = new ArrayList<>();
        int index = 1;
        while (true) {
            Media[] partialMedia = restTemplate.getForObject(RestClientUtil.WEBSITE_URL_PRODUCTION +"/wp-json/wp/v2/" + "media?after=" + fromDate + "&media_type=image&per_page=100&page=" + index, Media[].class);
            assert partialMedia != null;
            if (partialMedia.length > 0) {
                mediaList.addAll(List.of(partialMedia));
                index++;
            } else {
                break;
            }
            // Do not make another loop if less than 100
            if (partialMedia.length < 100) {
                break;
            }
        }

        return mediaList;
    }


}
