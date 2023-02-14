package com.nighthawk.spring_portfolio.mvc.calendar;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.HashMap;

import org.bson.codecs.pojo.annotations.BsonId;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// public class specs {
//     public static void main(String[] args) {
//         try {

//             String makeid = "palceholder"; //makeid
//             // URL url = new URL("https://car-specs.p.rapidapi.com/v2/cars/makes/%7BmakeId%7D/models");
//             URL url = new URL ("https://car-specs.p.rapidapi.com/v2/cars/makes/71295/models"); //puts in makeid into the url
//             HttpURLConnection con = (HttpURLConnection) url.openConnection();

//             // Add Request Headers
//             con.setRequestMethod("GET");
//             con.setRequestProperty("X-RapidAPI-Host", "car-specs.p.rapidapi.com");
//             con.setRequestProperty("X-RapidAPI-Key", "032f716b5amsh1241419d17ff651p1d3c54jsna32e706ab7f6");

//             // Send the GET Request
//             int responseCode = con.getResponseCode();
//             System.out.println("Response Code : " + responseCode);

//             // Read the Response    
//             BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//             String inputLine;
//             StringBuilder response = new StringBuilder();

//             while ((inputLine = in.readLine()) != null) {
//                 response.append(inputLine);
//             }
//             in.close();

//             // Print the Response
//             System.out.println(response.toString());
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }

@RestController // annotation to create a RESTful web services
@RequestMapping("/api/specs")  //prefix of API
public class specs {
    private JSONObject body; //last run result
    private HttpStatus status; //last run status
    String last_run = null; //last run day of month

    // GET Covid 19 Stats
    @GetMapping("/makes")   //added to end of prefix as endpoint
    public ResponseEntity<JSONObject> getMakes() {

        //calls API once a day, sets body and status properties
        // String today = new Date().toString().substring(0,10); 
        // if (last_run == null || !today.equals(last_run)) // this thing gave me problems
        // {
            try {  //APIs can fail (ie Internet or Service down)
                
                //RapidAPI header
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://car-specs.p.rapidapi.com/v2/cars/makes"))
                    .header("x-rapidapi-key", "032f716b5amsh1241419d17ff651p1d3c54jsna32e706ab7f6")
                    .header("x-rapidapi-host", "car-specs.p.rapidapi.com")
                    .build();

                //RapidAPI request and response
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

                //JSONParser extracts text body and parses to JSONObject
                this.body = (JSONObject) new JSONParser().parse(response.body());

                this.status = HttpStatus.OK;  //200 success
                // this.last_run = today; //updates it so when it pulls each day
            }
            catch (Exception e) {  //capture failure info
                HashMap<String, String> status = new HashMap<>();

                status.put("status", "RapidApi failure: " + e);

                //Setup object for error
                // this.body = (JSONObject) status;
                // this.status = HttpStatus.INTERNAL_SERVER_ERROR; //500 error
                this.last_run = null;
            
            }
        // }

        //return JSONObject in RESTful style
        return new ResponseEntity<>(body, status);
    }
    @GetMapping("/models/{id}")
    public ResponseEntity<JSONObject> getModels(@PathVariable String id) {
        // Backend Year Object
        Year year_obj = new Year();
        // year_obj.setStringYear(id);   // updating year object with the year

        // String url = year_obj.getUrl(id); // object generates the URL. Method gets it.


        //calls API once a day, sets body and status properties
        // String today = new Date().toString().substring(0,10); 
        // if (last_run == null || !today.equals(last_run)) // this thing gave me problems
        // {
            try {  //APIs can fail (ie Internet or Service down)
                
                //RapidAPI header
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://car-specs.p.rapidapi.com/v2/cars/makes/" + id + "/models"))
                    .header("x-rapidapi-key", "032f716b5amsh1241419d17ff651p1d3c54jsna32e706ab7f6")
                    .header("x-rapidapi-host", "car-specs.p.rapidapi.com")
                    .build();

                //RapidAPI request and response
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

                //JSONParser extracts text body and parses to JSONObject
                this.body = (JSONObject) new JSONParser().parse(response.body());

                this.status = HttpStatus.OK;  //200 success
                // this.last_run = today; //updates it so when it pulls each day
            }
            catch (Exception e) {  //capture failure info
                HashMap<String, String> status = new HashMap<>();

                status.put("status", "RapidApi failure: " + e);

                //Setup object for error
                // this.body = (JSONObject) status;
                // this.status = HttpStatus.INTERNAL_SERVER_ERROR; //500 error
                this.last_run = null;
            
            }
        // }

        //return JSONObject in RESTful style
        return new ResponseEntity<>(body, status);
    }
}


