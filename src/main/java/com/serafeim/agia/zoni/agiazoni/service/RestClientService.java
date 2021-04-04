package com.serafeim.agia.zoni.agiazoni.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serafeim.agia.zoni.agiazoni.model.ArticleAuthor;
import com.serafeim.agia.zoni.agiazoni.model.Post;
import com.serafeim.agia.zoni.agiazoni.model.Taxonomy;
import com.serafeim.agia.zoni.agiazoni.model.Video;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.serafeim.agia.zoni.agiazoni.service.RestClientUtil.getHttpHeaders;

@Service
public class RestClientService {
    Logger logger = LoggerFactory.getLogger(RestClientService.class);

    public <T extends Post> void createPostsToWordpressAccordingPostType(List<T> posts, String url) {
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

    public void updateSpecificField(URI uri, HttpEntity<String> request) {

        try {
            RestTemplate restTemplate = RestClientUtil.restTemplate();
            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

            ObjectMapper objectMapper = new ObjectMapper();
            String postResultAsJsonStr =
                    restTemplate.postForObject(uri, request, String.class);
            JsonNode root = objectMapper.readTree(postResultAsJsonStr);

            logger.info("Post updated: " + root);
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException | JsonProcessingException e) {
            e.printStackTrace();
        }


    }

    public void updatePostsDate(List<Post> posts, String url) {
        HttpHeaders headers = getHttpHeaders();
        URI uri;
        try {

            for (Post post : posts) {
                uri = new URI(url + post.getId());

                HttpEntity<String> request =
                        new HttpEntity<>("{\"fields\": {\"xronos_ekdosis\":\"" + post.getAge() + "\"}}", headers);

                updateSpecificField(uri,request);

            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void updateTaxonomyAuthor(List<Taxonomy> taxonomies, String url) {
        HttpHeaders headers = getHttpHeaders();

        URI uri;
        try {

            for (Taxonomy articleAuthor : taxonomies) {
                uri = new URI(url + articleAuthor.getId());

                HttpEntity<String> request =
                        new HttpEntity<>("{\"author_title\":\"" + articleAuthor.getProfession() + "\",\"old_webiste\":\"" + articleAuthor.getOldWebisteId() + "\"}", headers);

                updateSpecificField(uri, request);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            logger.debug("Error in the uri " + e.getMessage());
        }
    }

    public void updateVideoLinks(List<Post> posts, String url) {
        HttpHeaders headers = getHttpHeaders();

        URI uri;
        try {

            for (Post post : posts) {
                uri = new URI(url + post.getId());

                HttpEntity<String> request =
                        new HttpEntity<>("{\"test\":\"" + post.getAge() + "\"}", headers);
                     updateSpecificField(uri, request);

            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            logger.debug("Error in the uri " + e.getMessage());
        }

    }

    public void updateEnnoima(List<Post> posts, String url) {
        HttpHeaders headers = getHttpHeaders();

        URI uri;
        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

            for (Post post : posts) {
                uri = new URI(url + post.getId());

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
                        restTemplate.postForObject(uri, request, String.class);
                JsonNode root = objectMapper.readTree(postResultAsJsonStr);

                logger.info("Post created: " + root);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();

            logger.debug("Error in the uri " + e.getMessage());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }


    public List<Post> createPostToUpdatePostsDate(List<Post> wpPosts, List<Post> wpPostDTOS) {
        List<Post> posts = new ArrayList<>();

        for (Post post : wpPosts) {
            wpPostDTOS.stream()
                    .filter(wpPostDTO1 ->
                            (StringUtils.deleteWhitespace(wpPostDTO1.getTitle()).equals(StringUtils.deleteWhitespace(post.getTitle()))
                                    ||
                                    isEnnoimaAllFieldsEquals(post, wpPostDTO1))
                                    && StringUtils.isNotEmpty(post.getAge())
                    )
                    .findFirst()
                    .ifPresent(wpPostDTO1 -> posts.add(new Post(wpPostDTO1.getId(), post.getDate(), wpPostDTO1.getTitle(), post.getAge())));
        }

        return posts;

    }

    private boolean isEnnoimaAllFieldsEquals(Post post, Post wpPostDTO1) {
        // No need to test all ennoima
        boolean isEqual = false;
        try {
            isEqual = isEqualsWithNoSpace(post.getIdees1(), wpPostDTO1.getIdees1());
        } catch (Exception e) {
            logger.error(String.format("comparing: %s with %s", post.getIdees1(), wpPostDTO1.getIdees1()));
        }
//                && isEqualsWithNoSpace(post.getIdees2(), wpPostDTO1.getIdees2());
//                && isEqualsWithNoSpace(post.getIdees3(), wpPostDTO1.getIdees3())
//                && isEqualsWithNoSpace(post.getIdees4(), wpPostDTO1.getIdees4())
//                && isEqualsWithNoSpace(post.getIdees5(), wpPostDTO1.getIdees5())
//                && isEqualsWithNoSpace(post.getIdees6(), wpPostDTO1.getIdees6())
//                && isEqualsWithNoSpace(post.getIdees7(), wpPostDTO1.getIdees7())
//                && isEqualsWithNoSpace(post.getIdees8(), wpPostDTO1.getIdees8())
//                && isEqualsWithNoSpace(post.getIdees9(), wpPostDTO1.getIdees9());
        return isEqual;
    }

    private boolean isEqualsWithNoSpace(String str1, String str2) {
        return StringUtils.isNotEmpty(str1) && StringUtils.isNotEmpty(str1) && StringUtils.deleteWhitespace(str1).equals(StringUtils.deleteWhitespace(str2));
    }

    public List<Post> createPostToUpdatePostsEnnoima(List<Post> postsSingleEnnoima, List<Post> postsMultiEnnoima) {
        List<Post> posts = new ArrayList<>();

        for (Post postSingleEnnoima : postsSingleEnnoima) {
            postsMultiEnnoima.stream()
                    .filter(postMultiEnnoima ->
                            isEnnoimaEquals(postSingleEnnoima, postMultiEnnoima))
                    .findFirst()
                    .ifPresentOrElse(post1 -> posts.add(new Post(postSingleEnnoima.getId(), post1.getDate(), post1.getTitle(), postSingleEnnoima.getEnnoima(),
                                    ReadJSONService.getTextIfEmptyOrNull(post1.getIdees1()), ReadJSONService.getTextIfEmptyOrNull(post1.getIdees2()),
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

    //TODO make this general and not only for authors
    public List<Taxonomy> createPostToUpdateTaxonomy(List<Taxonomy> taxonomiesOldSite, List<Taxonomy> taxonomiesNewSite) {
        List<Taxonomy> taxonomies = new ArrayList<>();

        for (Taxonomy taxonomy : taxonomiesOldSite) {
            taxonomiesNewSite.stream()
                    .filter(taxonomy1 ->
                            StringUtils.deleteWhitespace(taxonomy.getName()).equalsIgnoreCase(StringUtils.deleteWhitespace(taxonomy1.getName())))
                    .findFirst()
                    .ifPresentOrElse(taxonomy1 -> taxonomies.add(new ArticleAuthor(taxonomy1.getId(), taxonomy1.getDescription(), taxonomy1.getName(), taxonomy1.getSlug(), taxonomy.getOldWebisteId(), taxonomy.getProfession())),
                            () -> {
                                // Its not matching
                                logger.error(String.format("Taxonomy with id %s and name %s did not found", taxonomy.getId(), taxonomy.getName()));
                            });
        }

        return taxonomies;

    }

    private boolean isEnnoimaEquals(Post postSingleEnnoima, Post postMultiEnnoima) {
        return StringUtils.deleteWhitespace(postSingleEnnoima.getEnnoima())
                .equals(StringUtils.deleteWhitespace(ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees1()) +
                        ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees2())
                        + ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees3()) + ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees4())
                        + ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees5()) + ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees6()) +
                        ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees7()) + ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees8()) +
                        ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees9()) + ReadJSONService.getTextIfEmptyOrNull(postMultiEnnoima.getIdees10())));
    }

    public List<Video> createVideoToUpdateVideoLink(List<Video> wpPosts) {
        return wpPosts.stream()
                .filter(wpPostDTO -> StringUtils.startsWith(wpPostDTO.getVideoLink(), "//"))
                .map(wpPostDTO -> new Video(wpPostDTO.getId(), wpPostDTO.getDate(), wpPostDTO.getTitle(), "https:" + wpPostDTO.getVideoLink()))
                .collect(Collectors.toList());
    }


    public List<Post> createPostsFromWpPostDTOJsonObjectList(List<Post> wpPostDTOS) {
        return wpPostDTOS.stream()
//                .map(wpPostDTO -> new Post(BigInteger.valueOf(wpPostDTO.getId()), wpPostDTO.getDate(), wpPostDTO.getTitle().getRendered(), "https:" + wpPostDTO.getVideoLink(), wpPostDTO.getEnnoima()))
                .map(wpPostDTO -> new Post(wpPostDTO.getId(), wpPostDTO.getDate(), wpPostDTO.getTitle(),
                        null,
                        wpPostDTO.getIdees1(),
                        wpPostDTO.getIdees2(),
                        wpPostDTO.getIdees3(),
                        wpPostDTO.getIdees4(),
                        wpPostDTO.getIdees5(),
                        wpPostDTO.getIdees6(),
                        wpPostDTO.getIdees7(),
                        wpPostDTO.getIdees8(),
                        wpPostDTO.getIdees9(),
                        wpPostDTO.getIdees10()
                ))
                .collect(Collectors.toList());
    }


}
