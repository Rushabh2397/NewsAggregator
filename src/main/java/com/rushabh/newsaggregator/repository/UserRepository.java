package com.rushabh.newsaggregator.repository;

import com.rushabh.newsaggregator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
