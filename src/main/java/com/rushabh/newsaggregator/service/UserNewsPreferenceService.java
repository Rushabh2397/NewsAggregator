package com.rushabh.newsaggregator.service;

import com.rushabh.newsaggregator.dto.Request.AddUserNewsPreference;
import com.rushabh.newsaggregator.dto.Response.UserNewsPreferenceResponse;
import com.rushabh.newsaggregator.entity.NewsCategory;
import com.rushabh.newsaggregator.entity.NewsCountry;
import com.rushabh.newsaggregator.entity.User;
import com.rushabh.newsaggregator.entity.UserNewsPreference;
import com.rushabh.newsaggregator.repository.NewsCategoryRepository;
import com.rushabh.newsaggregator.repository.NewsCountryRepository;
import com.rushabh.newsaggregator.repository.UserNewsPreferenceRepository;
import com.rushabh.newsaggregator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserNewsPreferenceService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserNewsPreferenceRepository userNewsPreferenceRepository;

    @Autowired
    NewsCategoryRepository newsCategoryRepository;

    @Autowired
    NewsCountryRepository newsCountryRepository;

    public UserNewsPreferenceResponse addUserNewsPreference(AddUserNewsPreference req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getPrincipal().toString();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found!"));

        List<NewsCategory> newsCategories = newsCategoryRepository.findAllById(req.getCategories());
        if (req.getCategories().size() != newsCategories.size()) {
            throw new RuntimeException("One or more category IDs are invalid: " + req.getCategories());
        }

        List<NewsCountry> newsCountries = newsCountryRepository.findAllById(req.getCountries());

        if (req.getCountries().size() != newsCountries.size()) {
            throw new RuntimeException("One or more countries ID are invalid: " + req.getCountries());
        }

        UserNewsPreference preference = new UserNewsPreference(
                req.getLang()
        );

        preference.getCategories().addAll(newsCategories);
        preference.getCountries().addAll(newsCountries);
        preference.setUser(user);
        UserNewsPreference savedUserPreference = userNewsPreferenceRepository.save(preference);

        return new UserNewsPreferenceResponse(
                savedUserPreference.getLang(),
                savedUserPreference.getCategories(),
                savedUserPreference.getCountries()
        );
    }

    public UserNewsPreferenceResponse fetchUserNewsPreference() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getPrincipal().toString();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found!"));

        UserNewsPreference preference = userNewsPreferenceRepository.findByUserId(user.getId()).orElseThrow(() -> new RuntimeException("User news preference has not been added!"));

        return new UserNewsPreferenceResponse(
                preference.getLang(),
                preference.getCategories(),
                preference.getCountries()
        );
    }

    public UserNewsPreferenceResponse updateUserNewsPreference(AddUserNewsPreference req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getPrincipal().toString();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        UserNewsPreference preference = userNewsPreferenceRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("User news preference has not been added yet!"));

        List<NewsCategory> newsCategories = newsCategoryRepository.findAllById(req.getCategories());
        if (req.getCategories().size() != newsCategories.size()) {
            throw new RuntimeException("One or more category IDs are invalid: " + req.getCategories());
        }

        List<NewsCountry> newsCountries = newsCountryRepository.findAllById(req.getCountries());
        if (req.getCountries().size() != newsCountries.size()) {
            throw new RuntimeException("One or more countries IDs are invalid: " + req.getCountries());
        }

        preference.setLang(req.getLang());
        preference.getCategories().clear();
        preference.getCategories().addAll(newsCategories);

        preference.getCountries().clear();
        preference.getCountries().addAll(newsCountries);

        UserNewsPreference updatedPreference = userNewsPreferenceRepository.save(preference);

        return new UserNewsPreferenceResponse(
                updatedPreference.getLang(),
                updatedPreference.getCategories(),
                updatedPreference.getCountries()
        );
    }

}
