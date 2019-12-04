package com.serafeim.agia.zoni.agiazoni.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serafeim.agia.zoni.agiazoni.model.Article;
import com.serafeim.agia.zoni.agiazoni.model.ArticleAuthor;
import com.serafeim.agia.zoni.agiazoni.model.Category;
import com.serafeim.agia.zoni.agiazoni.model.Tag;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ReadJSONService {

    @PostConstruct
    public void readJson() throws IOException {
        List<Article> articles = new ArrayList();
        Set<Tag> tags = new HashSet<>();
        Set<Category> categories = new HashSet<>();
        Set<ArticleAuthor> articleAuthors = new HashSet<>();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("sample.json"));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);
            for (JsonNode jsonNode : parser) {
                Article article = new Article();

                Set<Tag> articleTags = createArticleTags(jsonNode.path("articleTopics").asText(), article);
                Set<Category> articleCategories = createArticleCategories(jsonNode.path("category").asText(), article);
                Set<ArticleAuthor> articleArticleAuthors = createArticleAuthors(jsonNode.path("articleAuthors").asText(), article);

                article.setStatus("publish");
                article.setType("post");
                article.setTitle(jsonNode.path("title").asText());
                article.setContent(jsonNode.path("maintext").asText());
                article.setSource(jsonNode.path("source").asText());

                String ennoima = jsonNode.path("idees1").asText() +
                        " " + jsonNode.path("idees2").asText() +
                        " " + jsonNode.path("idees3").asText() +
                        " " + jsonNode.path("idees4").asText() +
                        " " + jsonNode.path("idees5").asText() +
                        " " + jsonNode.path("idees6").asText() +
                        " " + jsonNode.path("idees7").asText() +
                        " " + jsonNode.path("idees8").asText() +
                        " " + jsonNode.path("idees9").asText() +
                        " " + jsonNode.path("idees10").asText();

                article.setEnnoima(ennoima.trim());

                System.out.println(jsonNode.path("title").asText());

                tags.addAll(articleTags);
                categories.addAll(articleCategories);
                articleAuthors.addAll(articleArticleAuthors);
                articles.add(article);
            }


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        createJsonFile(articles, "articles.json");
        createJsonFile(tags, "tags.json");
        createJsonFile(categories, "categories.json");
        createJsonFile(articleAuthors, "articleAuthors.json");

    }

    private void createJsonFile(Collection contents, String filename) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename));
            ObjectMapper mapper = new ObjectMapper();
            writer.write(mapper.writeValueAsString(contents));
            System.out.println(filename + "  " + contents.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Set<ArticleAuthor> createArticleAuthors(String authors, Article article) {
        List<String> articleAuthorsText = Arrays.asList(authors.split("\\s*,\\s*"));
        article.setArticle_author(new HashSet<>(articleAuthorsText));
        Set<ArticleAuthor> articleAuthors = new HashSet<>();

        for (String articleAuthorName : articleAuthorsText) {
            articleAuthors.add(new ArticleAuthor(articleAuthorName, articleAuthorName, StringUtils.trimAllWhitespace(articleAuthorName), articleAuthorName));
        }
        return articleAuthors;
    }

    private Set<Category> createArticleCategories(String category, Article article) {
        List<String> articleCategories = Arrays.asList(category.split("\\s*,\\s*"));
        article.setCategories(new HashSet<>(articleCategories));
        Set<Category> categories = new HashSet<>();

        for (String categoryName : articleCategories) {
            categories.add(new Category(categoryName, categoryName, StringUtils.trimAllWhitespace(categoryName), categoryName));
        }
        return categories;
    }

    private Set<Tag> createArticleTags(String articleTopics, Article article) {
        List<String> tagsText = Arrays.asList(articleTopics.split("\\s*,\\s*"));
        article.setTags(new HashSet<>(tagsText));
        Set<Tag> tags = new HashSet<>();

        for (String tagName : tagsText) {
            tags.add(new Tag(tagName, tagName, StringUtils.trimAllWhitespace(tagName), tagName));
        }
        return tags;
    }
}
