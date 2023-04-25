package com.nighthawk.spring_portfolio.database.problems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nighthawk.spring_portfolio.database.grading.Assignment;
import com.nighthawk.spring_portfolio.database.grading.AssignmentJpaRepository;
import com.nighthawk.spring_portfolio.database.grading.Grade;
import com.nighthawk.spring_portfolio.database.grading.GradeJpaRepository;
import com.nighthawk.spring_portfolio.database.person.Person;
import com.nighthawk.spring_portfolio.database.person.PersonJpaRepository;
import com.nighthawk.spring_portfolio.database.role.Role;

@RestController
@RequestMapping("/api/problem")
public class ProblemApiController {

    @Autowired
    private ProblemJpaRepository problemJpaRepository;

    @Autowired
    private ProblemSetJpaRepository problemSetJpaRepository;

    @Autowired
    private AssignmentJpaRepository assignmentJpaRepository;

    @Autowired
    private PersonJpaRepository personJpaRepository;

    @Autowired
    private GradeJpaRepository gradeJpaRepository;

    /* data {
     *  name: "name of problem set",
     *  problems : {
     *    question1 : {
     *     "answer1" : true,
     *     "answer2" : false,
     *     "answer3" : false,
     *     "answer4" : false
     *      },
     *    question2 : {},
     *    ...
     * }
     */


    // Okay basically I'm going to be returning a Map<String, Map<String, Boolean>>
    // question, answer, correctness


    // Get by passing in name of problem set
    @PostMapping("/getProblemSetMC")
    public ResponseEntity<Object> getProblemSetMC(@RequestBody final Map<String, String> map) {
        ProblemSet problemSet = problemSetJpaRepository.findByName((String) map.get("name"));

        // If the problem set doesn't exist
        if (problemSet == null) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("err", "Problem set doesn't exist");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        Map<String, Map<String, Boolean>> mc = new HashMap<>();
        List<Problem> problems = problemJpaRepository.findByProblemSet(problemSet);

        // If there are no questions in the problem set
        if (problems.size() == 0) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("err", "Nothing in problem set");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        // Put each question and answers in the json response
        for (int i = 0; i < problems.size(); i++) {
            Problem problem = problems.get(i);
            String question = problem.getQuestion();
            Map<String, Boolean> answers = problem.getAnswers();

            mc.put(question, answers);
        }

        return new ResponseEntity<>(mc, HttpStatus.OK);
    }

    public static boolean checkAdmin(Person person) {
        for (Role role : person.getRoles()) {
            if (role.getName().equals("ROLE_ADMIN")) {
                return true;
            }
        }

        return false;
    }


    @PostMapping("/createProblemSetMC")
    public ResponseEntity<Object> createProblemSetMC(@RequestBody final Map<String, Object> map) {

        Map<String, Object> problemData = (Map<String, Object>) map.get("problems");

        Assignment assignment = new Assignment((String) map.get("name"), "quiz", 3.0, null);
        assignmentJpaRepository.save(assignment);

        List<Person> people = personJpaRepository.findAllByOrderByNameAsc();

        for (Person person : people) {
            if (!checkAdmin(person)) {
                Grade grade = new Grade(assignment, person);
                gradeJpaRepository.save(grade);
                personJpaRepository.save(person);
            }
        }

        ProblemSet problemSet = new ProblemSet((String) map.get("name"), assignment);
        problemSet = problemSetJpaRepository.save(problemSet);

        // for (Map<String, Object> problem : problemData) {
        //     Problem problemObject = new Problem();
        //     problemObject.setQuestion((String) problem.get("question"));

        //     // Probably should check this get() later
        //     problemObject.setAnswers((HashMap<String, Boolean>) problem.get("answers"));
        //     problemJpaRepository.save(problemObject);
        // }

        for (String question : problemData.keySet()) {
            Problem problemObject = new Problem();
            problemObject.setQuestion(question);
            problemObject.setAnswers((HashMap<String, Boolean>) problemData.get(question));
            problemObject.setProblemSet(problemSet);
            problemJpaRepository.save(problemObject);
        }


        Map<String, Object> resp = new HashMap<>();
        resp.put("id", problemSet.getId());
        return new ResponseEntity(resp, HttpStatus.OK);

    }

    // score
    @PostMapping("/score")
    public ResponseEntity<Object> score(@RequestBody final Map<String, Object> map) {
        String problemSetName = (String) map.get("problemSet");
        String scoreString = (String) map.get("score");
        String email = (String) map.get("email");

        double score = Double.valueOf(scoreString);

        ProblemSet problemSet = problemSetJpaRepository.findByName(problemSetName);
        Optional<Person> person = personJpaRepository.findByEmail(email);

        if (problemSet == null || person.get() == null) {
            return new ResponseEntity<>("problem set/person doesn't exist", HttpStatus.BAD_REQUEST);
        }

        Assignment assignment = problemSet.getAssignment();

        if (problemSet == null) {
            return new ResponseEntity<>("assignment don't exist, idk how this error would ever occur but ok lmao",
                    HttpStatus.BAD_REQUEST);
        }

        Grade grade = gradeJpaRepository.findByPersonAndAssignment(person.get(), assignment);

        if (grade == null) {
            return new ResponseEntity<>("grade doesn't exist", HttpStatus.BAD_REQUEST);
        }

        double gradeScaleFactor = 1;

        grade.setGrade(score / gradeScaleFactor);
        grade.setComment("quiz completed and graded");

        gradeJpaRepository.save(grade);

        return new ResponseEntity<>("score submitted", HttpStatus.OK);
    }
}
