package com.nighthawk.spring_portfolio.database.car;

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
    @GetMapping("delete/{id}")
    public RedirectView deleteCar(@PathVariable long id) {
        repository.deleteCar(id);
        return new RedirectView("https://ad1616.github.io/breadbops-frontend/inventory");
    }

    /*
    POST Aa record by Requesting Parameters from URI
     */
    @PostMapping( "/post/")
    public RedirectView postCar(@RequestParam("name") String name, @RequestParam("imageLink") String imageLink,
                                             @RequestParam("description") String description, @RequestParam("make") String make,
                                             @RequestParam("model") String model, @RequestParam("year") int year) {

        repository.saveCar(new Car(null, name, imageLink, description, make, model, year));
        // return new ResponseEntity<>(name +" is created successfully", HttpStatus.CREATED);
        return new RedirectView("https://ad1616.github.io/breadbops-frontend/inventory");
    }



    @RequestMapping("updateCarInventory/{id}")
    public String carUpdate(@PathVariable("id") int id, Model model) {
        model.addAttribute("car", repository.getCar(id));
        
        return "/carupdate";
    }
    


    /*
    PUT Aa record by Requesting Parameters from URI
     */
    @PutMapping( "updateCar/{id}")
    public ResponseEntity<Object> updateCar(@PathVariable Long id, @RequestParam("name") String name, @RequestParam("imageLink") String imageLink,
                                             @RequestParam("description") String description, @RequestParam("make") String make,
                                             @RequestParam("model") String model, @RequestParam("year") int year) {

        
        Car caredit = repository.getCar(id);
        caredit.setName(name);
        caredit.setImageLink(imageLink);
        caredit.setDescription(description);
        caredit.setMake(make);
        caredit.setModel(model);
        caredit.setYear(year);
        repository.saveCar(caredit);
        return new ResponseEntity<>(name +" is updated successfully", HttpStatus.ACCEPTED);
    }

}