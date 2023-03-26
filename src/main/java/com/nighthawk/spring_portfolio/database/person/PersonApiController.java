package com.nighthawk.spring_portfolio.database.person;

import org.hibernate.validator.internal.util.logging.formatter.ObjectArrayFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import com.nighthawk.spring_portfolio.database.ModelRepository;
import com.nighthawk.spring_portfolio.database.grading.Assignment;
import com.nighthawk.spring_portfolio.database.grading.AssignmentJpaRepository;
import com.nighthawk.spring_portfolio.database.grading.Grade;
import com.nighthawk.spring_portfolio.database.grading.GradeJpaRepository;
import com.nighthawk.spring_portfolio.database.role.RoleJpaRepository;

import java.util.*;

import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api/person")
public class PersonApiController {
    /*
     * #### RESTful API ####
     * Resource: https://spring.io/guides/gs/rest-service/
     */

    // Autowired enables Control to connect HTML and POJO Object to database easily
    // for CRUD
    @Autowired
    private ModelRepository repository;

    // Individual Repositories for People, Grades & Assignments
    // didn't want to mess up ModelRepository so i didn't add assignment/grade repos
    // there
    @Autowired
    private PersonJpaRepository personRepository;

    @Autowired
    private AssignmentJpaRepository assignmentRepository;

    @Autowired
    private GradeJpaRepository gradeRepository;

    /*
     * GET List of People
     */
    @GetMapping("/all")
    public ResponseEntity<List<Person>> getPeople() {
        return new ResponseEntity<>(personRepository.findAllByOrderByNameAsc(), HttpStatus.OK);
    }

    /*
     * GET individual Person using ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPerson(@PathVariable long id) {
        Person person = personRepository.findById(id).orElse(null);
        if (person.equals(null)) {
            return new ResponseEntity<>("person not found", HttpStatus.OK);
        }

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    /*
     * DELETE individual Person using ID
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable long id) {
        Person person = personRepository.findById(id).orElse(null);
        if (person.equals(null)) {
            return new ResponseEntity<>("person not found", HttpStatus.OK);
        }

        List<Grade> grades = gradeRepository.findAllByPerson(person);

        for (Grade grade : grades) {
            gradeRepository.delete(grade);
        }

        personRepository.delete(person);

        return new ResponseEntity<>("" + id + " and all relevant grades deleted", HttpStatus.OK);
    }

    /*
     * POST Aa record by Requesting Parameters from URI
     */
    @PostMapping("/post/")
    public ResponseEntity<Object> postPerson(@RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("dob") String dobString) {

        Date dob;
        try {
            dob = new SimpleDateFormat("MM-dd-yyyy").parse(dobString);
        } catch (Exception e) {
            return new ResponseEntity<>(dobString + " error; try MM-dd-yyyy", HttpStatus.BAD_REQUEST);
        }

        // A person object WITHOUT ID will create a new record with default roles as
        // student
        Person person = new Person(email, password, name, dob, repository.findRole("ROLE_USER"));
        personRepository.save(person);

        List<Assignment> assignments = assignmentRepository.findAllByOrderByIdAsc();

        for (Assignment assignment : assignments) {
            Grade grade = new Grade(assignment, person);
            gradeRepository.save(grade);
        }

        return new ResponseEntity<>(email + " is created successfully", HttpStatus.CREATED);
    }

    /*
     * The personSearch API looks across database for partial match to term (k,v)
     * passed by RequestEntity body
     */
    // @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<Object> personSearch(@RequestBody final Map<String,
    // String> map) {
    // // extract term from RequestEntity
    // String term = (String) map.get("term");

    // // custom JPA query to filter on term
    // List<Person> list = personRepository.listLikeNative(term);

    // // return resulting list and status, error checking should be added
    // return new ResponseEntity<>(list, HttpStatus.OK);
    // }

    @GetMapping("/getPersonRoles")
    public ResponseEntity<?> getPersonRoles(@RequestParam("email") String email) {
        Person person = personRepository.findByEmail(email);
        return new ResponseEntity<>(person.getRoles(), HttpStatus.OK);
    }

    @GetMapping("/getPersonName")
    public ResponseEntity<?> getPersonName(@RequestParam("email") String email) {
        Person person = personRepository.findByEmail(email);
        return new ResponseEntity<>(person.getName(), HttpStatus.OK);
    }

    @GetMapping("/getPersonAge")
    public ResponseEntity<?> getPersonAge(@RequestParam("email") String email) {
        Person person = personRepository.findByEmail(email);
        return new ResponseEntity<>(person.getAge(), HttpStatus.OK);
    }

    // Method to call addCarToPersonCarList from ModelRepository to add a car to a
    // person's car list, taking email and car name as parameters from URI
    // @PostMapping(value = "/addCar", produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<Object> addCarToPersonCarList(@RequestParam("email")
    // String email,
    // @RequestParam("carName") String carName) {
    // // custom JPA query to filter on term
    // repository.addCarToPersonCarList(email, carName);

    // // return resulting list and status
    // return new ResponseEntity<>("Car added to person's car list", HttpStatus.OK);
    // }

    // @GetMapping(value = "/deleteCar", produces =
    // MediaType.APPLICATION_JSON_VALUE)
    // public RedirectView deleteCarFromPersonCarList(@RequestParam("email") String
    // email,
    // @RequestParam("carName") String carName) {
    // repository.deleteCarFromPersonCarList(email, carName);

    // return new
    // RedirectView("https://ad1616.github.io/breadbops-frontend/carlist");

    // }

}