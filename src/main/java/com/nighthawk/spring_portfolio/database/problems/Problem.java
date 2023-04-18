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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // HashMap stores answer choices and boolean of correctness (ie 0 is incorrect,
    // 1 is correct)
    private String question;

    // options & correctness
    private HashMap<String, Boolean> answers;

    @JoinColumn(name = "problemSet_id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private ProblemSet problemSet;

}
