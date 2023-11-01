package com.example.originalspecialmove.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LineUserService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public String getLineUser(String idToken) throws JsonMappingException, JsonProcessingException {
        JsonNode jsonNode = connectLineApi(idToken);
        String subValue = jsonNode.get("sub").asText();

        return subValue;
    }

    public Pair<String, String> getLineUserInfo(String idToken) throws JsonMappingException, JsonProcessingException {
        JsonNode jsonNode = connectLineApi(idToken);
        String subValue = jsonNode.get("sub").asText();
        String userName = jsonNode.get("name").asText();

        return Pair.of(subValue, userName);
    }

    private JsonNode connectLineApi(String idToken) throws JsonMappingException, JsonProcessingException {
        // リクエストボディを作成
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("id_token", idToken);
        requestBody.add("client_id", "2001116233");

        // リクエストヘッダーを設定
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // POSTリクエストを送信
        String url = "https://api.line.me/oauth2/v2.1/verify";
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        // レスポンスデータを取得
        String responseBody = response.getBody();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode;
    }
}
