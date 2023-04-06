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

@RestController
@RequestMapping("/api/problem")
public class ProblemApiController {
    
    @Autowired
    private ProblemJpaRepository problemJpaRepository;

    @Autowired
    private ProblemSetJpaRepository problemSetJpaRepository;


    // Okay basically I'm going to be returning a Map<String, Map<String, Boolean>>
    //                                              question,     answer, correctness
    @PostMapping("/getProblemSetMC")
    public ResponseEntity<Object> getProblemSetMC(@RequestBody final Map<String, Object> map) {
        Optional<ProblemSet> optionalProblemSet = problemSetJpaRepository.findById((Long) map.get("id"));

        // If the problem set doesn't exist
        if (!optionalProblemSet.isPresent()) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("err", "Problem set doesn't exist");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        Map<String, Map<String, Boolean>> mc = new HashMap<>();
        List<Problem> problems = problemJpaRepository.findByProblemSet(optionalProblemSet.get());

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

}
