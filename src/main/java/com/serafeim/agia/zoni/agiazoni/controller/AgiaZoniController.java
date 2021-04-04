package com.serafeim.agia.zoni.agiazoni.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.serafeim.agia.zoni.agiazoni.model.*;
import com.serafeim.agia.zoni.agiazoni.service.ReadJSONService;
import com.serafeim.agia.zoni.agiazoni.service.RestClientService;
import com.serafeim.agia.zoni.agiazoni.service.RestClientUtil;
import com.serafeim.agia.zoni.agiazoni.service.RetreiveWordpressInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

@RestController
public class AgiaZoniController {


    @Autowired
    public ReadJSONService readJSONService;
    @Autowired
    public RetreiveWordpressInfoService retreiveWordpressInfoService;
    @Autowired
    public RestClientService restClientService;


    @GetMapping("/createTaxonomyJsonFiles")
    public String createTaxonomyJsonFiles(@RequestParam String fromFile) {
        readJSONService.createTaxonomyJsonFilesFromJsonFile(fromFile);
        return "createTaxonomyJsonFiles called";
    }

    @GetMapping("/createTaxonomyWordpressJsonFiles")
    public String createTaxonomyWordpressJsonFiles(@RequestParam String postType) throws Exception {
        List<Taxonomy> WPTaxonomyDTOS = retreiveWordpressInfoService.getWPTaxonomy(postType);
        readJSONService.createJsonFile(WPTaxonomyDTOS, "wordpress_" + postType + ".json");
        return "createTaxonomyWordpressJsonFiles called " + WPTaxonomyDTOS.size();
    }

    @GetMapping("/createPostWordpressJsonFiles")
    public String createPostWordpressJsonFiles(@RequestParam String postType, @RequestParam String filename) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        List<Post> wpPostDTOS = retreiveWordpressInfoService.getWPPost(postType, "9625,9626,9629,9676");
        readJSONService.createJsonFile(wpPostDTOS, "wordpress_" + postType + ".json");
        List<Post> posts = restClientService.createPostsFromWpPostDTOJsonObjectList(wpPostDTOS);
        readJSONService.createJsonFile(posts, filename + ".json");
        return "createPostWordpressJsonFiles called " + wpPostDTOS.size();
    }

    @GetMapping("/parseArticlesToJson")
    public String parseArticlesToJson(@RequestParam String fromFile) {
        List<Post> wpPosts = readJSONService.getPostsAccordingToTypeFromJsonFile(fromFile, Post.class);
        readJSONService.createJsonFile(wpPosts, "wordpress_posts_" + fromFile);
        return "parseArticlesToJson called " + wpPosts.size();
    }

    @GetMapping("/createUpdatePostsDate")
    public String createUpdatePostsDate(@RequestParam String wppostfile, @RequestParam String wppostdtofile) {
        List<Post> wpPosts = readJSONService.getPostsAccordingToTypeFromJsonFile(wppostfile, Post.class);
        List<Post> wpPostDTOS = readJSONService.getPostsAccordingToTypeFromJsonFile(wppostdtofile, Post.class);
        List<Post> posts = restClientService.createPostToUpdatePostsDate(wpPostDTOS, wpPosts);
        readJSONService.createJsonFile(posts, "finalPostsByAge.json");

        return String.format("createUpdatePostsDate called  wpPosts: %s wpPostDTOS: %s posts: %s", wpPosts.size(), wpPostDTOS.size(), posts.size());
    }

    @GetMapping("/createUpdatePostsEnnoima")
    public String createUpdatePostsEnnoima(@RequestParam String postsOneEnnoimaFile, @RequestParam String postsMultiEnnoimaFile) {
        List<Post> postsOneEnnoima = readJSONService.getPostsAccordingToTypeFromJsonFile(postsOneEnnoimaFile, Post.class);
        List<Post> postsMultiEnnoima = readJSONService.getPostsAccordingToTypeFromJsonFile(postsMultiEnnoimaFile, Post.class);
        List<Post> posts = restClientService.createPostToUpdatePostsEnnoima(postsOneEnnoima, postsMultiEnnoima);
        readJSONService.createJsonFile(posts, "finalPostsByEnnoima.json");

        return String.format("createUpdatePostsEnnoima called  postsOneEnnoima: %s postsMultipleEnnoima: %s posts: %s", postsOneEnnoima.size(), postsMultiEnnoima.size(), posts.size());
    }

    @GetMapping("/createUpdateAuthors")
    public String createUpdateAuthors(@RequestParam String authorsOldSiteFile, @RequestParam String authorsWordpressFile) {
        List<Taxonomy> authorsOldSite = readJSONService.createTaxonomyFromJsonFile(authorsOldSiteFile);
        List<Taxonomy> authorsNewSite = readJSONService.createTaxonomyFromJsonFile(authorsWordpressFile);
        List<Taxonomy> authorsFinal = restClientService.createPostToUpdateTaxonomy(authorsOldSite, authorsNewSite);
        readJSONService.createJsonFile(authorsFinal, "finalAuthors.json");

        return String.format("createUpdateAuthors called  authorsOldSite: %s authorsWordpress: %s authorsFinal: %s", authorsOldSite.size(), authorsNewSite.size(), authorsFinal.size());
    }

    @GetMapping("/createUpdateVideoLink")
    public String createUpdateVideoLink(@RequestParam String wppostfile) {
        List<Video> wpPosts = readJSONService.getPostsAccordingToTypeFromJsonFile(wppostfile, Video.class);
        List<Video> posts = restClientService.createVideoToUpdateVideoLink((wpPosts));
        readJSONService.createJsonFile(posts, "finalVideoUpdateVideoLink.json");

        return String.format("createUpdateVideoLink called  wpPosts: %s  posts: %s", wpPosts.size(), posts.size());
    }

    @GetMapping("/updateVideoLink")
    public String updateVideoLink(@RequestParam String fromfile) {
        List<Post> posts = readJSONService.getPostsAccordingToTypeFromJsonFile(fromfile, Post.class);
        restClientService.updateVideoLinks(posts, RestClientUtil.WEBSITE_URL_LOCAL + RestClientUtil.VIDEOS_ENDPOINT);

        return String.format("updateVideoLink called posts: %s", posts.size());
    }

    @GetMapping("/updateEnnoima")
    public String updateEnnoima(@RequestParam String fromfile) {
        List<Post> posts = readJSONService.getPostsAccordingToTypeFromJsonFile(fromfile, Post.class);
        restClientService.updateEnnoima(posts, RestClientUtil.WEBSITE_URL_LOCAL + RestClientUtil.POSTS_ENDPOINT);

        return String.format("updateEnnoima called posts: %s", posts.size());
    }

    @GetMapping("/updatePostsDate")
    public String createUpdatePostsDate(@RequestParam String fromFile) {
        List<Post> posts = readJSONService.getPostsAccordingToTypeFromJsonFile(fromFile, Post.class);
        restClientService.updatePostsDate(posts, RestClientUtil.WEBSITE_URL_LOCAL + RestClientUtil.POSTS_ENDPOINT);

        return "updatePostsDate called " + posts.size();
    }

    @GetMapping("/updateArticleAuthor")
    public String updateArticleAuthor(@RequestParam String fromFile) {
        List<Taxonomy> articleAuthors = readJSONService.createTaxonomyFromJsonFile(fromFile);
        restClientService.updateTaxonomyAuthor(articleAuthors, RestClientUtil.WEBSITE_URL_LOCAL  + RestClientUtil.ARTICLE_AUTHORS_ENDPOINT);

        return "updatePostsDate called " + articleAuthors.size();
    }


    @GetMapping("/createArticlesJsonFile")
    public String createArticlesJsonFile(@RequestParam String fromFile, @RequestParam String toFile) {
        readJSONService.createArticlesJsonFileFromJsonFile(fromFile, toFile);
        return "createArticlesJsonFile called";
    }

    @GetMapping("/getAllMedia")
    public String getAllMedia() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        List<Media> mediaList = retreiveWordpressInfoService.getAllMedia();
        readJSONService.createJsonFile(mediaList, "wordpress_media.json");
        return "getAllMedia called " + mediaList.size();
    }

    @GetMapping("/createSourcesEpikaitotita")
    public String createSourcesEpikaitotita() {
        Set<Source> sources = readJSONService.createSourcesEpikaitotitaFromJsonFile();
        return "createSourcesEpikaitotita called " + sources.size();
    }

    @GetMapping("/createEpikaitotitaPosts")
    public String createEpikaitotitaPosts() {
        List<Post> posts = readJSONService.createEpikaitotitaPostsFromJsonFile();
        return "createEpikaitotitaPosts called " + posts.size();
    }

    @GetMapping("/createParemvaseisPosts")
    public String createParemvaseisPosts() {
        List<Post> posts = readJSONService.createParemvaseisPostsFromJsonFile();
        return "createParemvaseisPosts called " + posts.size();
    }

    @GetMapping("/createArticlesFromJsonFile")
    public String createArticlesFromJsonFile(@RequestParam String filename) throws JsonProcessingException {
        List<Post> articles = readJSONService.getPostsAccordingToTypeFromJsonFile(filename, Post.class);
        restClientService.createPostsToWordpressAccordingPostType(articles, RestClientUtil.WEBSITE_URL_LOCAL + RestClientUtil.POSTS_ENDPOINT);
        return "createArticlesFromJsonFile called " + articles.size();
    }

    @GetMapping("/createEdafiaPosts")
    public String createEdafiaPosts(@RequestParam String filename) {
        List<Edafio> edafiaPosts = readJSONService.createEdafiaPostsFileFromJsonFile(filename);
        return "createEdafiaPosts called " + edafiaPosts.size();
    }

    @GetMapping("/createEdafiaFromJsonFile")
    public String createEdafiaFromJsonFile(@RequestParam String filename) {
        List edafiaFromJsonFile = readJSONService.getPostsAccordingToTypeFromJsonFile(filename, Post.class);
        restClientService.createPostsToWordpressAccordingPostType(edafiaFromJsonFile, RestClientUtil.WEBSITE_URL_LOCAL + RestClientUtil.EDAFIA_ENDPOINT);
        return "createEdafiaFromJsonFile called " + edafiaFromJsonFile.size();
    }

    @GetMapping("/updatePosts")
    public String updatePosts(@RequestParam String filename) {
        List<Post> postsFromJsonFile = readJSONService.getPostsAccordingToTypeFromJsonFile(filename, Post.class);
        restClientService.createPostsToWordpressAccordingPostType(postsFromJsonFile, RestClientUtil.WEBSITE_URL_LOCAL + RestClientUtil.POSTS_ENDPOINT);
        return "postsFromJsonFile called " + postsFromJsonFile.size();
    }

    @GetMapping("/createSynaxaristis")
    public String createSynaxaristis() {
        List<Post> articles = readJSONService.createSynaxaristisPostsFromJson();
        return "createSynaxaristis called " + articles.size();
    }

    @GetMapping("/createSynaxaristisFromJsonFile")
    public String createSynaxaristisFromJsonFile(@RequestParam String filename) throws JsonProcessingException {
        List<Post> postsFromJsonFile = readJSONService.getPostsAccordingToTypeFromJsonFile(filename, Post.class);
        restClientService.createPostsToWordpressAccordingPostType(postsFromJsonFile, RestClientUtil.WEBSITE_URL_LOCAL + RestClientUtil.POSTS_ENDPOINT);
        return "postsFromJsonFile called " + postsFromJsonFile.size();
    }

    @GetMapping("/createVideoJsonFile")
    public String createVideoJsonFile(@RequestParam String fromFile, @RequestParam String toFile) {
        List<Video> videoJsonFile = readJSONService.createVideoJsonFileFromJson(fromFile, toFile);
        return "createVideoJsonFile called " + videoJsonFile.size();
    }

    @GetMapping("/createVideoFromJsonFile")
    public String createVideoFromJsonFile(@RequestParam String filename) {
        List<Video> videos = readJSONService.getPostsAccordingToTypeFromJsonFile(filename, Video.class);
        restClientService.createPostsToWordpressAccordingPostType(videos, RestClientUtil.WEBSITE_URL_LOCAL + RestClientUtil.VIDEOS_ENDPOINT);
        return "create videos called " + videos.size();
    }

    @GetMapping("/createSoundJsonFile")
    public String createSoundJsonFile(@RequestParam String fromFile, @RequestParam String toFile) {
        List<Sound> soundJsonFile = readJSONService.createSoundJsonFileFromJson(fromFile, toFile);
        return "createSoundJsonFile called " + soundJsonFile.size();
    }

    @GetMapping("/createSoundFromJsonFile")
    public String createSoundFromJsonFile(@RequestParam String filename) {
        List<Sound> sounds = readJSONService.getPostsAccordingToTypeFromJsonFile(filename, Sound.class);
        restClientService.createPostsToWordpressAccordingPostType(sounds,RestClientUtil.WEBSITE_URL_LOCAL + RestClientUtil.SOUNDS_ENDPOINT);
        return "createSoundFromJsonFile called " + sounds.size();
    }


    @GetMapping("/createPhotoJsonFile")
    public String createPhotoJsonFile(@RequestParam String fromFile, @RequestParam String toFile) {
        List<Photo> photoJsonFile = readJSONService.createPhotoJsonFileFromJsonFile(fromFile, toFile);
        return "createPhotoJsonFile called " + photoJsonFile.size();
    }

    @GetMapping("/createPhotoFromJsonFile")
    public String createPhotoFromJsonFile(@RequestParam String filename) {
        List<Photo> photos = readJSONService.getPostsAccordingToTypeFromJsonFile(filename, Photo.class);
        restClientService.createPostsToWordpressAccordingPostType(photos, RestClientUtil.WEBSITE_URL_LOCAL + RestClientUtil.PHOTOS_ENDPOINT);

        return "createSoundFromJsonFile called " + photos.size();
    }

}
