package com.rushabh.newsaggregator.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

public class AddUserNewsPreference {

    @NotBlank(message = "Lang is required")
    @Pattern(regexp = "English|German|Hindi", message = "Lang must be either English, German, or Hindi")
    private String lang;

    @NotEmpty
    private Set<Long> categories;

    @NotEmpty
    private Set<Long> countries;

    public AddUserNewsPreference() {
    }

    public AddUserNewsPreference(String lang, Set<Long> categories, Set<Long> countries) {
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

    public Set<Long> getCategories() {
        return categories;
    }

    public void setCategories(Set<Long> categories) {
        this.categories = categories;
    }

    public Set<Long> getCountries() {
        return countries;
    }

    public void setCountries(Set<Long> countries) {
        this.countries = countries;
    }
}
