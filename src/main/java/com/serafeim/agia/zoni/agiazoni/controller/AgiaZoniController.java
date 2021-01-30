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
    public static final String HTTP_SOUNDS = "http://localhost:8081/wp-json/wp/v2/sound_posts";
    public static final String HTTP_PHOTO = "http://localhost:8081/wp-json/wp/v2/photo_posts";
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
    public String createPostWordpressJsonFiles(@RequestParam String postType, @RequestParam String filename) {
        List<WPPostDTO> wpPostDTOS = retreiveWordpressInfoService.getWPPost(postType, "9625,9626,9629");
        readJSONService.createJsonFile(wpPostDTOS, "wordpress_" + postType + ".json");
        List<Post> posts = restClientService.createPostsFromWpPostDTOJsonObjectList(wpPostDTOS);
        readJSONService.createJsonFile(posts, filename + ".json");
        return "createPostWordpressJsonFiles called " + wpPostDTOS.size();
    }

    @GetMapping("/parseArticlesToJson")
    public String parseArticlesToJson(@RequestParam String fromFile) {
        List<Post> wpPosts = restClientService.createPostsFromJsonFile(fromFile);
        readJSONService.createJsonFile(wpPosts, "wordpress_posts_" + fromFile);
        return "createTaxonomyWordpressJsonFiles called " + wpPosts.size();
    }

    @GetMapping("/createUpdatePostsDate")
    public String createUpdatePostsDate(@RequestParam String wppostfile, @RequestParam String wppostdtofile) {
        List<Post> wpPosts = restClientService.createPostsFromJsonFile(wppostfile);
        List<WPPostDTO> wpPostDTOS = restClientService.createWPPostsFromJsonFile(wppostdtofile);
        List<Post> posts = restClientService.createPostToupdatePostsDate(wpPosts, wpPostDTOS);
        readJSONService.createJsonFile(posts, "finalPostsByDate.json");

        return String.format("createUpdatePostsDate called  wpPosts: %s wpPostDTOS: %s posts: %s", wpPosts.size(), wpPostDTOS.size(), posts.size());
    }

    @GetMapping("/createUpdatePostsEnnoima")
    public String createUpdatePostsEnnoima(@RequestParam String postsOneEnnoimaFile, @RequestParam String postsMultiEnnoimaFile) {
        List<Post> postsOneEnnoima = restClientService.createPostsFromJsonFile(postsOneEnnoimaFile);
        List<Post> postsMultiEnnoima = restClientService.createPostsFromJsonFile(postsMultiEnnoimaFile);
        List<Post> posts = restClientService.createPostToupdatePostsEnnoima(postsOneEnnoima, postsMultiEnnoima);
        readJSONService.createJsonFile(posts, "finalPostsByEnnoima.json");

        return String.format("createUpdatePostsEnnoima called  postsOneEnnoima: %s postsMultipleEnnoima: %s posts: %s", postsOneEnnoima.size(), postsMultiEnnoima.size(), posts.size());
    }

    @GetMapping("/createUpdateVideoLink")
    public String createUpdateVideoLink(@RequestParam String wppostfile) {
        List<WPPostDTO> wpPosts = restClientService.createWPPostsFromJsonFile(wppostfile);
        List<Post> posts = restClientService.createVideoToUpdateVideoLink(wpPosts);
        readJSONService.createJsonFile(posts, "finalVideoUpdateVideoLink.json");

        return String.format("createUpdateVideoLink called  wpPosts: %s  posts: %s", wpPosts.size(), posts.size());
    }

    @GetMapping("/updateVideoLink")
    public String updateVideoLink(@RequestParam String fromfile) {
        List<Post> posts = restClientService.createPostsFromJsonFile(fromfile);
        restClientService.updateVideoLinks(posts);

        return String.format("updateVideoLink called posts: %s", posts.size());
    }
    @GetMapping("/updateEnnoima")
    public String updateEnnoima(@RequestParam String fromfile) {
        List<Post> posts = restClientService.createPostsFromJsonFile(fromfile);
        restClientService.updateEnnoima(posts);

        return String.format("updateEnnoima called posts: %s", posts.size());
    }

    @GetMapping("/updatePostsDate")
    public String createUpdatePostsDate(@RequestParam String fromFile) {
        List<Post> posts = restClientService.createPostsFromJsonFile(fromFile);
        restClientService.updatePostsDate(posts);

        return "updatePostsDate called " + posts.size();
    }


    @GetMapping("/createArticlesJsonFile")
    public String createArticlesJsonFile(@RequestParam String fromFile, @RequestParam String toFile) {
        readJSONService.createArticlesJsonFile(fromFile, toFile);
        return "createArticlesJsonFile called";
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
    public String createEdafiaFromJsonFile(@RequestParam String filename) {
        List<Edafio> edafiaFromJsonFile = restClientService.createEdafiaFromJsonFile(filename);
        restClientService.createEdafia(edafiaFromJsonFile);
        return "createEdafiaFromJsonFile called " + edafiaFromJsonFile.size();
    }

    @GetMapping("/updatePosts")
    public String updatePosts(@RequestParam String filename) {
        List<Post> postsFromJsonFile = restClientService.createPostsFromJsonFile(filename);
        restClientService.updateEdafiaPost(postsFromJsonFile);
        return "postsFromJsonFile called " + postsFromJsonFile.size();
    }

    @GetMapping("/createSynaxaristis")
    public String createSynaxaristis() {
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
    public String createVideoJsonFile(@RequestParam String fromFile, @RequestParam String toFile) {
        List<Video> videoJsonFile = readJSONService.createVideoJsonFile(fromFile, toFile);
        return "createVideoJsonFile called " + videoJsonFile.size();
    }

    @GetMapping("/createVideoFromJsonFile")
    public String createVideoFromJsonFile(@RequestParam String filename) {
        List<Video> videos = restClientService.getPostsAccordingToTypeFromJsonFile(filename, Video.class);
        restClientService.createPostsToWordpressAccordingPostType(videos, HTTP_VIDEOS);
        return "create videos called " + videos.size();
    }

    @GetMapping("/createSoundJsonFile")
    public String createSoundJsonFile(@RequestParam String fromFile, @RequestParam String toFile) {
        List<Sound> soundJsonFile = readJSONService.createSoundJsonFile(fromFile, toFile);
        return "createSoundJsonFile called " + soundJsonFile.size();
    }

    @GetMapping("/createSoundFromJsonFile")
    public String createSoundFromJsonFile(@RequestParam String filename) {
        List<Sound> sounds = restClientService.getPostsAccordingToTypeFromJsonFile(filename, Sound.class);
        restClientService.createPostsToWordpressAccordingPostType(sounds, HTTP_SOUNDS);
        return "createSoundFromJsonFile called " + sounds.size();
    }


    @GetMapping("/createPhotoJsonFile")
    public String createPhotoJsonFile(@RequestParam String fromFile, @RequestParam String toFile) {
        List<Photo> photoJsonFile = readJSONService.createPhotoJsonFile(fromFile, toFile);
        return "createPhotoJsonFile called " + photoJsonFile.size();
    }

    @GetMapping("/createPhotoFromJsonFile")
    public String createPhotoFromJsonFile(@RequestParam String filename) {
        List<Photo> photos = restClientService.getPostsAccordingToTypeFromJsonFile(filename, Photo.class);
        restClientService.createPostsToWordpressAccordingPostType(photos, HTTP_PHOTO);

        return "createSoundFromJsonFile called " + photos.size();
    }

}
