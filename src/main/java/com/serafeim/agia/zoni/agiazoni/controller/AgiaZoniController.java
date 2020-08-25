package com.serafeim.agia.zoni.agiazoni.controller;

import com.serafeim.agia.zoni.agiazoni.service.ReadJSONService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgiaZoniController {

    @Autowired
    public ReadJSONService readJSONService;


    @GetMapping("/createTaxonomyJsonFiles")
    public String createTaxonomyJsonFiles(){
        readJSONService.createTaxonomyJsonFiles();
        return "createTaxonomyJsonFiles called";
    }

    @GetMapping("/createArticlesJsonFile")
    public String createArticlesJsonFile(){
        return "createArticlesJsonFile called";
    }

}
