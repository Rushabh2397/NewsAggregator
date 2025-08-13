package com.rushabh.newsaggregator.controller;

import com.rushabh.newsaggregator.dto.Response.ApiResponse;
import com.rushabh.newsaggregator.dto.Response.NewsApiResponse;
import com.rushabh.newsaggregator.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class NewsController {
    @Autowired
    NewsService newsService;

    @GetMapping("/news")
    public ResponseEntity<ApiResponse<NewsApiResponse>> getNews() {
        NewsApiResponse newsApiResponse = newsService.fetchNews();
        return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Success","List of news article",newsApiResponse));
    }
}
