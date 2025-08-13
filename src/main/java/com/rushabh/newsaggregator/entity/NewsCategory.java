package com.rushabh.newsaggregator.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "news_categories")
public class NewsCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "display_name")
    private String displayName;

    private String val;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private Set<UserNewsPreference> userNewsPreferences = new HashSet<>();

    public NewsCategory(){}

    public NewsCategory(String displayName, String val) {
        this.displayName = displayName;
        this.val = val;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public Set<UserNewsPreference> getUserNewsPreferences() {
        return userNewsPreferences;
    }

    public void setUserNewsPreferences(Set<UserNewsPreference> userNewsPreferences) {
        this.userNewsPreferences = userNewsPreferences;
    }
}
