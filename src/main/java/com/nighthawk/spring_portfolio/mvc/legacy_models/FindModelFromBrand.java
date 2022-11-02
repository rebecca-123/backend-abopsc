package com.nighthawk.spring_portfolio.mvc.legacy_models;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
                    .uri(URI.create("https://vpic.nhtsa.dot.gov/api/vehicles/getallmakes?format=json"))
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
        JSONParser parser = new JSONParser();
        String jsonData = body.get("Results").toString();
        try {
            JSONArray obj = (JSONArray) parser.parse(jsonData);
            Object obj2;
            String temp;
            String[] arr = null;
            for (int i=0;i<obj.size(); i++) {
                temp = "";
                obj2 = parser.parse(obj.get(i).toString());
                temp = obj2.toString();
                arr = temp.split("Make_Name\":");
                arr = arr[1].split("}");
                arr = arr[0].split("\"");
                System.out.println(arr[1]);
            }
            //System.out.println(obj.get(5));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // System.out.println(jsonData);


        return new ResponseEntity<>(body, status);
    }
}