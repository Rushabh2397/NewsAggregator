package com.rushabh.newsaggregator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppliCationConfig {


    @Value("${news-aggregator-url}")
    private String newsAggregatorUrl;


    @Bean
    public WebClient newsClient(WebClient.Builder builder) {
        return builder
                .baseUrl(newsAggregatorUrl)
                .filter((request, next) -> {
                    System.out.println("Request URL: " + request.url());
                    return next.exchange(request);
                })
                .build();
    }


}
