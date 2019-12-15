package com.serafeim.agia.zoni.agiazoni.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serafeim.agia.zoni.agiazoni.model.*;
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
//        createTaxonomyJsonFiles();
        // TODO put this in another action
       // createArticlesJsonFile();


    }

    public String display(String name){
        return name;
    }

    public String createTaxonomyJsonFiles() {
        Set<Tag> tags = new TreeSet<>();
        Set<Category> categories = new TreeSet<>();
        Set<ArticleAuthor> articleAuthors = new TreeSet<>();
        Set<Source> articleSources = new TreeSet<>();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("sample.json"));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);
            for (JsonNode jsonNode : parser) {
                Set<Tag> articleTags = createArticleTags(jsonNode.path("articleTopics").asText());
                Set<Category> articleCategories = createArticleCategories(jsonNode.path("category").asText());
                Set<ArticleAuthor> articleArticleAuthors = createArticleAuthors(jsonNode.path("articleAuthors").asText());


                String sourceText = jsonNode.path("source").asText();
                Source articleSource = new Source(sourceText, sourceText, StringUtils.trimAllWhitespace(sourceText));

                System.out.println(jsonNode.path("title").asText());

                tags.addAll(articleTags);
                categories.addAll(articleCategories);
                articleAuthors.addAll(articleArticleAuthors);
                articleSources.add(articleSource);
            }


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        createJsonFile(tags, "tags.json");
        createJsonFile(categories, "categories.json");
        createJsonFile(articleAuthors, "articleAuthors.json");
        createJsonFile(articleSources, "articleSources.json");

        return "Article json files are created!!";
    }

    public String createArticlesJsonFile() {
        List<Article> articles = new ArrayList();
        Map<String, Integer> tags = createTaxonomyMap("wordpress_tags.json");
        Map<String, Integer> categories = createTaxonomyMap("wordpress_categories.json");
        Map<String, Integer> articleAuthors = createTaxonomyMap("wordpress_article_authors.json");
        Map<String, Integer> articleSources = createTaxonomyMap("wordpress_sources.json");


        try {
            Reader reader = Files.newBufferedReader(Paths.get("sample.json"));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);
            for (JsonNode jsonNode : parser) {
                Article article = new Article();

                article.setStatus("publish");
//                article.setType("post");
                article.setTitle(jsonNode.path("title").asText());
                article.setDate(jsonNode.path("date").asText());
                article.setExcerpt(jsonNode.path("introtext").asText());
                article.setContent(jsonNode.path("maintext").asText());
//                article.setAuthor();

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


                Set<Integer> articleTags = getTaxonomyIdsByName(jsonNode.path("articleTopics").asText(), tags);
                // There is a case that we have only one tag in the 'topic' (older articles)
                if (jsonNode.path("topic").asText() != null && !StringUtils.isEmpty(jsonNode.path("topic").asText())) {
                    Integer tagid = tags.get(jsonNode.path("topic").asText());
                    if (tagid != null) {
                        articleTags.add(tagid);
                    }
                }
                Set<Integer> articleCategories = getTaxonomyIdsByName(jsonNode.path("category").asText(), categories);
                Set<Integer> articleArticleAuthors = getTaxonomyIdsByName(jsonNode.path("articleAuthors").asText(), articleAuthors);
                Set<Integer> articleSource = getTaxonomyIdsByName(jsonNode.path("source").asText(), articleSources);


                article.setTags(articleTags);
                article.setCategories(articleCategories);
                article.setArticle_authors(articleArticleAuthors);
                article.setSources(articleSource);

                articles.add(article);
            }


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        createJsonFile(articles, "articles.json");

        return "Articles json file is created!!";
    }

    private Set<Integer> getTaxonomyIdsByName(String tags, Map<String, Integer> taxonomiesMap) {
        List<String> taxonomiesTextList = Arrays.asList(tags.split("\\s*,\\s*"));
        Set<Integer> taxonomiesIds = new TreeSet<>();

        for (String taxonomy : taxonomiesTextList) {
            Integer tagid = taxonomiesMap.get(taxonomy);
            if (tagid != null) {
                taxonomiesIds.add(tagid);
            }
        }
        return taxonomiesIds;
    }

    private Map<String, Integer> createTaxonomyMap(String filename) {
        Map<String, Integer> result = new HashMap<String, Integer>();

        try {
            Reader reader = Files.newBufferedReader(Paths.get(filename));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);
            for (JsonNode jsonNode : parser) {
                Integer id = jsonNode.path("id").asInt();
                String name = jsonNode.path("name").asText();
                result.put(name, id);
            }


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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

    private Set<ArticleAuthor> createArticleAuthors(String authors) {
        List<String> articleAuthorsText = Arrays.asList(authors.split("\\s*,\\s*"));
//        article.setArticle_author(new TreeSet<>(articleAuthorsText));
        Set<ArticleAuthor> articleAuthors = new TreeSet<>();

        for (String articleAuthorName : articleAuthorsText) {
            articleAuthors.add(new ArticleAuthor(articleAuthorName, articleAuthorName, StringUtils.trimAllWhitespace(articleAuthorName)));
        }
        return articleAuthors;
    }

    private Set<Category> createArticleCategories(String category) {
        List<String> articleCategories = Arrays.asList(category.split("\\s*,\\s*"));
//        article.setCategories(new TreeSet<>(articleCategories));
        Set<Category> categories = new TreeSet<>();

        for (String categoryName : articleCategories) {
            //TODO define the parent
            categories.add(new Category(categoryName, categoryName, StringUtils.trimAllWhitespace(categoryName), 0));
        }
        return categories;
    }

    private Set<Tag> createArticleTags(String articleTopics) {
        List<String> tagsText = Arrays.asList(articleTopics.split("\\s*,\\s*"));
//        article.setTags(new TreeSet<>(tagsText));
        Set<Tag> tags = new TreeSet<>();

        for (String tagName : tagsText) {
            tags.add(new Tag(tagName, tagName, StringUtils.trimAllWhitespace(tagName)));
        }
        return tags;
    }
}
