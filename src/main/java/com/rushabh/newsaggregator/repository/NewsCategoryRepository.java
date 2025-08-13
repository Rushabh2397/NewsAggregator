package com.rushabh.newsaggregator.repository;

import com.rushabh.newsaggregator.entity.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsCategoryRepository extends JpaRepository<NewsCategory,Long> {
}
