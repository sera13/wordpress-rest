package com.serafeim.agia.zoni.agiazoni.service;

import com.serafeim.agia.zoni.agiazoni.model.Media;
import com.serafeim.agia.zoni.agiazoni.model.Post;
import com.serafeim.agia.zoni.agiazoni.model.WPPostDTO;
import com.serafeim.agia.zoni.agiazoni.model.WPTaxonomyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RetreiveWordpressInfoService {

    Logger logger = LoggerFactory.getLogger(RetreiveWordpressInfoService.class);


    /**
     * it is very specific method //TODO we need to remove it
     * @param kindOfPost
     * @return
     * @throws Exception
     */
    public List<Post> getAllPosts(String kindOfPost) throws Exception {

        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("serafeim", "NFBN57Z8sVXs!a1N(IsFMdT(").build();
        List<Post> posts = new ArrayList<>();
        int index = 1;
        while (true) {
                Post[] partialPosts = restTemplate.getForObject("http://localhost:8081/wp-json/wp/v2/" + kindOfPost + "?status=private&per_page=100&page=" + index, Post[].class);
                assert partialPosts != null;
                if (partialPosts.length > 0) {
                    posts.addAll(List.of(partialPosts));
                    index++;
                } else {
                    break;
                }
            // Do not make another loop if less than 100
            if (partialPosts.length < 100){
                break;
            }
        }

        return posts;
    }

    public List<WPTaxonomyDTO> getWPTaxonomy(String kindOfTaxonomy) throws Exception {

        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("serafeim", "NFBN57Z8sVXs!a1N(IsFMdT(").build();
        List<WPTaxonomyDTO> posts = new ArrayList<>();
        int index = 1;
        while (true) {
            WPTaxonomyDTO[] partialPosts = restTemplate.getForObject("http://localhost:8081/wp-json/wp/v2/" + kindOfTaxonomy + "?per_page=100&page=" + index, WPTaxonomyDTO[].class);
                assert partialPosts != null;
                if (partialPosts.length > 0) {
                    posts.addAll(List.of(partialPosts));
                    index++;
                } else {
                    break;
                }
            // Do not make another loop if less than 100
            if (partialPosts.length < 100){
                break;
            }
        }

        return posts;
    }

    public List<WPPostDTO> getWPPost(String type) {
        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("serafeim", "NFBN57Z8sVXs!a1N(IsFMdT(").build();
        List<WPPostDTO> posts = new ArrayList<>();
        int index = 1;
        while (true) {
//            WPPostDTO[] partialPosts = restTemplate.getForObject("http://localhost:8081/wp-json/wp/v2/" + type + "?categories_exclude=9625,9626,9629&per_page=100&page=" + index, WPPostDTO[].class);
            WPPostDTO[] partialPosts = restTemplate.getForObject("http://localhost:8081/wp-json/wp/v2/" + type + "?categories=9629&per_page=100&page=" + index, WPPostDTO[].class);
            assert partialPosts != null;
            if (partialPosts.length > 0) {
                posts.addAll(List.of(partialPosts));
                index++;
            } else {
                break;
            }
            // Do not make another loop if less than 100
            if (partialPosts.length < 100){
                break;
            }
        }

        return posts;
    }

    public List<Media> getAllMedia() {

        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("serafeim", "NFBN57Z8sVXs!a1N(IsFMdT(").build();
        List<Media> mediaList = new ArrayList<>();
        int index = 1;
        while (true) {
            Media[] partialMedia = restTemplate.getForObject("http://localhost:8081/wp-json/wp/v2/media?media_type=image&per_page=100&page=" + index, Media[].class);
            assert partialMedia != null;
            if (partialMedia.length > 0) {
                mediaList.addAll(List.of(partialMedia));
                index++;
            } else {
                break;
            }
            // Do not make another loop if less than 100
            if (partialMedia.length < 100){
                break;
            }
        }

        return mediaList;
    }


}
