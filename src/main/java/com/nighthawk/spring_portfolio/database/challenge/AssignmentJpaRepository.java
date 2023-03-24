package com.nighthawk.spring_portfolio.database.challenge;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AssignmentJpaRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findAllById(Long id);

    List<Assignment> findAllByOrderByIdAsc();

    // Custom JPA query
    @Query(value = "SELECT * FROM Person p WHERE p.name LIKE ?1 or p.email LIKE ?1", nativeQuery = true)
    List<Assignment> findByLikeTermNative(String term);
}
