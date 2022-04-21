package com.serafeim.agia.zoni.agiazoni.service;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class RestClientUtil {

    public static final String WEBSITE_URL_LOCAL = "http://localhost:8081";
    public static final String WEBSITE_URL_PRODUCTION = "https://agiazoni.gr";
    public static final String WEBSITE_URL_SERAFEIMKOURLOS = "https://serafeimkourlos.xyz";
    public static final String BASE_PATH = "/wp-json/wp";
    public static final String VERSION = "/v2";
    public static final String VIDEOS_ENDPOINT = "/video_posts/";
    public static final String SOUNDS_ENDPOINT = "/sound_posts/";
    public static final String PHOTOS_ENDPOINT = "/photo_posts/";
    public static final String POSTS_ENDPOINT = "/posts/";
    public static final String EDAFIA_ENDPOINT = "/edafio/";
    public static final String ARTICLE_AUTHORS_ENDPOINT = "/article_authors/";

    public static HttpHeaders getHttpHeaders(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, password);
        return headers;
    }

    @Bean
    public static RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);
        return new RestTemplate(requestFactory);
    }


}
