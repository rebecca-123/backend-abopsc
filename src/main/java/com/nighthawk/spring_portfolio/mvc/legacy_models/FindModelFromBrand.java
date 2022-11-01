package com.nighthawk.spring_portfolio.mvc.legacy_models;

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

@RestController
@RequestMapping("/api/models") 
public class FindModelFromBrand {
    private JSONObject body; 
    private HttpStatus status; 
    String last_run = null; 

    @GetMapping("/dailyModels")   
    public ResponseEntity<JSONObject> getMakes() {
        String today = new Date().toString().substring(0,10); 
        if (last_run == null || !today.equals(last_run))
        {
            try { 
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://vpic.nhtsa.dot.gov/api/vehicles/getmodelsformake/?format=json"))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                this.body = (JSONObject) new JSONParser().parse(response.body());
                this.status = HttpStatus.OK;  
                this.last_run = today;
            }
            catch (Exception e) {  
                HashMap<String, String> status = new HashMap<>();
                status.put("status", "RapidApi failure: " + e);

                this.body = (JSONObject) status;
                this.status = HttpStatus.INTERNAL_SERVER_ERROR;
                this.last_run = null;
            }
        }

        return new ResponseEntity<>(body, status);
    }
}