package com.nighthawk.spring_portfolio.database.lab;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface  CarLabJpaRepository extends JpaRepository<CarLab, Long> {
    CarLab findByName(String name);
    List<CarLab> findAll();
    void deleteById(Long id);
}
