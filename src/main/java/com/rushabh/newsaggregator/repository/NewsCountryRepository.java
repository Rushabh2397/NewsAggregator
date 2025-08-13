package com.rushabh.newsaggregator.repository;

import com.rushabh.newsaggregator.entity.NewsCountry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsCountryRepository extends JpaRepository<NewsCountry,Long> {
}
