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

    @Autowired
    public ReadJSONService readJSONService;
    @Autowired
    public RetreiveWordpressInfoService retreiveWordpressInfoService;
    @Autowired
    public RestClientService restClientService;


    @GetMapping("/createTaxonomyJsonFiles")
    public String createTaxonomyJsonFiles() {
        readJSONService.createTaxonomyJsonFiles();
        return "createTaxonomyJsonFiles called";
    }

    @GetMapping("/createArticlesJsonFile")
    public String createArticlesJsonFile() {
        readJSONService.createArticlesJsonFile();
        return "createArticlesJsonFile called";
    }

    @GetMapping("/getAllPosts")
    public String getAllPosts(@RequestParam String type) throws Exception {
        List<Post> posts = retreiveWordpressInfoService.getAllPosts(type);
        readJSONService.createJsonFile(posts, "wordpress_" + type + ".json");
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
        restClientService.updatePost(postsFromJsonFile);
        return "postsFromJsonFile called " + postsFromJsonFile.size();
    }


}
