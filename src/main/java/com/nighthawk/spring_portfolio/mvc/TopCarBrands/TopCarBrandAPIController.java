package com.nighthawk.spring_portfolio.mvc.TopCarBrands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Optional;

@RestController // annotation to simplify the creation of RESTful web services
@RequestMapping("/api/topBrands")  // all requests in file begin with this URI
public class TopCarBrandAPIController {

    // Autowired enables Control to connect URI request and POJO Object to easily for Database CRUD operations
    @Autowired
    private BrandsJpaRepository repository;

    /* GET List of Jokes
     * @GetMapping annotation is used for mapping HTTP GET requests onto specific handler methods.
     */
    @GetMapping("/")
    public ResponseEntity<List<CarBrands>> getBrands() {
        // ResponseEntity returns List of Jokes provide by JPA findAll()
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "4000");
        responseHeaders.set("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        System.out.println(responseHeaders);
        ResponseEntity<List<CarBrands>> ent =  new ResponseEntity<>( repository.findAll(), responseHeaders, HttpStatus.OK);
        System.out.println(ent);
        return ent;
        
    }

    /* Update Like
     * @PutMapping annotation is used for mapping HTTP PUT requests onto specific handler methods.
     * @PathVariable annotation extracts the templated part {id}, from the URI
     */
    @PutMapping("/like/{id}")
    public ResponseEntity<CarBrands> setLike(@PathVariable long id) {
        /* 
        * Optional (below) is a container object which helps determine if a result is present. 
        * If a value is present, isPresent() will return true
        * get() will return the value.
        */
        Optional<CarBrands> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            CarBrands brand = optional.get();  // value from findByID
            brand.setLike(brand.getLike()+1); // increment value
            repository.save(brand);  // save entity
            return new ResponseEntity<>(brand, HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Failed HTTP response: status code, headers, and body
    }

    /* Update Jeer
     */
    @PutMapping("/dislike/{id}")
    public ResponseEntity<CarBrands> setDislike (@PathVariable long id) {
        Optional<CarBrands> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            CarBrands brand = optional.get();
            brand.setDislike(brand.getDislike()+1);
            repository.save(brand);
            return new ResponseEntity<>(brand, HttpStatus.OK);
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
