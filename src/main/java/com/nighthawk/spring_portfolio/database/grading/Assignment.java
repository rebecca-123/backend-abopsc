package com.nighthawk.spring_portfolio.database.grading;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Assignment {
    // automatic unique identifier for Person record
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String type;

    @PositiveOrZero
    private double totalPointValue;

    private Date dueDate;

    public Assignment(String name, String type, double pointValue, Date dueDate) {
        this.name = name;
        this.type = type;
        this.totalPointValue = pointValue;
        this.dueDate = dueDate;
    }

}
