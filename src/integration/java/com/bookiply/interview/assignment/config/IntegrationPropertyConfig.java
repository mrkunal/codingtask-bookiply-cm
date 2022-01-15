package com.bookiply.interview.assignment.config;


import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class IntegrationPropertyConfig {

    private static Properties properties;

    static {
        properties = fetchProperties();
    }


    public static Properties fetchProperties() {
        Properties properties = new Properties();
        try {
            File file = ResourceUtils.getFile("classpath:application-test.properties");
            InputStream in = new FileInputStream(file);
            properties.load(in);
        } catch (IOException e) {
        }
        return properties;
    }

    public static String getFirehosesHost() {
        return  properties.getProperty("firehoses.service.host");
    }
}
