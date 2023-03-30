package com.nighthawk.spring_portfolio.database.problems;

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


    // Problem stores the actual problem, String[] stores possible answers, correctAnswer 
    private String problem;
    private String[] answers;
    private int correctAnswer;

    @ManyToOne
    private ProblemSet problemSet;


}
