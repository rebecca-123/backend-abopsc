package com.nighthawk.spring_portfolio.database.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nighthawk.spring_portfolio.database.ModelRepository;
import com.nighthawk.spring_portfolio.database.car.CarJpaRepository;

import java.util.*;

import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api/carInventory/")
public class CarApiController {

    @Autowired
    private ModelRepository repository;
    @Autowired 
    CarJpaRepository carJpaRepository;

    /*
    GET List of Cars
     */
    @GetMapping("all")
    public ResponseEntity<List<Car>> getAllCars() {
        return new ResponseEntity<>( repository.listAllCars(), HttpStatus.OK);
    }

    /*
    GET individual Car using ID
     */
    @GetMapping("{id}")
    public ResponseEntity<Car> getCar(@PathVariable long id) {
        return new ResponseEntity<>( repository.getCar(id), HttpStatus.OK);
    }

    /*
    DELETE individual Car using ID
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteCar(@PathVariable long id) {
        repository.deleteCar(id);
        return new ResponseEntity<>( ""+ id +" deleted", HttpStatus.OK);
    }

    /*
    POST Aa record by Requesting Parameters from URI
     */
    @PostMapping( "/post/")
    public ResponseEntity<Object> postPerson(@RequestParam("name") String name, @RequestParam("imageLink") String imageLink,
                                             @RequestParam("description") String description, @RequestParam("make") String make,
                                             @RequestParam("model") String model, @RequestParam("year") int year) {

        repository.saveCar(new Car(null, name, imageLink, description));
        return new ResponseEntity<>(name +" is created successfully", HttpStatus.CREATED);
    }


}