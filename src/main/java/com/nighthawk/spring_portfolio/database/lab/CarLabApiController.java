package com.nighthawk.spring_portfolio.database.lab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.ModelAndView;

import com.nighthawk.spring_portfolio.database.ModelRepository;
import com.nighthawk.spring_portfolio.database.car.CarJpaRepository;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api/carLab/")
public class CarLabApiController {

    @Autowired 
    CarLabJpaRepository carLabJpaRepository;

    // GET List of Cars
    @GetMapping("/")
    public List<CarLab> listAllCars() {
        return carLabJpaRepository.findAll();
    }

    // Compare price API (referenced from lesson)
    @GetMapping("/compareprice")
    public ResponseEntity<String> comparePrice(@RequestParam String car1Name, @RequestParam double car1Price, @RequestParam String car2Name, @RequestParam double car2Price) {
        String cheaperCarName = CarLab.cheaperCar(car1Name, car1Price, car2Name, car2Price);
        return new ResponseEntity<>(cheaperCarName, HttpStatus.OK);
    }

    @GetMapping("/getcheapest")
    public ResponseEntity<String> getCheapest() {
        // use cheaperCar to compare each pair of cars and keep the cheapest one
        List<CarLab> cars = carLabJpaRepository.findAll();
        String cheapestCarName = cars.get(0).getName();
        double cheapestCarPrice = cars.get(0).getPrice();

        for (int i = 1; i < cars.size(); i++) {
            String carName = cars.get(i).getName();
            double carPrice = cars.get(i).getPrice();
            String cheaperCarName = CarLab.cheaperCar(cheapestCarName, cheapestCarPrice, carName, carPrice);
            if (cheaperCarName.equals(carName)) {
                cheapestCarName = carName;
                cheapestCarPrice = carPrice;
            }
        }

        return new ResponseEntity<>(cheapestCarName, HttpStatus.OK);
    }

    // add car
    @PostMapping("/addcar")
    public ResponseEntity<String> addCar(@RequestParam String name, @RequestParam double price) {
        CarLab car = new CarLab(name, price);
        carLabJpaRepository.save(car);
        return new ResponseEntity<>("Car added successfully", HttpStatus.OK);
    }

    // delete car
    @DeleteMapping("/deletecar")
    public ResponseEntity<String> deleteCar(@RequestParam String name) {
        CarLab car = carLabJpaRepository.findByName(name);
        carLabJpaRepository.delete(car);
        return new ResponseEntity<>("Car deleted successfully", HttpStatus.OK);
    }
}