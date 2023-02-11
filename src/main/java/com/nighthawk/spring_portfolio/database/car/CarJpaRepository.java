package com.nighthawk.spring_portfolio.database.car;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface  CarJpaRepository extends JpaRepository<Car, Long> {
    Car findByName(String name);
    List<Car> findAll();
    void deleteById(Long id);
}
