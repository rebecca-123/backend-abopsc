package com.nighthawk.spring_portfolio.database.car;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  CarJpaRepository extends JpaRepository<Car, Long> {
    Car findByName(String name);
}
