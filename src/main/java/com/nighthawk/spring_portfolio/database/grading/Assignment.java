package com.nighthawk.spring_portfolio.database.grading;

import java.sql.Date;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    @NotNull
    private HashMap<String, Boolean> checks = new HashMap<String, Boolean>();

    @NonNull
    private Date dueDate;

    public Assignment(String name, String type, Date dueDate) {
        this.name = name;
        this.type = type;
        this.dueDate = dueDate;

        checks.put("Active", true);
        checks.put("Started", false);
        checks.put("Completion", false);
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

}
