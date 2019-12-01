package com.serafeim.agia.zoni.agiazoni.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serafeim.agia.zoni.agiazoni.model.Article;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class ReadJSONService {

    @PostConstruct
    public void readJson() throws IOException {
        ArrayList articles = new ArrayList();

        ObjectMapper mapper;
        try {
            Reader reader = Files.newBufferedReader(Paths.get("sample.json"));
            mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);
            for (JsonNode jsonNode : parser) {
                Article article = new Article();
                article.setStatus("publish");
                article.setType("post");
                article.setTitle(jsonNode.path("title").asText());
                System.out.println(jsonNode.path("title").asText());
                article.setContent(jsonNode.path("maintext").asText());
                article.setSource(jsonNode.path("source").asText());
                article.setEnnoima(jsonNode.path("idees1").asText() + " " + jsonNode.path("idees2").asText() + " " + jsonNode.path("idees3").asText() + " " + jsonNode.path("idees4").asText() + " " + jsonNode.path("idees5").asText() + " " + jsonNode.path("idees6").asText() + " " + jsonNode.path("idees7").asText() + " " + jsonNode.path("idees8").asText() + " " + jsonNode.path("idees9").asText() + " " + jsonNode.path("idees10").asText());
                article.setTags(Arrays.asList(jsonNode.path("articleTopics").asText().split("\\s*,\\s*")));
                article.setCategories(Arrays.asList(jsonNode.path("category").asText().split("\\s*,\\s*")));
                article.setArticle_author(Arrays.asList(jsonNode.path("articleAuthors").asText().split("\\s*,\\s*")));
                articles.add(article);
            }


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("testarticle.json"));
            mapper = new ObjectMapper();
            writer.write(mapper.writeValueAsString(articles));
            writer.close();
        } catch (Exception var8) {
            var8.printStackTrace();
        }

    }
}
