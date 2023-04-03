package com.nighthawk.spring_portfolio.database.problems;

import java.util.Optional;

public interface ProblemSetJpaRepository {
    Optional<ProblemSet> findById(long id);
}
