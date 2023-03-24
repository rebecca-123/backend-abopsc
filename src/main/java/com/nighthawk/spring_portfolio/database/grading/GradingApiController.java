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

    @PostMapping("/updateGrade")
    public ResponseEntity<Object> updateGrade(@RequestBody final Map<String, Object> map) {
        String email = (String) map.get("email");
        String assignmentName = (String) map.get("assignment");
        String scoreString = (String) map.get("score");
        String comment = (String) map.get("comment");

        double score = Double.valueOf(scoreString);

        Person person = personRepository.findByEmail(email);
        Assignment assignment = assignmentRepository.findByName(assignmentName);

        if (person == null || assignment == null) {
            return new ResponseEntity<>("person/assignment does not exist", HttpStatus.BAD_REQUEST);
        }

        Grade grade = gradeRepository.findByPersonAndAssignment(person, assignment);

        if (grade == null) {
            return new ResponseEntity<>("grade does not exist", HttpStatus.BAD_REQUEST);
        }

        grade.setGrade(score);
        grade.setComment(comment);
        gradeRepository.save(grade);

        return new ResponseEntity<>(("Successfully updated grade for " + assignmentName + " for " + email),
                HttpStatus.OK);
    }
}
