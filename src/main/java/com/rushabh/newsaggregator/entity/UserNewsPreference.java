package com.rushabh.newsaggregator.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_news_preference")
public class UserNewsPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private String lang;

    @ManyToMany
    @JoinTable(
            name = "user_preference_news_categories",
            joinColumns = @JoinColumn(name = "user_news_preference_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<NewsCategory>  categories = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "user_preference_news_countries",
            joinColumns = @JoinColumn(name = "user_news_preference_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private Set<NewsCountry>  countries = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;


    @Column(name = "created_at", updatable = false,insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",insertable = false)
    private LocalDateTime updatedAt;

    UserNewsPreference(){}

    public UserNewsPreference(String lang) {
        this.lang = lang;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserNewsPreference{" +
                "id=" + Id +
                ", lang='" + lang + '\'' +
                ", userId=" + (user != null ? user.getId() : null) +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", categories=" + categories +
                ", countries=" + countries +
                '}';
    }
}
