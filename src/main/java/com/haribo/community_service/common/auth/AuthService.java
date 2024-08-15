package com.haribo.community_service.common.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;

    @Value("${path.to.auth}")
    private String pathToAuth;

    public LinkedHashMap<String, String> authorizedProfileId(String sessionId) throws URISyntaxException {

        log.info("profileId 알아보기!: session check!");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "JSESSIONID = " + sessionId);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange(new URI(pathToAuth), HttpMethod.GET, entity, LinkedHashMap.class);

        LinkedHashMap<String, LinkedHashMap<String, String>> map =  (LinkedHashMap<String, LinkedHashMap<String, String>>) responseEntity.getBody();

        log.info("profileId: {}, nickName: {}", map.get("profileMember").get("profileId"), map.get("profileMember").get("nickName"));

        LinkedHashMap<String, String> response = new LinkedHashMap<>();
        response.put("profileId", map.get("profileMember").get("profileId"));
        response.put("nickName", map.get("profileMember").get("nickName"));

        return response;
    }
}
