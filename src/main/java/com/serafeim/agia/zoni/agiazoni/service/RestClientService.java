package com.serafeim.agia.zoni.agiazoni.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serafeim.agia.zoni.agiazoni.model.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestClientService {
    Logger logger = LoggerFactory.getLogger(RestClientService.class);

    // Depricate this for this createPostsAccordingToTypeFromJsonFile if it works!!
    @Deprecated
    public List<Article> createArticlesFromJsonFile(String filename) {
        Article[] articles = new Article[0];
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream jsonFileStream = Files.newInputStream(Paths.get(filename));

            articles = mapper.readValue(jsonFileStream, Article[].class);

            logger.info(String.format("found articles %d ", articles.length));
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("There was an exception " + e.getMessage());
        }
        return Arrays.asList(articles);
    }

    public List<Edafio> createEdafiaFromJsonFile(String filename) {
        Edafio[] edafia = new Edafio[0];
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream jsonFileStream = Files.newInputStream(Paths.get(filename));

            edafia = mapper.readValue(jsonFileStream, Edafio[].class);

            logger.info(String.format("found edafia %d ", edafia.length));
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("There was an exception " + e.getMessage());
        }
        return Arrays.asList(edafia);
    }

    //TODO this can be of type Post
    public void createEdafia(List<Edafio> edafia) {
        HttpHeaders headers = getHttpHeaders();

        URI url = null;
        try {
            url = new URI("http://localhost:8081/wp-json/wp/v2/edafio");
        } catch (URISyntaxException e) {
            e.printStackTrace();

            logger.debug("Error in the uri " + e.getMessage());
        }
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        for (Edafio edafio : edafia) {

            ObjectMapper objectMapper = new ObjectMapper();
            HttpEntity<String> request =
                    null;
            try {
                request = new HttpEntity<>(objectMapper.writeValueAsString(edafio), headers);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            String articleResultAsJsonStr =
                    restTemplate.postForObject(url, request, String.class);
            JsonNode root = null;
            try {
                root = objectMapper.readTree(articleResultAsJsonStr);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error("Edafio not created: " + e.getMessage());
                continue;
            }

            logger.info("Edafio created: " + root);
        }
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("serafeim", "NFBN57Z8sVXs!a1N(IsFMdT(");
        return headers;
    }


    public void createArticles(List<Article> articles) throws JsonProcessingException {
        HttpHeaders headers = getHttpHeaders();

        URI url = null;
        try {
            url = new URI("http://localhost:8081/wp-json/wp/v2/posts");
        } catch (URISyntaxException e) {
            e.printStackTrace();

            logger.debug("Error in the uri " + e.getMessage());
        }
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        for (Article article : articles) {

            ObjectMapper objectMapper = new ObjectMapper();
            HttpEntity<String> request =
                    new HttpEntity<>(objectMapper.writeValueAsString(article), headers);

            String articleResultAsJsonStr =
                    restTemplate.postForObject(url, request, String.class);
            JsonNode root = objectMapper.readTree(articleResultAsJsonStr);

            logger.info("Article created: " + root);
        }
    }

    public List<Post> createPostsFromJsonFile(String filename) {
        Post[] posts = new Post[0];
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream jsonFileStream = Files.newInputStream(Paths.get(filename));

            posts = mapper.readValue(jsonFileStream, Post[].class);

            logger.info(String.format("found posts %d ", posts.length));
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("There was an exception " + e.getMessage());
        }
        return Arrays.asList(posts);
    }

    public List<WPPostDTO> createWPPostsFromJsonFile(String wpPostFile) {
        WPPostDTO[] wpPostDTOS = new WPPostDTO[0];
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream jsonFileStream = Files.newInputStream(Paths.get(wpPostFile));

            wpPostDTOS = mapper.readValue(jsonFileStream, WPPostDTO[].class);

            logger.info(String.format("found posts %d ", wpPostDTOS.length));
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("There was an exception " + e.getMessage());
        }
        return Arrays.asList(wpPostDTOS);
    }

    public void updateEdafiaPost(List<Post> posts) {
        HttpHeaders headers = getHttpHeaders();

        String url = "http://localhost:8081/wp-json/wp/v2/edafia/";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        for (Post post : posts) {

            ObjectMapper objectMapper = new ObjectMapper();
            HttpEntity<String> request = new HttpEntity<>("{\"status\":\"publish\"}", headers);

        }
    }

    public void createPosts(List<Article> posts, String url) {
        HttpHeaders headers = getHttpHeaders();

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        for (Article post : posts) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(post), headers);

                String jsonNode =
                        restTemplate.postForObject(url, request, String.class);
                JsonNode root = objectMapper.readTree(jsonNode);
                logger.debug(String.format("Post of type %s created: " + root, post.getClass().getName()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error("Post not created: " + e.getMessage());
            }

        }
    }

    public <T extends Article> List<T> getPostsAccordingToTypeFromJsonFile(String filename, Class<T> clazz) {
        List posts = new ArrayList();

        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream jsonFileStream = Files.newInputStream(Paths.get(filename));

            if (clazz.getDeclaredConstructor().newInstance() instanceof Video) {
                posts = Arrays.asList(mapper.readValue(jsonFileStream, Video[].class));
            } else if (clazz.getDeclaredConstructor().newInstance() instanceof Sound) {
                posts = Arrays.asList(mapper.readValue(jsonFileStream, Sound[].class));
            } else if (clazz.getDeclaredConstructor().newInstance() instanceof Photo) {
                posts = Arrays.asList(mapper.readValue(jsonFileStream, Photo[].class));
            } else {
                posts = Arrays.asList(mapper.readValue(jsonFileStream, Article[].class));
            }

            logger.info(String.format("found articles %d ", posts.size()));
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("There was an exception " + e.getMessage());
        }
        return posts;
    }

    public <T extends Article> void createPostsToWordpressAccordingPostType(List<T> posts, String url) {
        HttpHeaders headers = getHttpHeaders();

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        for (T post : posts) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(post), headers);

                String jsonNode =
                        restTemplate.postForObject(url, request, String.class);
                JsonNode root = objectMapper.readTree(jsonNode);
                logger.debug("Post of type created in the web: " + root);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error("Post not created: " + e.getMessage());
            }

        }
    }

    public List<Post> createPostToupdatePostsDate(List<Post> wpPosts, List<WPPostDTO> wpPostDTOS) {
        List<Post> posts = new ArrayList<>();

        for (Post post : wpPosts) {
            wpPostDTOS.stream()
                    .filter(wpPostDTO1 -> StringUtils.deleteWhitespace(wpPostDTO1.getTitle().getRendered()).equals(StringUtils.deleteWhitespace(post.getTitle())))
                    .findFirst()
                    .ifPresent(wpPostDTO1 -> posts.add(new Post(BigInteger.valueOf(wpPostDTO1.getId()), post.getDate(), wpPostDTO1.getTitle().getRendered())));
        }

        return posts;

    }

    public List<Post> createPostToupdatePostsEnnoima(List<Post> postsSingleEnnoima, List<Post> postsMultiEnnoima) {
        List<Post> posts = new ArrayList<>();

        for (Post postSingleEnnoima : postsSingleEnnoima) {
            postsMultiEnnoima.stream()
                    .filter(postMultiEnnoima ->
                            StringUtils.deleteWhitespace(postSingleEnnoima.getEnnoima())
                                    .equals(StringUtils.deleteWhitespace(ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees1()) +
                                            ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees2())
                                            + ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees3()) + ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees4())
                                            + ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees5()) + ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees6()) +
                                            ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees7()) + ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees8()) +
                                            ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees9()) + ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees10()))))
                    .findFirst()
                    .ifPresentOrElse(post1 -> posts.add(new Post(postSingleEnnoima.getId(), post1.getDate(), post1.getTitle(), postSingleEnnoima.getEnnoima(), post1.getVideoLink()
                                    , ReadJSONService.getTextIfEmptyOrNull(post1.getIdees1()), ReadJSONService.getTextIfEmptyOrNull(post1.getIdees2()),
                                    ReadJSONService.getTextIfEmptyOrNull(post1.getIdees3()), ReadJSONService.getTextIfEmptyOrNull(post1.getIdees4()),
                                    ReadJSONService.getTextIfEmptyOrNull(post1.getIdees5()), ReadJSONService.getTextIfEmptyOrNull(post1.getIdees6()),
                                    ReadJSONService.getTextIfEmptyOrNull(post1.getIdees7()), ReadJSONService.getTextIfEmptyOrNull(post1.getIdees8()),
                                    ReadJSONService.getTextIfEmptyOrNull(post1.getIdees9()), ReadJSONService.getTextIfEmptyOrNull(post1.getIdees10()))),
                            () -> {
                                // Its not matching
                                logger.error(String.format("Post with id %s and title %s did not found", postSingleEnnoima.getId(), postSingleEnnoima.getTitle()));
                            });
        }

        return posts;

    }

    public List<Post> createVideoToUpdateVideoLink(List<WPPostDTO> wpPosts) {
        return wpPosts.stream()
                .filter(wpPostDTO -> StringUtils.startsWith(wpPostDTO.getVideoLink(), "//"))
                .map(wpPostDTO -> new Post(BigInteger.valueOf(wpPostDTO.getId()), wpPostDTO.getDate(), wpPostDTO.getTitle().getRendered(), "https:" + wpPostDTO.getVideoLink()))
                .collect(Collectors.toList());
    }

    public void updatePostsDate(List<Post> posts) {
        HttpHeaders headers = getHttpHeaders();

        URI url;
        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

            for (Post post : posts) {
                url = new URI("http://localhost:8081/wp-json/wp/v2/posts/" + post.getId());

                ObjectMapper objectMapper = new ObjectMapper();
                HttpEntity<String> request =
                        new HttpEntity<>("{\"date\":\"" + post.getDate() + "\"}", headers);

                String postResultAsJsonStr =
                        restTemplate.postForObject(url, request, String.class);
                JsonNode root = objectMapper.readTree(postResultAsJsonStr);

                logger.info("Article created: " + root);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();

            logger.debug("Error in the uri " + e.getMessage());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public void updateVideoLinks(List<Post> posts) {
        HttpHeaders headers = getHttpHeaders();

        URI url;
        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

            for (Post post : posts) {
                url = new URI("http://localhost:8081/wp-json/wp/v2/video_posts/" + post.getId());

                ObjectMapper objectMapper = new ObjectMapper();
                HttpEntity<String> request =
                        new HttpEntity<>("{\"video_link\":\"" + post.getVideoLink() + "\"}", headers);

                String postResultAsJsonStr =
                        restTemplate.postForObject(url, request, String.class);
                JsonNode root = objectMapper.readTree(postResultAsJsonStr);

                logger.info("Article created: " + root);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();

            logger.debug("Error in the uri " + e.getMessage());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public void updateEnnoima(List<Post> posts) {
        HttpHeaders headers = getHttpHeaders();

        URI url;
        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

            for (Post post : posts) {
                url = new URI("http://localhost:8081/wp-json/wp/v2/posts/" + post.getId());

                ObjectMapper objectMapper = new ObjectMapper();

                Post postRequest = new Post();
                postRequest.setIdees1(StringUtils.isEmpty(post.getIdees1()) ? null : post.getIdees1().trim());
                postRequest.setIdees2(StringUtils.isEmpty(post.getIdees2()) ? null : post.getIdees2().trim());
                postRequest.setIdees3(StringUtils.isEmpty(post.getIdees3()) ? null : post.getIdees3().trim());
                postRequest.setIdees4(StringUtils.isEmpty(post.getIdees4()) ? null : post.getIdees4().trim());
                postRequest.setIdees5(StringUtils.isEmpty(post.getIdees5()) ? null : post.getIdees5().trim());
                postRequest.setIdees6(StringUtils.isEmpty(post.getIdees6()) ? null : post.getIdees6().trim());
                postRequest.setIdees7(StringUtils.isEmpty(post.getIdees7()) ? null : post.getIdees7().trim());
                postRequest.setIdees8(StringUtils.isEmpty(post.getIdees8()) ? null : post.getIdees8().trim());
                postRequest.setIdees9(StringUtils.isEmpty(post.getIdees9()) ? null : post.getIdees9().trim());
                postRequest.setIdees10(StringUtils.isEmpty(post.getIdees10()) ? null : post.getIdees10().trim());

                HttpEntity<Post> request =
                        new HttpEntity<>(postRequest, headers);

                String postResultAsJsonStr =
                        restTemplate.postForObject(url, request, String.class);
                JsonNode root = objectMapper.readTree(postResultAsJsonStr);

                logger.info("Article created: " + root);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();

            logger.debug("Error in the uri " + e.getMessage());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public List<Post> createPostsFromWpPostDTOJsonObjectList(List<WPPostDTO> wpPostDTOS) {
        return wpPostDTOS.stream()
                .map(wpPostDTO -> new Post(BigInteger.valueOf(wpPostDTO.getId()), wpPostDTO.getDate(), wpPostDTO.getTitle().getRendered(), "https:" + wpPostDTO.getVideoLink(), wpPostDTO.getEnnoima()))
                .collect(Collectors.toList());
    }
}
