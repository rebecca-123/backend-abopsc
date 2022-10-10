package com.nighthawk.spring_portfolio.mvc.TopCarBrands;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

// JPA is an object-relational mapping (ORM) to persistent data, originally relational databases (SQL). Today JPA implementations has been extended for NoSQL.
public interface BrandsJpaRepository extends JpaRepository<CarBrands, Long> {
    // JPA has many built in methods, these few have been prototyped for this application
    void save(String Brand);

    // A
    List<CarBrands> findAllByOrderByBrandAsc();  // returns a List of Jokes in Ascending order
    List<CarBrands> findByBrandIgnoreCase(String brand);  // look to see if Joke(s) exist
}
