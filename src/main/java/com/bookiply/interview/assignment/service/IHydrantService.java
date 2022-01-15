package com.bookiply.interview.assignment.service;

import com.bookiply.interview.assignment.response.FirehosesLengthResponse;
import com.bookiply.interview.assignment.response.HydrantResponse;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.RestClientException;

public interface IHydrantService {

    @Retryable( value = RestClientException.class, maxAttemptsExpression = "${hydrant.api.retryMaxAttempt}",
            backoff = @Backoff(delayExpression = "${hydrant.api.retryMaxDelay}"))
    HydrantResponse[] findNearestHydrants(Double latitude, Double longitude, Long totalFirehoses) throws RestClientException;

    FirehosesLengthResponse getFirehosesTotalLength(HydrantResponse[] hydrantResponse);
}
