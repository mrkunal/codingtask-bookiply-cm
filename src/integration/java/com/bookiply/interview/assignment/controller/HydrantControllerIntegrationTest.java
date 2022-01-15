package com.bookiply.interview.assignment.controller;

import com.bookiply.interview.assignment.config.IntegrationPropertyConfig;
import com.bookiply.interview.assignment.response.FirehosesLengthResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class HydrantControllerIntegrationTest {
    private RestTemplate restTemplate;
    private String firhosesLengthUrl = "/trucks/%s/points/latitude/%s/longitude/%s/firehose/length";
    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Before
    public void setUp() throws Exception {
        restTemplate = new RestTemplate();
    }

    @Test
    public void fetchNearestHydrantsAndLengthFromFirePoint() {
        String latitude = "40.75377740880684";
        String longitude = "-73.72485402779134";
        Long firehoses = 3l;

        ResponseEntity<FirehosesLengthResponse> responseEntity = restTemplate.getForEntity(IntegrationPropertyConfig.getFirehosesHost() +
                String.format(firhosesLengthUrl, firehoses, latitude, longitude), FirehosesLengthResponse.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        FirehosesLengthResponse firehosesLengthResponse = responseEntity.getBody();
        Assert.assertNotNull(firehosesLengthResponse);
        Assert.assertEquals(Integer.parseInt(firehoses.toString()), firehosesLengthResponse.getHydrants().length);
        Assert.assertTrue(firehosesLengthResponse.getTotalFirehosesLength() > 0);
        Assert.assertTrue(firehosesLengthResponse.getTotalFirehosesLength().equals(125.23431622000001));
    }

    @Test
    public void shouldReturnEmptyNearestHydrantsAndLengthFromInvalidFirePoint() {
        String latitude = "45.75377740880684";
        String longitude = "-73.72485402779134";
        Long firehoses = 3l;

        ResponseEntity<FirehosesLengthResponse> responseEntity = restTemplate.getForEntity(IntegrationPropertyConfig.getFirehosesHost() +
                String.format(firhosesLengthUrl, firehoses, latitude, longitude), FirehosesLengthResponse.class);

        FirehosesLengthResponse firehosesLengthResponse = responseEntity.getBody();
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(firehosesLengthResponse);
        Assert.assertEquals(0, firehosesLengthResponse.getHydrants().length);
        Assert.assertTrue(firehosesLengthResponse.getTotalFirehosesLength() == 0.0);
    }

    @Test
    public void shouldThrowBadRequestFromNearestHydrantsAndLengthInCaseOfInvalidPathParams() {
        exception.expect(RestClientException.class);
        exception.expect(HttpClientErrorException.class);

        String latitude = "45.75377740880684";
        String longitude = "-73.72485402779134";
        Long firehoses = 3l;

        try {
            ResponseEntity<FirehosesLengthResponse> responseEntity = restTemplate.getForEntity(IntegrationPropertyConfig.getFirehosesHost() +
                    String.format(firhosesLengthUrl, latitude, firehoses, longitude), FirehosesLengthResponse.class);
        } catch (HttpClientErrorException e) {
            Assert.assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
            throw e;
        }
    }


}
