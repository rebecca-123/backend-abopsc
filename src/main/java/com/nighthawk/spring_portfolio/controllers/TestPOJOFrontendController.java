package com.nighthawk.spring_portfolio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // HTTP requests are handled as a controller, using the @Controller annotation
public class TestPOJOFrontendController {

    @GetMapping("/testPOJOFrontend")
    public String testPOJOFrontend() {

        return "testPOJOfrontend";

    }
}

