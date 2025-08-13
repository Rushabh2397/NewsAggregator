package com.rushabh.newsaggregator.service;

import com.rushabh.newsaggregator.dto.Response.NewsApiResponse;
import com.rushabh.newsaggregator.entity.NewsCategory;
import com.rushabh.newsaggregator.entity.NewsCountry;
import com.rushabh.newsaggregator.entity.User;
import com.rushabh.newsaggregator.entity.UserNewsPreference;
import com.rushabh.newsaggregator.repository.UserNewsPreferenceRepository;
import com.rushabh.newsaggregator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.rushabh.newsaggregator.constants.NewsLangConstant.NEWS_LANG;

@Service
public class NewsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserNewsPreferenceRepository userNewsPreferenceRepository;

    @Autowired
    WebClient client;

    @Value("${news-aggregator-api-key}")
    private String apiKey;


    public NewsApiResponse fetchNews() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getPrincipal().toString();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        UserNewsPreference preference = userNewsPreferenceRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("User news preference has not been added yet!"));

        NewsCategory firstCategory = preference.getCategories().iterator().next();
        NewsCountry  firstCountry = preference.getCountries().iterator().next();
        String lang = NEWS_LANG.get(preference.getLang());

        System.out.println(lang+" "+firstCategory.getVal()+" "+ firstCountry.getVal());


        NewsApiResponse newsApiResponseMono = client.get().uri(uriBuilder ->
                        uriBuilder
                                .path("/top-headlines")
                                .queryParam("category", firstCategory.getVal())
                                .queryParam("lang", lang)
                                .queryParam("country", firstCountry.getVal())
                                .queryParam("apikey", apiKey)
                                .build())
                .retrieve()
                .bodyToMono(NewsApiResponse.class)
                .onErrorMap(Exception.class, ex ->
                        new RuntimeException("Failed to fetch news: " + ex.getMessage(), ex)
                ).block();
        return  newsApiResponseMono;
    }
}
