package com.nighthawk.spring_portfolio.mvc.calendar.carapi;

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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class specs {
    public static void main(String[] args) {
        try {

            String makeid = "palceholder"; //makeid
            // URL url = new URL("https://car-specs.p.rapidapi.com/v2/cars/makes/%7BmakeId%7D/models");
            URL url = new URL ("https://car-specs.p.rapidapi.com/v2/cars/makes/" +  makeid + "/models"); //puts in makeid into the url
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // Add Request Headers
            con.setRequestMethod("GET");
            con.setRequestProperty("X-RapidAPI-Host", "car-specs.p.rapidapi.com");
            con.setRequestProperty("X-RapidAPI-Key", "032f716b5amsh1241419d17ff651p1d3c54jsna32e706ab7f6");

            // Send the GET Request
            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            // Read the Response    
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print the Response
            System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// @RestController // annotation to create a RESTful web services
// @RequestMapping("/api/specs")  //prefix of API
// public class specs {
//     private JSONObject body; //last run result
//     private HttpStatus status; //last run status
//     String last_run = null; //last run day of month

//     @GetMapping("/isLeapYear/{year}")
//     public ResponseEntity<JsonNode> getIsLeapYear(@PathVariable int year) throws JsonMappingException, JsonProcessingException {
//       // Backend Year Object
//       Year year_obj = new Year();
//       year_obj.setYear(year);  // evaluates Leap Year

//       // Turn Year Object into JSON
//       ObjectMapper mapper = new ObjectMapper(); 
//       JsonNode json = mapper.readTree(year_obj.isLeapYearToString()); // this requires exception handling

//       return ResponseEntity.ok(json);  // JSON response, see ExceptionHandlerAdvice for throws
//     }
//     // GET Covid 19 Stats
//     @GetMapping("/fetchCars/{make}/{year}")
//     public ResponseEntity<JSONObject> getUrl(@PathVariable String make, @PathVariable String year) {
//         // Backend Year Object
//         Year year_obj = new Year();
//         year_obj.setStringYear(year, make);  // updating year object with the year

//         String url = year_obj.getUrl(year, make); // object generates the URL. Method gets it.

//         // API Call
//             try {  //APIs can fail (ie Internet or Service down)
//                      String makeid = "palceholder"; //makeid

//                 //RapidAPI header
//                 HttpRequest request = HttpRequest.newBuilder()
//                     .uri(URI.create("https://car-specs.p.rapidapi.com/v2/cars/makes/" + makeid + "/models")) //only shows for the models, figure out how to use the database into it
//                     .header("x-rapidapi-key", "032f716b5amsh1241419d17ff651p1d3c54jsna32e706ab7f6")
//                     .header("x-rapidapi-host", "car-specs.p.rapidapi.com")
//                     .method("GET", HttpRequest.BodyPublishers.noBody())
//                     .build();

//                 //RapidAPI request and response
//                 HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()); 
//                 //JSONParser extracts text body and parses to JSONObject
//                 this.body = (JSONObject) new JSONParser().parse(response.body());
//                 this.status = HttpStatus.OK;  //200 success
//                 this.last_run = today;
//             }
//             catch (Exception e) {  //capture failure info
//                 HashMap<String, String> status = new HashMap<>();
//                 status.put("status", "RapidApi failure: " + e);

//                 //Setup object for error
//                 this.body = (JSONObject) status;
//                 this.status = HttpStatus.INTERNAL_SERVER_ERROR; //500 error
//                 this.last_run = null;
//             }
//         }

//         //return JSONObject in RESTful style
//         return new ResponseEntity<>(body, status);
//     }
// }


// @RestController
// @RequestMapping("/api/calendar")
// public class CalendarApiController {

//     public JSONObject body;
//     public HttpStatus status;

//     /** GET isLeapYear endpoint
//      * ObjectMapper throws exceptions on bad JSON
//      *  @throws JsonProcessingException
//      *  @throws JsonMappingException
//      */
//     @GetMapping("/isLeapYear/{year}")
//     public ResponseEntity<JsonNode> getIsLeapYear(@PathVariable int year) throws JsonMappingException, JsonProcessingException {
//       // Backend Year Object
//       Year year_obj = new Year();
//       year_obj.setYear(year);  // evaluates Leap Year

//       // Turn Year Object into JSON
//       ObjectMapper mapper = new ObjectMapper(); 
//       JsonNode json = mapper.readTree(year_obj.isLeapYearToString()); // this requires exception handling

//       return ResponseEntity.ok(json);  // JSON response, see ExceptionHandlerAdvice for throws
//     }

//     // method for getting the cars in an inputted year
//     @GetMapping("/fetchCars/{brand}/{year}")
//     public ResponseEntity<JSONObject> getUrl(@PathVariable String brand, @PathVariable String year) {
//         // Backend Year Object
//         Year year_obj = new Year();
//         year_obj.setStringYear(year, brand);  // updating year object with the year

//         String url = year_obj.getUrl(year, brand); // object generates the URL. Method gets it.

//         // API Call
//         try {  
//             HttpRequest request = HttpRequest.newBuilder()
//                 .uri(URI.create(url))
//                 .method("GET", HttpRequest.BodyPublishers.noBody())
//                 .build();
//             HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//             this.body = (JSONObject) new JSONParser().parse(response.body());
            
//             this.status = HttpStatus.OK; 
//         }
//         catch (Exception e) { 
//             HashMap<String, String> status = new HashMap<>();
//             status.put("status", "failure: " + e);

//             this.status = HttpStatus.INTERNAL_SERVER_ERROR; 
//         }

//         //return JSONObject
//         return new ResponseEntity<>(body, status);
//     }
// }