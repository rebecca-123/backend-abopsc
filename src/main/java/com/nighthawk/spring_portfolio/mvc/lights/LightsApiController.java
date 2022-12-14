package com.nighthawk.spring_portfolio.mvc.lights;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/lights")
public class LightsApiController {
    public LightBoard lightBoard;
    public String result;
    @GetMapping("/") 
    public ResponseEntity<String> getLightData() {
        if (result == null) {
            lightBoard = new LightBoard(5, 5); 
            this.startLights();      
        }
        result = lightBoard.toString();
        if (result != null && !result.equals("BAD REQUEST")) {
            return new ResponseEntity<String>(result, HttpStatus.OK);
        }
        
        return new ResponseEntity<String>(result, HttpStatus.BAD_REQUEST);       
    }

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void startLights() {
        final Runnable decrementer = new Runnable() {
            public void run() { lightBoard.decrementLights(); }
        };
        final ScheduledFuture<?> decrementerHandle =
            scheduler.scheduleAtFixedRate(decrementer, 1, 1, SECONDS);
        scheduler.schedule(new Runnable() {
            public void run() { decrementerHandle.cancel(true); }
        }, 60 * 60, SECONDS);
    }

}