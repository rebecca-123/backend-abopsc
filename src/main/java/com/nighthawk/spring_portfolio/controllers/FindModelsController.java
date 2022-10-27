package com.nighthawk.spring_portfolio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class FindModelsController {
    @GetMapping("/models")
    public String models() {

        return "FindModels";

    }
}
