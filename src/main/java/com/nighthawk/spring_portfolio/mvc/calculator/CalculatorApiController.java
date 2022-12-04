package com.nighthawk.spring_portfolio.mvc.calculator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/calculator")
public class CalculatorApiController {

    @GetMapping("/{expression}")
    public ResponseEntity<String> getResult(@PathVariable String expression) {
        String result = new Calculator(expression).calcToJSON();
        if (result != null && !result.equals("BAD REQUEST")) {
            return new ResponseEntity<String>(result, HttpStatus.OK);
        }
        
        return new ResponseEntity<String>(result, HttpStatus.BAD_REQUEST);       
    }

}