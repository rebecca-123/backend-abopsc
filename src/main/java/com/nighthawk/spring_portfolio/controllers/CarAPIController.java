package com.nighthawk.spring_portfolio.controllers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // annotation to create a RESTful web services
@RequestMapping("/api/car")  //prefix of API
public class CarAPIController {
    private JSONObject body; //last run result
    private HttpStatus status; //last run status
    String last_run = null; //last run day of month

    // GET Covid 19 Stats
    @GetMapping("/daily")   //added to end of prefix as endpoint
    public ResponseEntity<JSONObject> getCovid() {
        //calls API once a day, sets body and status properties
        String today = new Date().toString().substring(0,10); 
        if (last_run == null || !today.equals(last_run))
        {
            try {  //APIs can fail (ie Internet or Service down)
                // HttpRequest request = HttpRequest.newBuilder()
                //     .uri(URI.create("https://car-data.p.rapidapi.com/cars"))
                //     .header("X-RapidAPI-Key", "df17610e35msh51d75ac58fb44f9p14c5f0jsn7d95a150e08b")
                //     .header("X-RapidAPI-Host", "car-data.p.rapidapi.com")
                //     .method("GET", HttpRequest.BodyPublishers.noBody())
                //     .build();
                // HttpRequest request = HttpRequest.newBuilder()
                //     .uri(URI.create("https://corona-virus-world-and-india-data.p.rapidapi.com/api"))
                //     .header("x-rapidapi-key", "dec069b877msh0d9d0827664078cp1a18fajsn2afac35ae063")
                //     .header("x-rapidapi-host", "corona-virus-world-and-india-data.p.rapidapi.com")
                //     .method("GET", HttpRequest.BodyPublishers.noBody())
                //     .build();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://car-api2.p.rapidapi.com/api/models?sort=id&direction=asc&verbose=yes"))
                    .header("X-RapidAPI-Key", "df17610e35msh51d75ac58fb44f9p14c5f0jsn7d95a150e08b")
                    .header("X-RapidAPI-Host", "car-api2.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
                // HttpRequest request = HttpRequest.newBuilder()
                //     .uri(URI.create("https://car-data.p.rapidapi.com/cars/types"))
                //     .header("X-RapidAPI-Key", "df17610e35msh51d75ac58fb44f9p14c5f0jsn7d95a150e08b")
                //     .header("X-RapidAPI-Host", "car-data.p.rapidapi.com")
                //     .method("GET", HttpRequest.BodyPublishers.noBody())
                //     .build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                //JSONParser extracts text body and parses to JSONObject
                this.body = (JSONObject) new JSONParser().parse(response.body());
                this.status = HttpStatus.OK;  //200 success
                this.last_run = today;
            }
            catch (Exception e) {  //capture failure info
                HashMap<String, String> status = new HashMap<>();
                status.put("status", "RapidApi failure: " + e);

                //Setup object for error
                this.body = (JSONObject) status;
                this.status = HttpStatus.INTERNAL_SERVER_ERROR; //500 error
                this.last_run = null;
            }
        }

        //return JSONObject in RESTful style
        return new ResponseEntity<>(body, status);
    }
}