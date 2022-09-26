package com.nighthawk.spring_portfolio.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PowerToWeight {

    @GetMapping("/powertoweight")
    public String powertoweight(@RequestParam(name="horsepower", required=true, defaultValue="0") int horsepower, 
    @RequestParam(name="weight", required=true, defaultValue="0") int weight, Model model) {

        model.addAttribute("horsepower", horsepower);
        model.addAttribute("weight", weight);

        int powertoweight = horsepower * 1.0/ weight;

        // load HTML VIEW (range.html)
        return "powertoweight"; 

    }
    
}
