package com.serafeim.agia.zoni.agiazoni.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serafeim.agia.zoni.agiazoni.model.Article;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
public class RestClientService {
    Logger logger = LoggerFactory.getLogger(RestClientService.class);

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

    public void createArticles(List<Article> articles) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("serafeim", "NFBN57Z8sVXs!a1N(IsFMdT(");

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
            HttpEntity request =
                    new HttpEntity(objectMapper.writeValueAsString(article), headers);

            String articleResultAsJsonStr =
                    restTemplate.postForObject(url, request, String.class);
            JsonNode root = objectMapper.readTree(articleResultAsJsonStr);

            logger.info("Article created: " + root);
        }
    }

}
