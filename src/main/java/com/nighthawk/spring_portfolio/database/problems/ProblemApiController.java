package com.nighthawk.spring_portfolio.database.problems;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    @PostMapping("/getProblemSetMC")
    public ResponseEntity<Object> getProblemSetMC(@RequestBody final Map<String, Object> map) {
        Optional<ProblemSet> optionalProblemSet = problemSetJpaRepository.findById((int)map.get("id"));
        
        // Delete this later
        Map<String, Object> resp = new HashMap<>();
        resp.put("err", "Work In Progress");
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }
    
}
