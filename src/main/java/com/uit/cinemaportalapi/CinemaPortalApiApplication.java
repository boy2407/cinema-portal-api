package com.uit.cinemaportalapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class CinemaPortalApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaPortalApiApplication.class, args);
    }

}
