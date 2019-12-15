package com.serafeim.agia.zoni.agiazoni.controller;

import com.serafeim.agia.zoni.agiazoni.service.ReadJSONService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WordpressController {

    @Autowired
    ReadJSONService readJSONService;

    @GetMapping({"/", "/index"})
    public String index(Model model, @RequestParam(value = "name", required = false, defaultValue = "Index") String name) {
        model.addAttribute("name", name);

        if (StringUtils.endsWithIgnoreCase(name, "taxonomies")) {
            String value = readJSONService.createTaxonomyJsonFiles();
            model.addAttribute("value", value);
        } else if (StringUtils.endsWithIgnoreCase(name, "article")) {
            String value = readJSONService.createArticlesJsonFile();
            model.addAttribute("value", value);
        } else {
            model.addAttribute("value","Start!!");
        }
        return "index";
    }

}
