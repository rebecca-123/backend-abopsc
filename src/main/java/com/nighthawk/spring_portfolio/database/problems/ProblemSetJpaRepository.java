package com.nighthawk.spring_portfolio.database.problems;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemSetJpaRepository extends JpaRepository<ProblemSet, Long> {
    Optional<ProblemSet> findById(long id);
}
