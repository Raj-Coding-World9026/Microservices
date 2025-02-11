package com.microservice.comment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
//RestTemplate class an help us interact with other project
//to create RestTemplate object you need to create in @Springboot anotation with @Bean anotation or
//make a separate configuration file