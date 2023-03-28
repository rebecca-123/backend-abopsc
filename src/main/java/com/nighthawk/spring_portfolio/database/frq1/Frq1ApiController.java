package com.nighthawk.spring_portfolio.database.frq1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

@RestController
@RequestMapping("/api/frq1")
public class Frq1ApiController {

    // private LightBoard lightBoard;
    // private JsonNode json;

    @GetMapping("/{values}")
    public ResponseEntity<JsonNode> generateCarList(@PathVariable String values)
            throws JsonMappingException, JsonProcessingException {
        ArrayList<Integer> carList = new ArrayList<Integer>();

        // Create objectmapper to convert String to JSON
        // ObjectMapper mapper = new ObjectMapper();
        // json = mapper.readTree(carList.toString());

        return ResponseEntity.ok(json);
    }
}
