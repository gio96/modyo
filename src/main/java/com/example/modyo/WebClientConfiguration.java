package com.example.modyo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfiguration {


    @Bean
    WebClient webClient(WebClient.Builder b) {
        return b.build();
    }

}
