package com.example.commissioncalculator.controller;

import com.example.commissioncalculator.CommissionCalculatorApplication;
import com.example.commissioncalculator.model.CommissionResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommissionCalculatorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PropertySource(value = "classpath:resources/application-test.yml")
public class CommissionCalculatorControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url;

    @Before
    public void setup () {
        url = "http://localhost:" + port;
    }

    @Test
    public void shouldCommitTransaction() {
        // Given
        String date = "2021-02-01";
        String amount = "500.00";
        String currency = "EUR";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson = "{\"date\": \""+date+"\",\"amount\": \""+amount+"\",\"currency\": \""+currency+"\",\"client_id\": 1}";
        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

        // When
        ResponseEntity<CommissionResponse> response = restTemplate.exchange(url.concat("/commission"),
                HttpMethod.POST, entity, CommissionResponse.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getCurrency(), currency);
        assertEquals(response.getBody().getAmount(), BigDecimal.valueOf(2.50).setScale(2, RoundingMode.CEILING));

    }
}
