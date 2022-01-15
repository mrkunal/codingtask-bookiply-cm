package com.bookiply.interview.assignment.controller;

import com.bookiply.interview.assignment.response.FirehosesLengthResponse;
import com.bookiply.interview.assignment.response.HydrantResponse;
import com.bookiply.interview.assignment.service.IHydrantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

@RestController
@RequestMapping("/hydrant")
public class HydrantController {

    private static final Logger log = LoggerFactory.getLogger(HydrantController.class);

    private IHydrantService hydrantService;

    public HydrantController(IHydrantService hydrantService) {
        this.hydrantService = hydrantService;
    }

    @GetMapping(path = "/trucks/{totalTruck}/points/latitude/{latitude}/longitude/{longitude}/firehose/length")
    public ResponseEntity<FirehosesLengthResponse> firehosesLengthFromPoint(@PathVariable("totalTruck") Long firehoses, @PathVariable("latitude") Double latitude, @PathVariable("longitude") Double longitude) {

        try {
            HydrantResponse[] hydrantResponse = hydrantService.findNearestHydrants(latitude, longitude, firehoses);
            return ResponseEntity.ok(hydrantService.getFirehosesTotalLength(hydrantResponse));
        } catch (RestClientException restClientException) {
            log.error("RestClientException occurred with the soda API", restClientException);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        } catch (Exception e) {
            log.error("Exception occurred while fetching length from service", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
