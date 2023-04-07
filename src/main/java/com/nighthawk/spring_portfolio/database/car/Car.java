package com.nighthawk.spring_portfolio.database.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String name;

    @Column()
    private String description;

    @Column()
    private String make;

    @Column()
    private String model;

    @Column()
    private int year;

    @Column()
    private double mpg;
    
    public Car(String name, String description, String make, String model, int year, double mpg) {
        this.name = name;
        this.description = description;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mpg = mpg;
        
    }

    public String toString() {
        return( "{" + 
            "\"name\": " + name + "," +
            "\"description\": " +  description + "," +
            "\"make\": " +  make + "," +
            "\"model\": " +  model + "," +
            "\"year\": " + year + "," +
            "\"mpg\": " + mpg +
            "}" );
    }

}