package com.rushabh.newsaggregator.dto.Response;

import com.rushabh.newsaggregator.entity.NewsCategory;
import com.rushabh.newsaggregator.entity.NewsCountry;

import java.util.Set;

public class UserNewsPreferenceResponse {

    String lang;
    Set<NewsCategory> categories;
    Set<NewsCountry> countries;

    public UserNewsPreferenceResponse(String lang, Set<NewsCategory> categories, Set<NewsCountry> countries) {
        this.lang = lang;
        this.categories = categories;
        this.countries = countries;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Set<NewsCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<NewsCategory> categories) {
        this.categories = categories;
    }

    public Set<NewsCountry> getCountries() {
        return countries;
    }

    public void setCountries(Set<NewsCountry> countries) {
        this.countries = countries;
    }
}
