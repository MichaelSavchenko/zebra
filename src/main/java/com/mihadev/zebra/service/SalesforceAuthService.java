package com.mihadev.zebra.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mihadev.zebra.dto.SalesForceAuthDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public class SalesforceAuthService {

    @Value("${SALESFORCE_USERNAME}")
    static String USERNAME;
    @Value("${SALESFORCE_PASSWORD}")
    static String PASSWORD;
    @Value("${SALESFORCE_CLIENTID}")
    static String CLIENTID;
    @Value("${SALESFORCE_CLIENTSECRET}")
    static String CLIENTSECRET;
    static String LOGINURL     = "https://login.salesforce.com";
    static String GRANTSERVICE = "/services/oauth2/token?grant_type=password";


    public static SalesForceAuthDto getToken() {
        String loginURL = LOGINURL +
                GRANTSERVICE +
                "&client_id=" + CLIENTID +
                "&client_secret=" + CLIENTSECRET +
                "&username=" + USERNAME +
                "&password=" + PASSWORD;

        System.out.println(loginURL);

        OkHttpClient httpClient = new OkHttpClient();
        RequestBody reqbody = RequestBody.create(null, new byte[0]);

        Request request = new Request.Builder()
                .url(loginURL)
                .addHeader("Content-Type", "application/json")
                .post(reqbody)
                .build();

        SalesForceAuthDto result = new SalesForceAuthDto();
        try (Response response = httpClient.newCall(request).execute()) {
            String jsonData = response.body().string();
            System.out.println(jsonData);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = mapper.readValue(jsonData, Map.class);

            result.setAccessToken(map.get("access_token"));
            result.setInstanceUrl(map.get("instance_url"));


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        return result;
    }
}
