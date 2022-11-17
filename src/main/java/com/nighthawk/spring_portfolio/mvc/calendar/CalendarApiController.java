package com.nighthawk.spring_portfolio.mvc.calendar;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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

/** Calendar API
 * Calendar Endpoint: /api/calendar/isLeapYear/2022, Returns: {"year":2020,"isLeapYear":false}
 */
@RestController
@RequestMapping("/api/calendar")
public class CalendarApiController {

    public JSONObject body;
    public HttpStatus status;

    /** GET isLeapYear endpoint
     * ObjectMapper throws exceptions on bad JSON
     *  @throws JsonProcessingException
     *  @throws JsonMappingException
     */
    @GetMapping("/isLeapYear/{year}")
    public ResponseEntity<JsonNode> getIsLeapYear(@PathVariable int year) throws JsonMappingException, JsonProcessingException {
      // Backend Year Object
      Year year_obj = new Year();
      year_obj.setYear(year);  // evaluates Leap Year

      // Turn Year Object into JSON
      ObjectMapper mapper = new ObjectMapper(); 
      JsonNode json = mapper.readTree(year_obj.isLeapYearToString()); // this requires exception handling

      return ResponseEntity.ok(json);  // JSON response, see ExceptionHandlerAdvice for throws
    }

    // add other methods
    @GetMapping("/fetchCars/{year}")
    public ResponseEntity<JSONObject> getUrl(@PathVariable String year) {
        // Backend Year Object
        Year year_obj = new Year();
        year_obj.setStringYear(year);  // evaluates Leap Year

        String url = year_obj.getUrl(year);

        System.out.println(url);


        try {  //APIs can fail (ie Internet or Service down)
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            //JSONParser extracts text body and parses to JSONObject
            this.body = (JSONObject) new JSONParser().parse(response.body());
            
            this.status = HttpStatus.OK;  //200 success
        }
        catch (Exception e) {  //capture failure info
            HashMap<String, String> status = new HashMap<>();
            status.put("status", "failure: " + e);

            //Setup object for error
            this.status = HttpStatus.INTERNAL_SERVER_ERROR; //500 error
        }

        //return JSONObject in RESTful style
        return new ResponseEntity<>(body, status);
    }
}