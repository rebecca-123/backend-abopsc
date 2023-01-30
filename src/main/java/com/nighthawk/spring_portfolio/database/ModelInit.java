package com.nighthawk.spring_portfolio.database;

import com.nighthawk.spring_portfolio.database.role.Role;
import com.nighthawk.spring_portfolio.database.role.RoleJpaRepository;
import com.nighthawk.spring_portfolio.database.car.CarJpaRepository;
import com.nighthawk.spring_portfolio.database.person.Person;
import com.nighthawk.spring_portfolio.database.car.Car;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component // Scans Application for ModelInit Bean, this detects CommandLineRunner
public class ModelInit {
    // Inject repositories
    @Autowired RoleJpaRepository roleJpaRepository;
    @Autowired ModelRepository modelRepository;
    @Autowired CarJpaRepository carJpaRepository;

    @Bean
    CommandLineRunner run() {  // The run() method will be executed after the application starts
        return args -> {

            System.out.println("Person Databases Init");

            // ***** Uncomment for Person Database Reset ******


            // String[] roles = {"ROLE_USER", "ROLE_DEALERSHIP", "ROLE_ADMIN", "ROLE_TESTER"};
            // for (String role : roles) {
            //     if (roleJpaRepository.findByName(role) == null)
            //         roleJpaRepository.save(new Role(null, role));
            // }

            // Date dob = new SimpleDateFormat("MM-dd-yyyy").parse("11-07-2005");

            // Person person = new Person("sahilsamar031@gmail.com", "test123", "Sahil Samar", dob, modelRepository.findRole("ROLE_TESTER") );
            // modelRepository.save(person);
                
            // modelRepository.defaults("123querty", "ROLE_USER");

            // modelRepository.addRoleToPerson("sahilsamar031@gmail.com", "ROLE_ADMIN");
            
            // String[] cars = {"Tesla Model 3", "Honda Odyssey"};
            // for (String car : cars) {
            //     if (carJpaRepository.findByName(car) == null)
            //         carJpaRepository.save(new Car(null, car));
            // }
            // modelRepository.addCarToPersonCarList("sahilsamar031@gmail.com", "Tesla Model 3");


        };
    }
}
