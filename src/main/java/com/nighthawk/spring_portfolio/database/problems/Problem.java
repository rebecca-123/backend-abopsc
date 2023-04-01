package com.nighthawk.spring_portfolio.database.problems;

import java.util.HashMap;

import javax.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Problem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    // HashMap stores answer choices and boolean of correctness (ie 0 is incorrect, 1 is correct)
    private String problem;
    private HashMap<String, Boolean> answers;

    @ManyToOne
    private ProblemSet problemSet;


}
