package com.bookiply.interview.assignment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HydrantPropertyConfig {

    @Value("${hydrant.api.host}")
    private String hydrantApiHost;

    @Value("${hydrant.api.resource}")
    private String hydrantApiResource;

    @Value("${hydrant.api.scheme}")
    private String hydrantApiScheme;

    @Value("${hydrant.api.defaultCircleRadius}")
    private Long hydrantApiDefaultCircleRadius;

    @Value("${hydrant.api.enhanceCircleRadius}")
    private Long hydrantApiEnhanceCircleRadius;

    public String getHydrantApiHost() {
        return hydrantApiHost;
    }

    public void setHydrantApiHost(String hydrantApiHost) {
        this.hydrantApiHost = hydrantApiHost;
    }

    public String getHydrantApiResource() {
        return hydrantApiResource;
    }

    public void setHydrantApiResource(String hydrantApiResource) {
        this.hydrantApiResource = hydrantApiResource;
    }

    public String getHydrantApiScheme() {
        return hydrantApiScheme;
    }

    public void setHydrantApiScheme(String hydrantApiScheme) {
        this.hydrantApiScheme = hydrantApiScheme;
    }

    public Long getHydrantApiDefaultCircleRadius() {
        return hydrantApiDefaultCircleRadius;
    }

    public void setHydrantApiDefaultCircleRadius(Long hydrantApiDefaultCircleRadius) {
        this.hydrantApiDefaultCircleRadius = hydrantApiDefaultCircleRadius;
    }

    public Long getHydrantApiEnhanceCircleRadius() {
        return hydrantApiEnhanceCircleRadius;
    }

    public void setHydrantApiEnhanceCircleRadius(Long hydrantApiEnhanceCircleRadius) {
        this.hydrantApiEnhanceCircleRadius = hydrantApiEnhanceCircleRadius;
    }
}
