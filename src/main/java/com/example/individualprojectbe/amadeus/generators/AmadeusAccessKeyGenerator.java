package com.example.individualprojectbe.amadeus.generators;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.example.individualprojectbe.config.AdminConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class AmadeusAccessKeyGenerator {
    private final AdminConfig adminConfig;
    private final String apiKey;
    private final String apiSecret;
    private final String tokenUrl = "https://test.api.amadeus.com/v1/security/oauth2/token";
    private String accessToken;

    @Autowired
    public AmadeusAccessKeyGenerator(AdminConfig adminConfig) {
        this.adminConfig = adminConfig;
        this.apiKey = adminConfig.getAmadeusApiKey();
        this.apiSecret = adminConfig.getAmadeusApiSecret();
    }

    public String generateAccessToken() {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(tokenUrl);

        String authHeader = "Basic " + Base64.getEncoder().encodeToString((apiKey + ":" + apiSecret).getBytes());
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", "client_credentials"));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse response = httpClient.execute(httpPost);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            JsonObject jsonResponse = JsonParser.parseString(result.toString()).getAsJsonObject();

            this.accessToken = jsonResponse.get("access_token").getAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("key  " + adminConfig.getAmadeusApiKey());
        System.out.println("key  " + apiKey);

        System.out.println("secret:  " + adminConfig.getAmadeusApiSecret());
        System.out.println("secret:  " + apiSecret);

        System.out.println("Your access token: " + accessToken);
        return accessToken;
    }
}