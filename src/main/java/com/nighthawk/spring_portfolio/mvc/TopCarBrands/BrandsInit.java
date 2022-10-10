package com.nighthawk.spring_portfolio.mvc.TopCarBrands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component // Scans Application for ModelInit Bean, this detects CommandLineRunner
public class BrandsInit {
    
    // Inject repositories
    @Autowired BrandsJpaRepository repository;
    
    @Bean
    CommandLineRunner run() {  // The run() method will be executed after the application starts
        return args -> {
            // Fail safe data validations

            // starting jokes
            final String[] brandsArray = {
                "Porsche",
                "Tesla",
                "Kia",
                "Honda",
                "Jaguar",
                "Mazda",
                "Volvo",
                "Toyota",
                "Hyundai",
                "BMW",
                "Lexus",
                "Nissan",
                "Audi",
                "Volkswagen"
            };

            // make sure Joke database is populated with starting jokes
            for (String brand : brandsArray) {
                List<CarBrands> test = repository.findByBrandIgnoreCase(brand);  // JPA lookup
                if (test.size() == 0)
                    repository.save(new CarBrands(null, brand, 0, 0)); //JPA save
            }
            
        };
    }
}

