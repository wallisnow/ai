package com.ai.sys.controller;

import com.ai.sys.model.LoginEntry;
import com.ai.sys.service.DataSetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DataSetControllerTest {

    @Autowired
    private TestRestTemplate template;

    @MockBean
    private DataSetService dataSetService;

    public String token;

    @BeforeEach
    public void setUp() throws Exception {
        token = getToken();
    }

    @Test
    void findDataSetById() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", token);

        when(dataSetService.findAll()).thenAnswer(invocation -> {
            throw new Error("");
        });
        ResponseEntity<String> response = template.exchange("/api/v1/dataset/all", HttpMethod.GET, new HttpEntity<>(headers), String.class);
        assertThat(response).asString().contains("发生异常");
    }

    private String getToken() throws JsonProcessingException {
        LoginEntry user = new LoginEntry("admin", "admin");
        HttpEntity<LoginEntry> request = new HttpEntity<>(user);
        ResponseEntity<String> forEntity = template.postForEntity("/login", request, String.class);
        Map<String, Object> result = new ObjectMapper().readValue(forEntity.getBody(), HashMap.class);
        Object token = result.get("token");
        return String.valueOf(token);
    }

}