package com.nighthawk.spring_portfolio.database.problems;

import javax.persistence.*;

import com.nighthawk.spring_portfolio.database.grading.Assignment;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProblemSet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @JoinColumn(name = "assignment_id")
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Assignment assignment;
}
