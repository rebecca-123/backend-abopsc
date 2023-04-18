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

    // Okay basically I'm going to be returning a Map<String, Map<String, Boolean>>
    // question, answer, correctness
    @PostMapping("/getProblemSetMC")
    public ResponseEntity<Object> getProblemSetMC(@RequestBody final Map<String, Object> map) {
        ProblemSet problemSet = problemSetJpaRepository.findByName((String) map.get("problemSet"));

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

    @PostMapping("/createProblemSetMC")
    public ResponseEntity<Object> createProblemSetMC(@RequestBody final Map<String, Object> map) {

        List<Map<String, Object>> problemData = (List<Map<String, Object>>) map.get("problems");
        ProblemSet problemSet = new ProblemSet();

        problemSet.setName((String) map.get("name"));
        problemSet = problemSetJpaRepository.save(problemSet);

        for (Map<String, Object> problem : problemData) {
            Problem problemObject = new Problem();
            problemObject.setQuestion((String) problem.get("question"));
            problemObject.setAnswers((HashMap<String, Boolean>) problem.get("answers"));
            problemJpaRepository.save(problemObject);
        }
        Map<String, Object> resp = new HashMap<>();
        resp.put("id", problemSet.getId());
        return new ResponseEntity(resp, HttpStatus.OK);

    }

    @PostMapping("/score")
    public ResponseEntity<Object> score(@RequestBody final Map<String, Object> map) {
        String problemSetName = (String) map.get("problemSet");
        String scoreString = (String) map.get("score");
        String userIDString = (String) map.get("userID");

        double score = Double.valueOf(scoreString);
        long userID = Long.valueOf(userIDString);

        ProblemSet problemSet = problemSetJpaRepository.findByName(problemSetName);
        Person person = personJpaRepository.findById(userID).orElse(null);

        if (problemSet == null || person == null) {
            return new ResponseEntity<>("problem set/person doesn't exist", HttpStatus.BAD_REQUEST);
        }

        Assignment assignment = problemSet.getAssignment();

        if (problemSet == null) {
            return new ResponseEntity<>("assignment don't exist, idk how this error would ever occur but ok lmao",
                    HttpStatus.BAD_REQUEST);
        }

        Grade grade = gradeJpaRepository.findByPersonAndAssignment(person, assignment);

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
