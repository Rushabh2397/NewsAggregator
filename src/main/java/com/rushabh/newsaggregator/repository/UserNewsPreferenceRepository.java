package com.rushabh.newsaggregator.repository;

import com.rushabh.newsaggregator.entity.UserNewsPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserNewsPreferenceRepository extends JpaRepository<UserNewsPreference,Long> {
    Optional<UserNewsPreference> findByUserId(Long userId);
}
