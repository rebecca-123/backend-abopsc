package com.nighthawk.spring_portfolio.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Range {

    @GetMapping("/range")
    public String range(@RequestParam(name="mpg", required=true, defaultValue="0") int mpg, 
    @RequestParam(name="tank", required=true, defaultValue="0") int tank, Model model) {

        model.addAttribute("mpg", mpg);
        model.addAttribute("tank", tank);

        int range = mpg * tank;
        //model.addAttribute("rangeCalc", range);

        // load HTML VIEW (range.html)
        return "range"; 

    }
    
}
