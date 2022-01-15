package com.bookiply.interview.assignment.controller;

import com.bookiply.interview.assignment.response.FirehosesLengthResponse;
import com.bookiply.interview.assignment.service.IHydrantService;
import com.bookiply.interview.assignment.utils.HydrantUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

public class HydrantControllerTest {

    private HydrantController hydrantController;
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        IHydrantService hydrantService = Mockito.mock(IHydrantService.class);

        Mockito.
                when(hydrantService.findNearestHydrants(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyLong()))
                .thenReturn(HydrantUtils.mockHydrantResponse());
        Mockito.
                when(hydrantService.getFirehosesTotalLength(Mockito.any()))
                .thenReturn(HydrantUtils.mockFirehosesLengthResponse());

        this.hydrantController = new HydrantController(hydrantService);
    }


    @Test
    public void fetchNearestHydrantsAndTotalLengthByPointOfFire() {
        Double pointOfFireLatitude = 40.75377740880684;
        Double pointOfFireLongitude = -73.72485402779134;
        Long totalFirehoses = 3l;
        ResponseEntity<FirehosesLengthResponse> responseEntity = hydrantController.firehosesLengthFromPoint(totalFirehoses, pointOfFireLatitude, pointOfFireLongitude);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertNotNull(responseEntity.getBody().getHydrants());
        Assert.assertNotNull(responseEntity.getBody().getTotalFirehosesLength());
        Assert.assertEquals(Long.valueOf(responseEntity.getBody().getHydrants().length), totalFirehoses);
    }

    @Test
    public void shouldThrowServiceUnavailableExceptionInCaseOfHttpClientErrorForNearestHydrant() {
        Double pointOfFireLatitude = 40.75377740880684;
        Double pointOfFireLongitude = -73.72485402779134;
        Long totalFirehoses = 3l;
        IHydrantService hydrantService = Mockito.mock(IHydrantService.class);

        Mockito.when(hydrantService.findNearestHydrants(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyLong()))
                .thenThrow(new HttpClientErrorException(HttpStatus.REQUEST_TIMEOUT));
        HydrantController hydrantController = new HydrantController(hydrantService);
        ResponseEntity<FirehosesLengthResponse> responseEntity = hydrantController.firehosesLengthFromPoint(totalFirehoses, pointOfFireLatitude, pointOfFireLongitude);
        Assert.assertEquals(HttpStatus.SERVICE_UNAVAILABLE, responseEntity.getStatusCode());
    }

    @Test
    public void shouldThrowInternalServerErrorInCaseOfAnyOtherErrorForNearestHydrant() {

        Double pointOfFireLatitude = 40.75377740880684;
        Double pointOfFireLongitude = -73.72485402779134;
        Long totalFirehoses = 3l;
        IHydrantService hydrantService = Mockito.mock(IHydrantService.class);
        Mockito.when(hydrantService.findNearestHydrants(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyLong()))
                .thenThrow(new RuntimeException("Any random exception"));
        HydrantController hydrantController = new HydrantController(hydrantService);
        ResponseEntity<FirehosesLengthResponse> responseEntity = hydrantController.firehosesLengthFromPoint(totalFirehoses, pointOfFireLatitude, pointOfFireLongitude);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

}