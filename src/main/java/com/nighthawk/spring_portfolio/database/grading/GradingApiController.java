package com.nighthawk.spring_portfolio.database.grading;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import com.nighthawk.spring_portfolio.database.person.Person;
import com.nighthawk.spring_portfolio.database.person.PersonJpaRepository;
import com.nighthawk.spring_portfolio.database.role.Role;

import java.util.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

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
     * GET List of Grades by Person
     */
    @GetMapping("/grades")
    public ResponseEntity<Object> getGrades(@RequestBody final Map<String, Object> map) {

        String email = (String) map.get("email");

        Person person = personRepository.findByEmail(email);

        if (person == null) {
            return new ResponseEntity<>("person does not exist",
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(gradeRepository.findAllByPerson(person), HttpStatus.OK);
    }

    @GetMapping("/totalGrade")
    public ResponseEntity<Object> totalGrade(@RequestBody final Map<String, Object> map) {
        String email = (String) map.get("email");

        Person person = personRepository.findByEmail(email);

        if (person == null) {
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

    /*
     * DELETE individual Person using ID
     * Set as POST since not sure if delete works through aws CORS
     */
    @PostMapping("/delete")
    public ResponseEntity<Object> deletePerson(@RequestBody final Map<String, Object> map) {
        String assignmentName = (String) map.get("assignment");

        Assignment assignment = assignmentRepository.findByName(assignmentName);

        if (assignment == null) {
            return new ResponseEntity<>("person not found", HttpStatus.OK);
        }

        List<Grade> grades = gradeRepository.findAllByAssignment(assignment);

        for (Grade grade : grades) {
            gradeRepository.delete(grade);
        }

        assignmentRepository.delete(assignment);

        return new ResponseEntity<>(assignmentName + " and all relevant grades deleted", HttpStatus.OK);
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

        // accounts for late penalty (prob wont use bc we can just do it manually)
        // ZoneId defaultZoneId = ZoneId.systemDefault();
        // LocalDate currentDate = LocalDate.now();
        // Date date = Date.from(currentDate.atStartOfDay(defaultZoneId).toInstant());
        // if(date.after(assignment.getDueDate())){
        // score = score*0.9;
        // }

        if (person == null || assignment == null) {
            return new ResponseEntity<>("person/assignment does not exist", HttpStatus.BAD_REQUEST);
        }

        Grade grade = gradeRepository.findByPersonAndAssignment(person, assignment);

        if (grade == null) {
            return new ResponseEntity<>("grade does not exist", HttpStatus.BAD_REQUEST);
        }

        grade.setGrade(score);
        grade.setComment(comment);
        grade.updateCheck("Graded", true);
        gradeRepository.save(grade);

        return new ResponseEntity<>(("Successfully updated grade for " + assignmentName + " for " + email),
                HttpStatus.OK);
    }

    /*
     * Endpoint to update checks
     * Make sure endpoint correlates with checks in grades
     */
    @PostMapping("/updateCheck")
    public ResponseEntity<Object> updateCheck(@RequestBody final Map<String, Object> map) {
        String email = (String) map.get("email");
        String assignmentName = (String) map.get("assignment");
        String check = (String) map.get("check");
        String checkStatus = (String) map.get("status");

        boolean status = Boolean.valueOf(checkStatus);

        Person person = personRepository.findByEmail(email);
        Assignment assignment = assignmentRepository.findByName(assignmentName);

        if (person == null) {
            return new ResponseEntity<>("person does not exist",
                    HttpStatus.BAD_REQUEST);
        }

        Grade grade = gradeRepository.findByPersonAndAssignment(person, assignment);

        if (grade == null) {
            return new ResponseEntity<>("grade does not exist", HttpStatus.BAD_REQUEST);
        }

        grade.updateCheck(check, status);

        gradeRepository.save(grade);

        return new ResponseEntity<>((email + " started assignment for " + assignmentName),
                HttpStatus.OK);
    }

    // just checks if there's an admin role]
    // helps to skip admin so they don't have Grade POJOs stored
    public static boolean checkAdmin(Person person) {
        for (Role role : person.getRoles()) {
            if (role.getName().equals("ROLE_ADMIN")) {
                return true;
            }
        }

        return false;
    }

    @PostMapping("/createAssignment")
    public ResponseEntity<Object> createAssignment(@RequestBody final Map<String, Object> map) {
        String name = (String) map.get("name");
        String type = (String) map.get("type");
        String pointValueString = (String) map.get("point value");
        String dateString = (String) map.get("dueDate");

        double pointValue = Double.valueOf(pointValueString);
        Date dueDate;

        try {
            dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (Exception e) {
            return new ResponseEntity<>(dateString + " error; try yyyy-MM-dd",
                    HttpStatus.BAD_REQUEST);
        }

        Assignment assignment = new Assignment(name, type, pointValue, dueDate);
        assignmentRepository.save(assignment);

        // initialize grades for every current person that exists for this new
        // assignment
        List<Person> people = personRepository.findAllByOrderByNameAsc();

        for (Person person : people) {
            if (!checkAdmin(person)) {
                Grade grade = new Grade(assignment, person);
                gradeRepository.save(grade);
            }
        }

        return new ResponseEntity<>(name + " assignment created successfully", HttpStatus.OK);
    }

    @PostMapping("/initializeAssignment")
    public ResponseEntity<Object> initializeAssignment(@RequestBody final Map<String, Object> map) {
        String assignmentName = (String) map.get("assignment");
        Assignment assignment = assignmentRepository.findByName(assignmentName);

        if (assignment == null) {
            return new ResponseEntity<>("assignment does not exist", HttpStatus.BAD_REQUEST);
        }

        List<Grade> grades = gradeRepository.findAllByAssignment(assignment);

        for (Grade grade : grades) {
            grade.updateCheck("Active", true);
            gradeRepository.save(grade);
        }

        return new ResponseEntity<>((assignmentName + " initialized"), HttpStatus.OK);
    }

    /*
     * GET List of Grades by Assignment
     * Lists statuses as well in checks HashMap
     */
    @GetMapping("/assignmentGrades")
    public ResponseEntity<Object> getGradesByAssignment(@RequestBody final Map<String, Object> map) {

        String assignmentString = (String) map.get("assignment");

        Assignment assignment = assignmentRepository.findByName(assignmentString);

        if (assignment == null) {
            return new ResponseEntity<>("assignment does not exist",
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(gradeRepository.findAllByAssignment(assignment), HttpStatus.OK);
    }

}
