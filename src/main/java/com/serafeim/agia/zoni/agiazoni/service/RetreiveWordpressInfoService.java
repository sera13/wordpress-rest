package com.serafeim.agia.zoni.agiazoni.service;

import com.serafeim.agia.zoni.agiazoni.model.Media;
import com.serafeim.agia.zoni.agiazoni.model.WPPostDTO;
import com.serafeim.agia.zoni.agiazoni.model.WPTaxonomyDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RetreiveWordpressInfoService {

    public static final String WEBSITE_URL = "http://serafeimkourlos.xyz/wp-json/wp/v2/";
    public static final String USERNAME = "serafeim";
    public static final String PASSWORD = "NFBN57Z8sVXs!a1N(IsFMdT(";
    Logger logger = LoggerFactory.getLogger(RetreiveWordpressInfoService.class);

    public List<WPTaxonomyDTO> getWPTaxonomy(String kindOfTaxonomy) {

        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication(USERNAME, PASSWORD).build();
        List<WPTaxonomyDTO> posts = new ArrayList<>();
        int index = 1;
        while (true) {
            WPTaxonomyDTO[] partialPosts = restTemplate.getForObject(WEBSITE_URL + kindOfTaxonomy + "?per_page=100&page=" + index, WPTaxonomyDTO[].class);
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

    public List<WPPostDTO> getWPPost(String type, String excludeCategories) {
        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication(USERNAME, PASSWORD).build();
        List<WPPostDTO> posts = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(WEBSITE_URL)
                .append(type)
                .append(!StringUtils.isEmpty(excludeCategories) ? "?categories_exclude=" + excludeCategories + "&per_page=100&page=" : "?per_page=100&page=");
        int index = 1;
        while (true) {
            WPPostDTO[] partialPosts = restTemplate.getForObject(stringBuilder.toString() + index, WPPostDTO[].class);
//            WPPostDTO[] partialPosts = restTemplate.getForObject("http://localhost:8081/wp-json/wp/v2/" + type + "?categories=9629&per_page=100&page=" + index, WPPostDTO[].class);
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

    public List<WPPostDTO> getWPPost(String type) {
        return getWPPost(type, null);
    }

    public List<Media> getAllMedia() {

        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication(USERNAME, PASSWORD).build();
        List<Media> mediaList = new ArrayList<>();
        int index = 1;
        while (true) {
            Media[] partialMedia = restTemplate.getForObject(WEBSITE_URL + "media?media_type=image&per_page=100&page=" + index, Media[].class);
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
