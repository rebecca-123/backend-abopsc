package com.nighthawk.spring_portfolio.database.challenge;

import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    private String type;

    @NotNull
    private HashMap<String, Boolean> checks = new HashMap<String, Boolean>();

    public Assignment(String type) {
        this.type = type;

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
