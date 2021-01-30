package com.serafeim.agia.zoni.agiazoni.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serafeim.agia.zoni.agiazoni.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReadJSONService {

    public static final String YOUTUBE_COM_EMBED_OLD = "www.youtube.com/embed";
    public static final String YOUTUBE_COM_EMBED_NEW = "youtu.be";

    public void createTaxonomyJsonFiles(String filename) {
        Set<Tag> tags = new TreeSet<>();
        Set<Category> categories = new TreeSet<>();
        Set<ArticleAuthor> articleAuthors = new TreeSet<>();
        Set<Source> articleSources = new TreeSet<>();

        try {
            Reader reader = Files.newBufferedReader(Paths.get(filename));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);
            for (JsonNode jsonNode : parser) {
                //TODO eliminate greek tonous
                Set<Tag> articleTags = createArticleTags(jsonNode.path("articleTopics").asText());
                Set<Category> articleCategories = createArticleCategories(jsonNode.path("category").asText());

                ArticleAuthor articleArticleAuthor = createArticleAuthor(jsonNode.path("articleAuthors").asText());


                String sourceText = jsonNode.path("source").asText();
                String normalizedTextSlug = deAccent(StringUtils.deleteWhitespace(sourceText));
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

    public void createArticlesJsonFile(String fromFile, String toFile) {
        List<Article> articles = new ArrayList();
        Map<String, Integer> tags = createTaxonomyMap("wordpress_tags.json");
        Map<String, Integer> categories = createTaxonomyMap("wordpress_categories.json");
        Map<String, Integer> articleAuthors = createTaxonomyMap("wordpress_article_authors.json");
        Map<String, Integer> articleSources = createTaxonomyMap("wordpress_sources.json");


        try {
            Reader reader = Files.newBufferedReader(Paths.get(fromFile));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);
            for (JsonNode jsonNode : parser) {
                Article article = new Article();

                article.setStatus("publish");
                article.setTitle(jsonNode.path("title").asText());
                article.setDate(jsonNode.path("date").asText());
                article.setExcerpt(jsonNode.path("introtext").asText());
                article.setContent(jsonNode.path("maintext").asText());
                article.setNumReadings(jsonNode.path("numReadings").asText());
                article.setTemplate("full-width-single-post-az-template.php");


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
                article.setArticleAuthors(articleArticleAuthors);
                article.setSources(articleSource);

                articles.add(article);
            }


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        createJsonFile(articles, toFile);
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
                String normalizedTextSlug = deAccent(StringUtils.deleteWhitespace(sourceText));
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
                article.setTitle(jsonNode.path("title").asText());
                article.setDate(jsonNode.path("date").asText());
                article.setExcerpt(jsonNode.path("introtext").asText());
                article.setContent(jsonNode.path("text").asText());


                Set<Integer> articleSource = getSingleTaxonomyIdsByName(jsonNode.path("source").asText(), epikairotitaSources);


                article.setSources(articleSource);

                Integer imageId = getSingleMediaIdsByName(jsonNode.path("img").asText(), epikairotitaMedia);

                article.setFeaturedMedia(imageId);

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

                article.setFeaturedMedia(imageId);

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

    public List<Edafio> createEdafiaPosts() {
        List<Edafio> edafiaList = new ArrayList();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("ag_content.json"));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);
            for (JsonNode jsonNode : parser) {
                Edafio edafio = new Edafio();

                edafio.setStatus("private");
                edafio.setTitle(jsonNode.path("contentID").asText());
                edafio.setContentid(jsonNode.path("contentID").asText());
                edafio.setBookid(jsonNode.path("bookID").asText());
                edafio.setBook(jsonNode.path("book").asText());
                edafio.setArxaio(jsonNode.path("arxaio").asText());
                edafio.setMetafrasi(jsonNode.path("metafrasi").asText());
                edafio.setKef(jsonNode.path("kef").asText());
                edafio.setEdafio(jsonNode.path("edafio").asText());
                edafio.setPart(jsonNode.path("part").asText());
                edafio.setAgbookname(jsonNode.path("ag_bookname").asText());
                edafio.setAgcategory(jsonNode.path("cat").asText());

                edafiaList.add(edafio);
            }


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        createJsonFile(edafiaList, "edaiaProduction.json");
        return edafiaList;
    }

    public List<Article> createSynaxaristisPosts() {

        List<Article> articles = new ArrayList();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("synaxaristis.json"));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);
            for (JsonNode jsonNode : parser) {
                Article article = new Article();

                article.setStatus("publish");
                article.setTitle(jsonNode.path("introtext").asText());
                String dateString = jsonNode.path("date").asText();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);


                String date = formatter2.format(formatter.parse(dateString));
                Calendar c = Calendar.getInstance();
                c.setTime(formatter.parse(date));
                c.add(Calendar.YEAR, 1);
//                sdf.format(c.getTime());
                article.setDate(formatter2.format(c.getTime()));
                article.setContent(jsonNode.path("text").asText());
                article.setCategories(Set.of(9629));
                article.setCommentStatus("closed");

                articles.add(article);
            }


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        createJsonFile(articles, "synaxaristisProduction.json");
        return articles;
    }

    public List<Video> createVideoJsonFile(String fromFile, String toFile) {

        List<Video> videoList = new ArrayList();
        Map<String, Integer> mapMedia = createTaxonomyMapMedia("wordpress_media.json");


        try {
            Reader reader = Files.newBufferedReader(Paths.get(fromFile));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);
            for (JsonNode jsonNode : parser) {
                String extractUrl = extractEmbedVideoUrl(jsonNode.path("src").asText());
                if (!StringUtils.isEmpty(extractUrl)) {
                    Video video = new Video();

                    video.setStatus("publish");
                    video.setTitle(jsonNode.path("title").asText());
                    video.setDate(jsonNode.path("date_published").asText());
                    video.setEditor(jsonNode.path("txt").asText());
                    video.setVideoLink(extractUrl);
                    if (!StringUtils.isEmpty(jsonNode.path("img").asText())) {
                        String imagestr = jsonNode.path("img").asText(); //StringUtils.substringBefore(jsonNode.path("img").asText(), ".");
                        video.setImagePreview(getSingleMediaIdsByName(imagestr, mapMedia));
                    }
                    video.setNumReadings(jsonNode.path("num_hits").asText());
                    video.setCommentStatus("closed");
                    video.setDuration(jsonNode.path("duration").asText());

                    videoList.add(video);
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        createJsonFile(videoList, toFile);

        return videoList;
    }

    public List<Sound> createSoundJsonFile(String fromFile, String toFile) {

        List<Sound> soundList = new ArrayList();
        Map<String, Integer> mapMedia = createTaxonomyMapMedia("wordpress_media.json");

        try {
            Reader reader = Files.newBufferedReader(Paths.get(fromFile));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);
            for (JsonNode jsonNode : parser) {
                String file = jsonNode.path("file").asText();
                String embedCode = jsonNode.path("embed_code").asText();


                Sound sound = new Sound();
                sound.setStatus("publish");
                sound.setTitle(jsonNode.path("title").asText());
                sound.setDate(jsonNode.path("date_published").asText());
                sound.setEditor(jsonNode.path("txt").asText());
                sound.setImagePreview(getSingleMediaIdsByName(jsonNode.path("img").asText(),mapMedia));
                sound.setNumReadings(jsonNode.path("num_hits").asText());
                sound.setCommentStatus("closed");
                sound.setDuration(jsonNode.path("duration").asText());


//               TODO currently we should use the iframe since the soundlinks are not correct
                if (!StringUtils.isEmpty(embedCode)) {
//                    String embedCodeUrl = extractEmbedSoundUrl(embedCode);
//                    sound.setSoundLink(embedCodeUrl);
                    sound.setIframe(jsonNode.path("embed_code").asText());
                    soundList.add(sound);
                } else if (!StringUtils.isEmpty(file)) {
//                    sound.setFile(file);
//                    soundList.add(sound);
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        createJsonFile(soundList, toFile);

        return soundList;
    }

    private String extractEmbedSoundUrl(String embedCode) {
        String url = null;

        if (StringUtils.startsWithIgnoreCase(embedCode, "<iframe")) {

            Matcher matcher = Pattern.compile("src=\"([^\"]+)\"").matcher(embedCode);
            boolean found = matcher.find();
            if (found) {
                url = matcher.group(1);
            }
        }

        return url;
    }

    private String extractEmbedVideoUrl(String src) {
        String url = null;

        if (StringUtils.startsWithIgnoreCase(src, "<iframe")) {

            Matcher matcher = Pattern.compile("src=\\\\\"([^\"]+)\"").matcher(src);
            boolean found = matcher.find();
            if (found) {
                // when we have https://www.youtube.com/embed/wiVQ9Ik16qI\\ it should become  https://youtu.be/wiVQ9Ik16qI
                url = matcher.group(1);
                if (StringUtils.contains(src, YOUTUBE_COM_EMBED_OLD)) {
                    url = url.replace(YOUTUBE_COM_EMBED_OLD, YOUTUBE_COM_EMBED_NEW);
                }
            }
        }

        return url;
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
            if (s.contains(media) || StringUtils.substringBefore(s, ".jpg").contains(StringUtils.substringBefore(media,".jpg"))) {
                return mediaMap.get(s);
            }
        }
        return null;
    }

    private String removeAccentsAndSpecialChars(String tag) {
        String normalizedTextTag = deAccent(StringUtils.deleteWhitespace(tag));
        return normalizedTextTag.replaceAll("[\\\\»«()<>0-9+&@#/%?=~'_|!:,.;]", "");
    }

    private ArticleAuthor createArticleAuthor(String articleAuthors) {
        String normalizedTextSlug = deAccent(StringUtils.deleteWhitespace(articleAuthors));
        String normalizedText = articleAuthors.replaceAll("[\\\\/]", "");
        return new ArticleAuthor(normalizedText, normalizedText, normalizedTextSlug.replaceAll("[\\\\»«()<>0-9+&@#/%?=~'_|!:,.;]", ""));
    }

    public static String getTextIfEmptyOrNull(String text) {
        return  StringUtils.isEmpty(text) || text.equals("null") ? "" : text + " ";
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
                String trimArticleAuthorName = StringUtils.trim(articleAuthorName);
                articleAuthors.add(new ArticleAuthor(trimArticleAuthorName, trimArticleAuthorName, deAccent(StringUtils.deleteWhitespace(trimArticleAuthorName))));
            }
        }
        return articleAuthors;
    }

    private Set<Category> createArticleCategories(String category) {
        Set<Category> categories = new TreeSet<>();
        if (!category.equals("null")) {
            String[] articleCategories = category.split("\\s*,\\s*");

            for (String categoryName : articleCategories) {
                String trimCategoryName = StringUtils.trim(categoryName);
                //TODO define the parent
                categories.add(new Category(trimCategoryName, trimCategoryName, deAccent(StringUtils.deleteWhitespace(trimCategoryName)), 0));
            }
        }
        return categories;
    }

    private Set<Tag> createArticleTags(String articleTopics) {
        Set<Tag> tags = new TreeSet<>();
        if (!articleTopics.equals("null")) {
            String[] tagsText = articleTopics.split("\\s*,\\s*");

            for (String tagName : tagsText) {
                String trimTagName = StringUtils.trim(tagName);
                tags.add(new Tag(trimTagName, trimTagName, deAccent(StringUtils.deleteWhitespace(trimTagName))));
            }
        }
        return tags;
    }

    public String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCOMBINING_DIACRITICAL_MARKS}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public List<Photo> createPhotoJsonFile(String fromFile, String toFile) {
        List<Photo> photoList = new ArrayList();
        Map<String, Integer> mapMedia = createTaxonomyMapMedia("wordpress_media.json");


        try {
            Reader reader = Files.newBufferedReader(Paths.get(fromFile));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);
            for (JsonNode jsonNode : parser) {

                Photo photo = new Photo();
                photo.setStatus("publish");
                photo.setTitle(StringUtils.trim(jsonNode.path("title").asText()));
                photo.setPhoto(getSingleMediaIdsByName(jsonNode.path("img").asText().replaceAll("[\\\\»«()<>+&@#/%?=~'|!:,.;]", "").trim(), mapMedia));
                photo.setEditor(StringUtils.trim(jsonNode.path("txt").asText()));
                photo.setDate(jsonNode.path("date_published").asText());
                photo.setNumReadings(jsonNode.path("num_hits").asText());
                photo.setCommentStatus("closed");

                photoList.add(photo);

            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        createJsonFile(photoList, toFile);

        return photoList;
    }
}


