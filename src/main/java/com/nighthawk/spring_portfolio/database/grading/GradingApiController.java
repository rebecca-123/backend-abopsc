package com.nighthawk.spring_portfolio.database.grading;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import com.nighthawk.spring_portfolio.database.person.Person;
import com.nighthawk.spring_portfolio.database.person.PersonJpaRepository;

import java.util.*;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api/grading")
public class GradingApiController {

    @Autowired
    private PersonJpaRepository personRepository;

    @Autowired
    private AssignmentJpaRepository assignmentRepository;

    @Autowired
    private GradeJpaRepository gradeRepository;

    /*
     * GET List of Assignments
     */
    @GetMapping("/assignments")
    public ResponseEntity<List<Assignment>> getAssignments() {
        return new ResponseEntity<>(assignmentRepository.findAllByOrderByIdAsc(), HttpStatus.OK);
    }

    /*
     * GET List of Grades
     */
    @GetMapping("/grades")
    public ResponseEntity<Object> getGrades(@RequestBody final Map<String, Object> map) {

        String email = (String) map.get("email");

        Person person = personRepository.findByEmail(email);

        if (person.equals(null)) {
            return new ResponseEntity<>("person does not exist",
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(gradeRepository.findAllByPerson(person), HttpStatus.OK);
    }

    @PostMapping("/totalGrade")
    public ResponseEntity<Object> totalGrade(@RequestBody final Map<String, Object> map) {
        String email = (String) map.get("email");

        Person person = personRepository.findByEmail(email);

        if (person.equals(null)) {
            return new ResponseEntity<>("person does not exist",
                    HttpStatus.BAD_REQUEST);
        }

        double totalGrade = 0;

        List<Grade> grades = gradeRepository.findAllByPerson(person);

        if (grades != null) {
            for (Grade grade : grades) {
                totalGrade += grade.getPoints();
            }
        }

        return new ResponseEntity<>("Total Grade of " + person.getName() + ": " + totalGrade, HttpStatus.OK);
    }

    @PostMapping("/updateGrade")
    public ResponseEntity<Object> updateGrade(@RequestBody final Map<String, Object> map) {
        String email = (String) map.get("email");
        String assignmentName = (String) map.get("assignment");
        String scoreString = (String) map.get("score");
        String comment = (String) map.get("comment");

        double score = Double.valueOf(scoreString);

        Person person = personRepository.findByEmail(email);
        Assignment assignment = assignmentRepository.findByName(assignmentName);

        if (person.equals(null) || assignment.equals(null)) {
            return new ResponseEntity<>("person/assignment does not exist", HttpStatus.BAD_REQUEST);
        }

        Grade grade = gradeRepository.findByPersonAndAssignment(person, assignment);

        if (grade.equals(null)) {
            return new ResponseEntity<>("grade does not exist", HttpStatus.BAD_REQUEST);
        }

        grade.setGrade(score);
        grade.setComment(comment);
        gradeRepository.save(grade);

        return new ResponseEntity<>(("Successfully updated grade for " + assignmentName + " for " + email),
                HttpStatus.OK);
    }

    /*
     * Endpoint to update checks
     * Make sure endpoint correlates with checks in grades
     */
    @PostMapping
    public ResponseEntity<Object> updateCheck(@RequestBody final Map<String, Object> map) {
        String email = (String) map.get("email");
        String assignmentName = (String) map.get("assignment");
        String check = (String) map.get("check");

        Person person = personRepository.findByEmail(email);
        Assignment assignment = assignmentRepository.findByName(assignmentName);

        if (person.equals(null)) {
            return new ResponseEntity<>("person does not exist",
                    HttpStatus.BAD_REQUEST);
        }

        Grade grade = gradeRepository.findByPersonAndAssignment(person, assignment);

        if (grade.equals(null)) {
            return new ResponseEntity<>("grade does not exist", HttpStatus.BAD_REQUEST);
        }

        grade.updateCheck(check, true);

        return new ResponseEntity<>((email + " started assignment for " + assignmentName),
                HttpStatus.OK);
    }

    /*
     * GET List of Grades by Assignment
     * Lists statuses as well in checks HashMap
     */
    @GetMapping("/assignmentGrades")
    public ResponseEntity<Object> getGradesByAssignment(@RequestBody final Map<String, Object> map) {

        String assignmentString = (String) map.get("assignment");

        Assignment assignment = assignmentRepository.findByName(assignmentString);

        if (assignment.equals(null)) {
            return new ResponseEntity<>("assignment does not exist",
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(gradeRepository.findAllByAssignment(assignment), HttpStatus.OK);
    }

}
