package com.bookiply.interview.assignment.service.impl;

import com.bookiply.interview.assignment.config.HydrantPropertyConfig;
import com.bookiply.interview.assignment.config.SodaApiBuilder;
import com.bookiply.interview.assignment.response.FirehosesLengthResponse;
import com.bookiply.interview.assignment.response.HydrantResponse;
import com.bookiply.interview.assignment.service.IHydrantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;

@Service
public class HydrantService implements IHydrantService {

    private static final Logger log = LoggerFactory.getLogger(HydrantService.class);
    private RestTemplate restTemplate;
    private HydrantPropertyConfig hydrantPropertyConfig;

    public HydrantService(RestTemplate restTemplate, HydrantPropertyConfig hydrantPropertyConfig) {
        this.restTemplate = restTemplate;
        this.hydrantPropertyConfig = hydrantPropertyConfig;
    }

    @Override
    public HydrantResponse[] findNearestHydrants(Double latitude, Double longitude, Long totalFirehoses) throws RestClientException {
        return findNearestHydrants(latitude, longitude, totalFirehoses, hydrantPropertyConfig.getHydrantApiDefaultCircleRadius());
    }


    public HydrantResponse[] findNearestHydrants(Double latitude, Double longitude, Long totalFirehoses, Long hydrantCircleRadius) throws RestClientException {
        SodaApiBuilder sodaApiBuilder = SodaApiBuilder.newInstance()
                .withScheme(hydrantPropertyConfig.getHydrantApiScheme())
                .withHost(hydrantPropertyConfig.getHydrantApiHost())
                .withResource(hydrantPropertyConfig.getHydrantApiResource())
                .withSelect("unitid,distance_in_meters(the_geom, 'POINT(" + longitude + " " + latitude + ")') AS distanceToFire")
                .withOrder("distanceToFire")
                .withLimit(totalFirehoses);

        URI hydrantUri = sodaApiBuilder.build().toUri();
        log.info("Invoking URI for fetch hydrant distance {}", hydrantUri);
        HydrantResponse[] hydrantResponse = restTemplate.getForObject(hydrantUri, HydrantResponse[].class);
        log.info("Response received from fetch hydrant distance {}  totalHydrants :{}", hydrantUri, hydrantResponse.length);

        return hydrantResponse;
    }

    @Override
    public FirehosesLengthResponse getFirehosesTotalLength(HydrantResponse[] hydrantResponse) {

        Double totalLength = Arrays.stream(hydrantResponse)
                .map(HydrantResponse::getDistanceToFire)
                .reduce(0d, (a, b) -> a + b);

        FirehosesLengthResponse firehosesLengthResponse = new FirehosesLengthResponse();
        firehosesLengthResponse.setTotalFirehosesLength(totalLength);
        firehosesLengthResponse.setHydrants(hydrantResponse);
        return firehosesLengthResponse;
    }
}
