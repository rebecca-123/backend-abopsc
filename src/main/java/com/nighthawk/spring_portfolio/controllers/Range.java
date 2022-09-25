package com.nighthawk.spring_portfolio.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Range {

    @GetMapping("/range")
    public String range(@RequestParam(name="mpg", required=true, defaultValue="World") String mpg, Model model) {

        // model attributes are visible to Thymeleaf when HTML is "pre-processed"
        model.addAttribute("mpg", mpg);

        // load HTML VIEW (greet.html)
        return "range"; 

    }
    
}
