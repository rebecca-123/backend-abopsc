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
    CarJpaRepository carJpaRepository;

    // Compare price API (referenced from lesson)
    @GetMapping("/compareprice")
    public ResponseEntity<String> comparePrice(@RequestParam String car1Name, @RequestParam double car1Price, @RequestParam String car2Name, @RequestParam double car2Price) {
        String cheaperCarName = CarLab.cheaperCar(car1Name, car1Price, car2Name, car2Price);
        return new ResponseEntity<>(cheaperCarName, HttpStatus.OK);
    }
}