package com.nighthawk.spring_portfolio.database.review;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface  ReviewJpaRepository extends JpaRepository<Review, Long> {
    Review findByName(String name);
    List<Review> findAll();
}
