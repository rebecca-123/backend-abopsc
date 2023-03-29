package com.nighthawk.spring_portfolio.database.problems;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemJpaRepository extends JpaRepository<Problem, Long> {
    
    Optional<Problem> findById();
    List<Problem> findByProblemSet(ProblemSet problemSet);

}
