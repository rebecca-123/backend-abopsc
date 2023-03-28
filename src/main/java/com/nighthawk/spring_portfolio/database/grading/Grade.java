package com.nighthawk.spring_portfolio.database.grading;

import java.util.HashMap;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.nighthawk.spring_portfolio.database.person.Person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Class on Grade of Each Assignment
 * Relationship to Assignment
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Grade {

    // identity to prevent database locking
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "assignment_id")
    @OneToOne(cascade = CascadeType.MERGE)
    private Assignment assignment;

    @JoinColumn(name = "person_id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Person person;

    private double points;
    private String comment;

    @NotNull
    private HashMap<String, Boolean> checks = new HashMap<String, Boolean>();

    public Grade(Assignment assignment, Person person) {
        this.assignment = assignment;
        this.person = person;

        checks.put("Active", false);
        checks.put("Started", false);
        checks.put("Completed", false);
        checks.put("Live Review", false);
        checks.put("Graded", false);
    }

    public void updateCheck(String key, boolean value) {
        try {
            checks.replace(key, value);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setGrade(double grade) {
        if (grade < 0 || grade > this.assignment.getTotalPointValue()) {
            System.out.println("invalid grade, please enter in a valid grade");
            return;
        }

        this.points = grade;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
