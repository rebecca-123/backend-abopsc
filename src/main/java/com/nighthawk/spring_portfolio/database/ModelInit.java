package com.nighthawk.spring_portfolio.database;

import com.nighthawk.spring_portfolio.database.role.Role;
import com.nighthawk.spring_portfolio.database.role.RoleJpaRepository;
import com.nighthawk.spring_portfolio.database.car.CarJpaRepository;
import com.nighthawk.spring_portfolio.database.person.Person;
import com.nighthawk.spring_portfolio.database.person.PersonJpaRepository;
import com.nighthawk.spring_portfolio.database.car.Car;
import com.nighthawk.spring_portfolio.database.car.CarApiController;
import com.nighthawk.spring_portfolio.database.lab.CarLab;
import com.nighthawk.spring_portfolio.database.lab.CarLabJpaRepository;

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
    @Autowired PersonJpaRepository personJpaRepository;
    @Autowired CarApiController carApiController;
    @Autowired CarLabJpaRepository carLabJpaRepository;

    @Bean
    CommandLineRunner run() {  // The run() method will be executed after the application starts
        return args -> {

            System.out.println("Person Databases Init");


            String[] roles = {"ROLE_USER", "ROLE_ADMIN", "ROLE_ANONYMOUS"};
            for (String role : roles) {
                if (roleJpaRepository.findByName(role) == null)
                    roleJpaRepository.save(new Role(null, role));
            }

            Date dob1 = new SimpleDateFormat("MM-dd-yyyy").parse("11-07-2005");
            Date dob2 = new SimpleDateFormat("MM-dd-yyyy").parse("08-11-2005");

            String email1 = "sahilsamar031@gmail.com";
            String email2 = "samuel@gmail.com";

            Person person1 = new Person(email1, "123qwerty", "Sahil Samar", dob1, modelRepository.findRole("ROLE_ADMIN") );
            Person person2 = new Person(email2, "123qwerty", "Samuel Wang", dob2, modelRepository.findRole("ROLE_ADMIN") );

            if (personJpaRepository.findByEmail(email1) == null) {
                modelRepository.save(person1);
            }
            
            if (personJpaRepository.findByEmail(email2) == null) {
                modelRepository.save(person2);
            }

            Car car1 = new Car("Toyota Camry", "Car", "Toyota", "Camry", 2000, 10.7);

            if (carJpaRepository.findByName("Toyota Camry") == null) {
                modelRepository.saveCar(car1);
            }

            Car car2 = new Car("Toyota Corolla", "Car", "Toyota", "Corolla", 2002, 12.5);

            if (carJpaRepository.findByName("Toyota Corolla") == null) {
                modelRepository.saveCar(car2);
            }

            carApiController.iterationExample();
            carApiController.objectExample();
            carApiController.booleanExample();
                
            System.out.println("Car Lab Databases Init");

            CarLab test = new CarLab("Toyota Camry", 2000000);
            if (carLabJpaRepository.findByName("Toyota Camry") == null) {
                carLabJpaRepository.save(test);
            }
            CarLab test2 = new CarLab("Toyota Corolla", 3000000);
            if (carLabJpaRepository.findByName("Toyota Corolla") == null) {
                carLabJpaRepository.save(test2);
            }
        };
    }
}