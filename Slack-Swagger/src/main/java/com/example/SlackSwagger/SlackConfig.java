package com.example.SlackSwagger;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class SlackConfig {
    private final RestTemplate restTemplate;

    @Value("${slack.url}")
    private String url;
    public SlackConfig(RestTemplateBuilder builder) {

        this.restTemplate = builder.build();
    }
    public void sendMessageToSlack(String message){
        CloseableHttpClient client = null;
        try {
            client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            String json = "{\"text\":\"" + message + "\"}";
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            client.execute(httpPost);
        } catch (Exception e) {
            System.out.println("\"Could not send message to slack: \" = " + e);
        } finally {
            if (null != client) {
                try {
                    client.close();
                } catch (IOException e) {
                    System.out.println("Could not close CloseableHttpClient: " + e);
                }
            }
        }
    }
}
