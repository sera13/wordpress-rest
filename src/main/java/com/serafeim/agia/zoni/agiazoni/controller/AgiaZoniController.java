package com.serafeim.agia.zoni.agiazoni.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.serafeim.agia.zoni.agiazoni.model.*;
import com.serafeim.agia.zoni.agiazoni.service.ReadJSONService;
import com.serafeim.agia.zoni.agiazoni.service.RestClientService;
import com.serafeim.agia.zoni.agiazoni.service.RetreiveWordpressInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class AgiaZoniController {

    public static final String HTTP_LOCALHOST_8081_WP_JSON_WP_V_2 = "http://localhost:8081/wp-json/wp/v2/";
    public static final String HTTP_VIDEOS = "http://localhost:8081/wp-json/wp/v2/video_posts";
    @Autowired
    public ReadJSONService readJSONService;
    @Autowired
    public RetreiveWordpressInfoService retreiveWordpressInfoService;
    @Autowired
    public RestClientService restClientService;


    @GetMapping("/createTaxonomyJsonFiles")
    public String createTaxonomyJsonFiles(@RequestParam String fromFile) {
        readJSONService.createTaxonomyJsonFiles(fromFile);
        return "createTaxonomyJsonFiles called";
    }

    @GetMapping("/createTaxonomyWordpressJsonFiles")
    public String createTaxonomyWordpressJsonFiles(@RequestParam String postType) throws Exception {
        List<WPTaxonomyDTO> WPTaxonomyDTOS = retreiveWordpressInfoService.getWPTaxonomy(postType);
        readJSONService.createJsonFile(WPTaxonomyDTOS, "wordpress_" + postType + ".json");
        return "createTaxonomyWordpressJsonFiles called " + WPTaxonomyDTOS.size();
    }

    @GetMapping("/createPostWordpressJsonFiles")
    public String createPostWordpressJsonFiles() throws Exception {
        List<WPPostDTO> wpPostDTOS = retreiveWordpressInfoService.getWPPost();
        readJSONService.createJsonFile(wpPostDTOS, "wordpress_posts.json");
        return "createTaxonomyWordpressJsonFiles called " + wpPostDTOS.size();
    }

    @GetMapping("/parseArticlesToJson")
    public String parseArticlesToJson(@RequestParam String fromFile) throws Exception {
        List<Post> wpPosts = restClientService.createPostsFromJsonFile(fromFile);
        readJSONService.createJsonFile(wpPosts, "wordpress_posts_from_article.json");
        return "createTaxonomyWordpressJsonFiles called " + wpPosts.size();
    }

    @GetMapping("/createUpdatePostsDate")
    public String createUpdatePostsDate(@RequestParam String wppostfile, @RequestParam String wppostdtofile) throws Exception {
        List<Post> wpPosts = restClientService.createPostsFromJsonFile(wppostfile);
        List<WPPostDTO> wpPostDTOS = restClientService.createWPPostsFromJsonFile(wppostdtofile);
        List<Post> posts = restClientService.createPostToupdatePostsDate(wpPosts, wpPostDTOS);
        readJSONService.createJsonFile(posts, "finalPostsByDate.json");

        return String.format("createUpdatePostsDate called  wpPosts: %s wpPostDTOS: %s posts: %s", wpPosts.size(), wpPostDTOS.size(), posts.size());
    }

    @GetMapping("/updatePostsDate")
    public String createUpdatePostsDate(@RequestParam String fromFile) throws Exception {
        List<Post> wpPosts = restClientService.createPostsFromJsonFile(fromFile);
        restClientService.updatePostsDate(wpPosts);

        return "updatePostsDate called " + wpPosts.size();
    }


    @GetMapping("/createArticlesJsonFile")
    public String createArticlesJsonFile(@RequestParam String fromFile, @RequestParam String toFile) {
        readJSONService.createArticlesJsonFile(fromFile, toFile);
        return "createArticlesJsonFile called";
    }

    @GetMapping("/getAllPosts")
    public String getAllPosts(@RequestParam String postType) throws Exception {
        List<Post> posts = retreiveWordpressInfoService.getAllPosts(postType);
        readJSONService.createJsonFile(posts, "wordpress_" + postType + ".json");
        return "getAllPosts called " + posts.size();
    }

    @GetMapping("/getAllMedia")
    public String getAllMedia() {
        List<Media> mediaList = retreiveWordpressInfoService.getAllMedia();
        readJSONService.createJsonFile(mediaList, "wordpress_media.json");
        return "getAllMedia called " + mediaList.size();
    }

    @GetMapping("/createSourcesEpikaitotita")
    public String createSourcesEpikaitotita() {
        Set<Source> sources = readJSONService.createSourcesEpikaitotita();
        return "createSourcesEpikaitotita called " + sources.size();
    }

    @GetMapping("/createEpikaitotitaPosts")
    public String createEpikaitotitaPosts() {
        List<Article> posts = readJSONService.createEpikaitotitaPosts();
        return "createEpikaitotitaPosts called " + posts.size();
    }

    @GetMapping("/createParemvaseisPosts")
    public String createParemvaseisPosts() {
        List<Article> posts = readJSONService.createParemvaseisPosts();
        return "createParemvaseisPosts called " + posts.size();
    }

    @GetMapping("/createArticlesFromJsonFile")
    public String createArticlesFromJsonFile(@RequestParam String filename) throws JsonProcessingException {
        List<Article> articles = restClientService.createArticlesFromJsonFile(filename);
        restClientService.createArticles(articles);
        return "createArticlesFromJsonFile called " + articles.size();
    }

    @GetMapping("/createEdafiaPosts")
    public String createEdafiaPosts() {
        List<Edafio> edafiaPosts = readJSONService.createEdafiaPosts();
        return "createEdafiaPosts called " + edafiaPosts.size();
    }

    @GetMapping("/createEdafiaFromJsonFile")
    public String createEdafiaFromJsonFile(@RequestParam String filename) throws JsonProcessingException {
        List<Edafio> edafiaFromJsonFile = restClientService.createEdafiaFromJsonFile(filename);
        restClientService.createEdafia(edafiaFromJsonFile);
        return "createEdafiaFromJsonFile called " + edafiaFromJsonFile.size();
    }

    @GetMapping("/updatePosts")
    public String updatePosts(@RequestParam String filename) throws JsonProcessingException {
        List<Post> postsFromJsonFile = restClientService.createPostsFromJsonFile(filename);
        restClientService.updateEdafiaPost(postsFromJsonFile);
        return "postsFromJsonFile called " + postsFromJsonFile.size();
    }

    @GetMapping("/createSynaxaristis")
    public String createSynaxaristis() throws JsonProcessingException {
        List<Article> articles = readJSONService.createSynaxaristisPosts();
        return "createSynaxaristis called " + articles.size();
    }

    @GetMapping("/createSynaxaristisFromJsonFile")
    public String createSynaxaristisFromJsonFile(@RequestParam String filename) throws JsonProcessingException {
        List<Article> postsFromJsonFile = restClientService.createArticlesFromJsonFile(filename);
        restClientService.createArticles(postsFromJsonFile);
        return "postsFromJsonFile called " + postsFromJsonFile.size();
    }

    @GetMapping("/createVideoJsonFile")
    public String createVideoJsonFile(@RequestParam String fromFile, @RequestParam String toFile) throws JsonProcessingException {
        List<Video> videoJsonFile = readJSONService.createVideoJsonFile(fromFile, toFile);
        return "createVideoJsonFile called " + videoJsonFile.size();
    }

    @GetMapping("/createVideoFromJsonFile")
    public String createVideoFromJsonFile(@RequestParam String filename) throws JsonProcessingException {
        List<Video> videos = restClientService.getPostsAccordingToTypeFromJsonFile(filename, Video.class);
        restClientService.createPostsToWordpressAccordingPostType(videos, HTTP_VIDEOS);
        return "create videos called " + videos.size();
    }

    @GetMapping("/createSoundJsonFile")
    public String createSoundJsonFile(@RequestParam String fromFile, @RequestParam String toFile) throws JsonProcessingException {
        List<Sound> videoJsonFile = readJSONService.createSoundJsonFile(fromFile, toFile);
        return "createVideoJsonFile called " + videoJsonFile.size();
    }

}
