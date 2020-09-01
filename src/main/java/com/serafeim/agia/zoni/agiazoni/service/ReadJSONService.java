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
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class ReadJSONService {

//    @PostConstruct
//    public void readJson() throws IOException {
//        createTaxonomyJsonFiles();
//        // TODO put this in another action
////        createArticlesJsonFile();
//
//
//    }

    public void createTaxonomyJsonFiles() {
        Set<Tag> tags = new TreeSet<>();
        Set<Category> categories = new TreeSet<>();
        Set<ArticleAuthor> articleAuthors = new TreeSet<>();
        Set<Source> articleSources = new TreeSet<>();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("articles.json"));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);
            for (JsonNode jsonNode : parser) {
                //TODO eliminate greek tonous
                Set<Tag> articleTags = createArticleTags(jsonNode.path("articleTopics").asText());
                Set<Category> articleCategories = createArticleCategories(jsonNode.path("category").asText());

                ArticleAuthor articleArticleAuthor = createArticleAuthor(jsonNode.path("articleAuthors").asText());


                String sourceText = jsonNode.path("source").asText();
                String normalizedTextSlug = deAccent(StringUtils.trimAllWhitespace(sourceText));
                String normalizedText = sourceText.replaceAll("[\\\\/]", "");
                Source articleSource = new Source(normalizedText, normalizedText, normalizedTextSlug.replaceAll("[\\\\»«()<>0-9+&@#/%?=~'_|!:,.;]", ""));

                System.out.println(jsonNode.path("title").asText());

                tags.addAll(articleTags);
                categories.addAll(articleCategories);
                articleAuthors.add(articleArticleAuthor);
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
    }

    public void createArticlesJsonFile() {
        List<Article> articles = new ArrayList();
        Map<String, Integer> tags = createTaxonomyMap("wordpress_tags.json");
        Map<String, Integer> categories = createTaxonomyMap("wordpress_categories.json");
        Map<String, Integer> articleAuthors = createTaxonomyMap("wordpress_article_authors.json");
        Map<String, Integer> articleSources = createTaxonomyMap("wordpress_sources.json");


        try {
            Reader reader = Files.newBufferedReader(Paths.get("articles.json"));
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
                article.setNumReadings(jsonNode.path("numReadings").asText());
                article.setTemplate("full-width-single-post-az-template.php");
//                article.setAuthor();


                String ennoima = getTextIfEmptyOrNull(jsonNode.path("idees1").asText()) +
                        getTextIfEmptyOrNull(jsonNode.path("idees2").asText()) +
                        getTextIfEmptyOrNull(jsonNode.path("idees3").asText()) +
                        getTextIfEmptyOrNull(jsonNode.path("idees4").asText()) +
                        getTextIfEmptyOrNull(jsonNode.path("idees5").asText()) +
                        getTextIfEmptyOrNull(jsonNode.path("idees6").asText()) +
                        getTextIfEmptyOrNull(jsonNode.path("idees7").asText()) +
                        getTextIfEmptyOrNull(jsonNode.path("idees8").asText()) +
                        getTextIfEmptyOrNull(jsonNode.path("idees9").asText()) +
                        getTextIfEmptyOrNull(jsonNode.path("idees10").asText());

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
                Set<Integer> articleArticleAuthors = getSingleTaxonomyIdsByName(jsonNode.path("articleAuthors").asText(), articleAuthors);
                Set<Integer> articleSource = getSingleTaxonomyIdsByName(jsonNode.path("source").asText(), articleSources);


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

        createJsonFile(articles, "articlesProduction.json");


    }

    public Set<Source> createSourcesEpikaitotita() {
        Set<Source> epikairotitaSources = new TreeSet<>();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("epikairotita.json"));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);
            for (JsonNode jsonNode : parser) {
                //TODO eliminate greek tonous

                String sourceText = jsonNode.path("source").asText();
                String normalizedTextSlug = deAccent(StringUtils.trimAllWhitespace(sourceText));
                String normalizedText = sourceText.replaceAll("[\\\\/]", "");
                Source articleSource = new Source(normalizedText, normalizedText, normalizedTextSlug.replaceAll("[\\\\»«()<>0-9+&@#/%?=~'_|!:,.;]", ""));

                System.out.println(jsonNode.path("title").asText());

                epikairotitaSources.add(articleSource);
            }


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        createJsonFile(epikairotitaSources, "epikairotitaSources.json");

        return epikairotitaSources;
    }

    public List<Article> createEpikaitotitaPosts() {

        List<Article> articles = new ArrayList();
        Map<String, Integer> epikairotitaSources = createTaxonomyMap("wordpress_sources.json");
        Map<String, Integer> epikairotitaMedia = createTaxonomyMapMedia("wordpress_media.json");


        try {
            Reader reader = Files.newBufferedReader(Paths.get("epikairotita.json"));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);
            for (JsonNode jsonNode : parser) {
                Article article = new Article();

                article.setStatus("publish");
//                article.setType("post");
                article.setTitle(jsonNode.path("title").asText());
                article.setDate(jsonNode.path("date").asText());
                article.setExcerpt(jsonNode.path("introtext").asText());
                article.setContent(jsonNode.path("text").asText());
//                article.setNumReadings(jsonNode.path("numReadings").asText());
//                article.setTemplate("full-width-single-post-az-template.php");
//                article.setAuthor();


                Set<Integer> articleSource = getSingleTaxonomyIdsByName(jsonNode.path("source").asText(), epikairotitaSources);


                article.setSources(articleSource);

                Integer imageId = getSingleMediaIdsByName(jsonNode.path("img").asText(), epikairotitaMedia);

                article.setFeatured_media(imageId);

                article.setCategories(Set.of(9625));

                articles.add(article);
            }


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        createJsonFile(articles, "epikairotitaProduction.json");
        return articles;
    }

    public List<Article> createParemvaseisPosts() {

        List<Article> articles = new ArrayList();
        Map<String, Integer> paremvaseisMedia = createTaxonomyMapMedia("wordpress_media.json");


        try {
            Reader reader = Files.newBufferedReader(Paths.get("paremvaseis.json"));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);
            for (JsonNode jsonNode : parser) {
                Article article = new Article();

                article.setStatus("publish");
                article.setTitle(jsonNode.path("title").asText());
                article.setDate(jsonNode.path("date").asText());
                article.setContent(jsonNode.path("text").asText());


                Integer imageId = getSingleMediaIdsByName(jsonNode.path("img").asText(), paremvaseisMedia);

                article.setFeatured_media(imageId);

                article.setCategories(Set.of(9626));

                articles.add(article);
            }


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        createJsonFile(articles, "paremvaseisProduction.json");
        return articles;
    }

    private Set<Integer> getSingleTaxonomyIdsByName(String tag, Map<String, Integer> taxonomiesMap) {
        Set<Integer> taxonomiesIds = new TreeSet<>();

        String taxonomy = removeAccentsAndSpecialChars(tag);

        Integer tagid = taxonomiesMap.get(taxonomy);
        if (tagid != null) {
            taxonomiesIds.add(tagid);
        }
        return taxonomiesIds;
    }

    private Integer getSingleMediaIdsByName(String media, Map<String, Integer> mediaMap) {
        for (String s : mediaMap.keySet()) {
            if (s.contains(media)){
                return mediaMap.get(s);
            }
        }
        return null;
    }

    private String removeAccentsAndSpecialChars(String tag) {
        String normalizedTextTag = deAccent(StringUtils.trimAllWhitespace(tag));
        return normalizedTextTag.replaceAll("[\\\\»«()<>0-9+&@#/%?=~'_|!:,.;]", "");
    }

    private ArticleAuthor createArticleAuthor(String articleAuthors) {
        String normalizedTextSlug = deAccent(StringUtils.trimAllWhitespace(articleAuthors));
        String normalizedText = articleAuthors.replaceAll("[\\\\/]", "");
        return new ArticleAuthor(normalizedText, normalizedText, normalizedTextSlug.replaceAll("[\\\\»«()<>0-9+&@#/%?=~'_|!:,.;]", ""));
    }

    private String getTextIfEmptyOrNull(String text) {
        return text.equals("null") || StringUtils.isEmpty(text) ? "" : text + " ";
    }

    private Set<Integer> getTaxonomyIdsByName(String tags, Map<String, Integer> taxonomiesMap) {
        String[] taxonomiesTextList = tags.split("\\s*,\\s*");
        Set<Integer> taxonomiesIds = new TreeSet<>();

        for (String taxonomy : taxonomiesTextList) {
            String normalizeTaxonomy = removeAccentsAndSpecialChars(taxonomy);
            Integer tagid = taxonomiesMap.get(normalizeTaxonomy);
            if (tagid != null) {
                taxonomiesIds.add(tagid);
            }
        }
        return taxonomiesIds;
    }

    private Map<String, Integer> createTaxonomyMap(String filename) {
        Map<String, Integer> result = new HashMap();

        try {
            Reader reader = Files.newBufferedReader(Paths.get(filename));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);
            for (JsonNode jsonNode : parser) {
                Integer id = jsonNode.path("id").asInt();
                String name = removeAccentsAndSpecialChars(jsonNode.path("name").asText());
                result.put(name, id);
            }


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Map<String, Integer> createTaxonomyMapMedia(String filename) {
        Map<String, Integer> result = new HashMap();

        try {
            Reader reader = Files.newBufferedReader(Paths.get(filename));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);
            for (JsonNode jsonNode : parser) {
                Integer id = jsonNode.path("id").asInt();
                String source_url = jsonNode.path("source_url").asText();
                result.put(source_url, id);
            }


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public void createJsonFile(Collection contents, String filename) {
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
        Set<ArticleAuthor> articleAuthors = new TreeSet<>();
        if (!authors.equals("null")) {
            String[] articleAuthorsText = authors.split("\\s*,\\s*");

            for (String articleAuthorName : articleAuthorsText) {
                String trimArticleAuthorName = StringUtils.trimWhitespace(articleAuthorName);
                articleAuthors.add(new ArticleAuthor(trimArticleAuthorName, trimArticleAuthorName, deAccent(StringUtils.trimAllWhitespace(trimArticleAuthorName))));
            }
        }
        return articleAuthors;
    }

    private Set<Category> createArticleCategories(String category) {
        Set<Category> categories = new TreeSet<>();
        if (!category.equals("null")) {
            String[] articleCategories = category.split("\\s*,\\s*");

            for (String categoryName : articleCategories) {
                String trimCategoryName = StringUtils.trimWhitespace(categoryName);
                //TODO define the parent
                categories.add(new Category(trimCategoryName, trimCategoryName, deAccent(StringUtils.trimAllWhitespace(trimCategoryName)), 0));
            }
        }
        return categories;
    }

    private Set<Tag> createArticleTags(String articleTopics) {
        Set<Tag> tags = new TreeSet<>();
        if (!articleTopics.equals("null")) {
            String[] tagsText = articleTopics.split("\\s*,\\s*");

            for (String tagName : tagsText) {
                String trimTagName = StringUtils.trimWhitespace(tagName);
                tags.add(new Tag(trimTagName, trimTagName, deAccent(StringUtils.trimAllWhitespace(trimTagName))));
            }
        }
        return tags;
    }

    public String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCOMBINING_DIACRITICAL_MARKS}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}
