package com.nighthawk.spring_portfolio.mvc.TopCarBrands;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


@Component // Scans Application for ModelInit Bean, this detects CommandLineRunner
public class BrandsInit {
    
    // Inject repositories
    @Autowired BrandsJpaRepository repository;
    private JSONObject body;
    private Object status;
    
    @Bean
    CommandLineRunner run() {  // The run() method will be executed after the application starts
        return args -> {
            ArrayList<String> brandsArray = new ArrayList<String>();
            try { 
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://vpic.nhtsa.dot.gov/api/vehicles/getallmakes?format=json"))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                this.body = (JSONObject) new JSONParser().parse(response.body());
                this.status = HttpStatus.OK;  
            }
            catch (Exception e) {  
                HashMap<String, String> status = new HashMap<>();
                status.put("status", "RapidApi failure: " + e);

                this.body = (JSONObject) status;
                this.status = HttpStatus.INTERNAL_SERVER_ERROR;
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
                    brandsArray.add(arr[1]);
                }
                //System.out.println(obj.get(5));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // Fail safe data validations

            // final String[] brandsArray = {
            //     "Porsche",
            //     "Tesla",
            //     "Kia",
            //     "Honda",
            //     "Jaguar",
            //     "Mazda",
            //     "Volvo",
            //     "Toyota",
            //     "Hyundai",
            //     "BMW",
            //     "Lexus",
            //     "Nissan",
            //     "Audi",
            //     "Volkswagen"
            // };

            // make sure Joke database is populated with starting jokes
            for (String brand : brandsArray) {
                List<CarBrands> test = repository.findByBrandIgnoreCase(brand);  // JPA lookup
                if (test.size() == 0)
                    repository.save(new CarBrands(null, brand, 0, 0)); //JPA save
            }
            
        };
    }
}

