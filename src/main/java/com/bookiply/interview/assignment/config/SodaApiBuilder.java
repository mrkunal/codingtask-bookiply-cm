package com.bookiply.interview.assignment.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


public class SodaApiBuilder {

    private String select;
    private Long limit;
    private String where;
    private String order;
    private String host;
    private String scheme;
    private String resource;

    private SodaApiBuilder() {
    }

    public static SodaApiBuilder newInstance() {
        return new SodaApiBuilder();
    }

    public SodaApiBuilder withSelect(String selectQuery) {
        this.select = selectQuery;
        return this;
    }

    public SodaApiBuilder withLimit(Long limit) {
        this.limit = limit;
        return this;
    }

    public SodaApiBuilder withWhere(String where) {
        this.where = where;
        return this;
    }

    public SodaApiBuilder withOrder(String order) {
        this.order = order;
        return this;
    }

    public SodaApiBuilder withHost(String host) {
        this.host = host;
        return this;
    }

    public SodaApiBuilder withScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    public SodaApiBuilder withResource(String resource) {
        this.resource = resource;
        return this;
    }

    public UriComponents build() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .path(this.resource);

        if (StringUtils.isNotBlank(this.select))
            uriComponentsBuilder.queryParam("$select", this.select);
        if (StringUtils.isNotBlank(this.where))
            uriComponentsBuilder.queryParam("$where", this.where);
        if (StringUtils.isNotBlank(this.order))
            uriComponentsBuilder.queryParam("$order", this.order);
        if (this.limit != null)
            uriComponentsBuilder.queryParam("$limit", this.limit);

        return uriComponentsBuilder.build();
    }

}
