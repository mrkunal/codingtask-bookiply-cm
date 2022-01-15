package com.bookiply.interview.assignment.service.impl;

import com.bookiply.interview.assignment.config.HydrantPropertyConfig;
import com.bookiply.interview.assignment.response.FirehosesLengthResponse;
import com.bookiply.interview.assignment.response.HydrantResponse;
import com.bookiply.interview.assignment.service.IHydrantService;
import com.bookiply.interview.assignment.utils.HydrantUtils;

import java.net.URI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.web.client.RestTemplate;

public class HydrantServiceTest {
    private RestTemplate restTemplate;
    private IHydrantService hydrantService;
    private HydrantPropertyConfig hydrantPropertyConfig;

    @Before
    public void setUp() throws Exception {
        restTemplate = Mockito.mock(RestTemplate.class);
        hydrantPropertyConfig = Mockito.mock(HydrantPropertyConfig.class);

        Mockito.when(hydrantPropertyConfig.getHydrantApiScheme()).thenReturn("https");
        Mockito.when(hydrantPropertyConfig.getHydrantApiHost()).thenReturn("dummy-host");
        Mockito.when(hydrantPropertyConfig.getHydrantApiResource()).thenReturn("resource/test.json");
        Mockito.when(hydrantPropertyConfig.getHydrantApiDefaultCircleRadius()).thenReturn(5l);


        Mockito.when(restTemplate.getForObject(Mockito.any(URI.class), Mockito.any()))
                .then(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        URI uri = (URI) invocationOnMock.getArguments()[0];
                        Assert.assertEquals("https", uri.getScheme());
                        Assert.assertEquals("dummy-host", uri.getHost());
                        Assert.assertEquals("/resource/test.json", uri.getPath());
                        Assert.assertTrue(uri.getQuery().contains("$limit=3"));
                        Assert.assertTrue(uri.getQuery().contains("$order=distanceToFire"));
                        Assert.assertTrue(uri.getQuery().contains("$select=unitid,distance_in_meters(the_geom, 'POINT(-73.72485402779134 40.75377740880684)') AS distanceToFire"));
                        Assert.assertTrue(uri.getQuery().contains("$where=within_circle(the_geom,\"40.75377740880684\",\"-73.72485402779134\",5)"));
                        return HydrantUtils.mockHydrantResponse();
                    }
                });

        hydrantService = new HydrantService(restTemplate, hydrantPropertyConfig);
    }


    @Test
    public void toFetchNearestHydrantsFromThePointCoordinates() {
        Double pointOfFireLatitude = 40.75377740880684;
        Double pointOfFireLongitude = -73.72485402779134;
        Long totalFirehoses = 3l;
        HydrantResponse[] hydrantRespons = hydrantService.findNearestHydrants(pointOfFireLatitude, pointOfFireLongitude, totalFirehoses);
        Assert.assertEquals(Long.valueOf(hydrantRespons.length), totalFirehoses);
    }

    @Test
    public void toTestTheFirehoseLengthObjectCreationFromHydrantResponse() {
        FirehosesLengthResponse firehosesLengthResponse = hydrantService.getFirehosesTotalLength(HydrantUtils.mockHydrantResponse());
        Assert.assertNotNull(firehosesLengthResponse);
        Assert.assertEquals(3, firehosesLengthResponse.getHydrants().length);
        Assert.assertTrue(firehosesLengthResponse.getTotalFirehosesLength() > 0);
        Assert.assertTrue(firehosesLengthResponse.getTotalFirehosesLength().equals(200.0));

    }


}