// package com.nighthawk.spring_portfolio.database.role;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.*;
// import java.text.SimpleDateFormat;

// @RestController
// @RequestMapping("/api/person")
// public class RoleApi {
//     /*
//     #### RESTful API ####
//     Resource: https://spring.io/guides/gs/rest-service/
//     */

//     // Autowired enables Control to connect POJO Object through JPA
//     @Autowired
//     private RoleJpaRepository repository;

//     /*
//     GET List of People
//      */
//     // @GetMapping("/")
//     // public ResponseEntity<List<Role>> getPeople() {
//     //     return new ResponseEntity<>( repository.listAll(), HttpStatus.OK);
//     // }

//     /*
//     GET individual Person using ID
//      */
//     @GetMapping("/{id}")
//     public ResponseEntity<Role> getPerson(@PathVariable long id) {
//         Optional<Role> optional = repository.findById(id);
//         if (optional.isPresent()) {  // Good ID
//             Role person = optional.get();  // value from findByID
//             return new ResponseEntity<>(person, HttpStatus.OK);  // OK HTTP response: status code, headers, and body
//         }
//         // Bad ID
//         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);       
//     }

//     /*
//     DELETE individual Person using ID
//      */
//     @DeleteMapping("/delete/{id}")
//     public ResponseEntity<Role> deletePerson(@PathVariable long id) {
//         Optional<Role> optional = repository.findById(id);
//         if (optional.isPresent()) {  // Good ID
//             Role person = optional.get();  // value from findByID
//             repository.deleteById(id);  // value from findByID
//             return new ResponseEntity<>(person, HttpStatus.OK);  // OK HTTP response: status code, headers, and body
//         }
//         // Bad ID
//         return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
//     }

//     /*
//     POST Aa record by Requesting Parameters from URI
//      */
//     @PostMapping( "/post")
//     public ResponseEntity<Object> postPerson(@RequestParam("email") String email,
//                                              @RequestParam("password") String password,
//                                              @RequestParam("name") String name,
//                                              @RequestParam("dob") Date dob,
//                                              @RequestParam("role") Role role) {

//         // A person object WITHOUT ID will create a new record with default roles as student
//         Role person = new Role(email, password, name, dob, role);
//         repository.save(person);
//         return new ResponseEntity<>(email +" is created successfully", HttpStatus.CREATED);
//     }

//     /*
//     The personSearch API looks across database for partial match to term (k,v) passed by RequestEntity body
//      */
//     @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
//     public ResponseEntity<Object> personSearch(@RequestBody final Map<String,String> map) {
//         // extract term from RequestEntity
//         String term = (String) map.get("term");

//         // return resulting list and status, error checking should be added
//         return new ResponseEntity<>(list, HttpStatus.OK);
//     }

    
// }
