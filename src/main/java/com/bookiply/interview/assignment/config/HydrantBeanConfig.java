package com.bookiply.interview.assignment.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class HydrantBeanConfig {

    @Bean
    RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bookiply.interview.assignment.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(metaInfo());

    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "Hydrant API's",
                null,
                "1.0",
                "",
                new Contact("Kunal Kumar", null,
                        "kunalkumar284@gmail.com"),
                "",
                ""
        );

        return apiInfo;
    }
}
