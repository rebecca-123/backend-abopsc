package com.nighthawk.spring_portfolio.mvc.CarPOJO;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarJpaRepository extends JpaRepository<Car, Long> {
    void save(String Car);

    List<Car> findAllByOrderByCarAsc(); 
    List<Car> findByCarIgnoreCase(String Car); 
}

