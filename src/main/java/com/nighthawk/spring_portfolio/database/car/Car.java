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
    private String imageLink;

    @Column()
    private String description;

    @Column()
    private String make;

    @Column()
    private String model;

    @Column()
    @Size(min = 4, max = 4)
    private int year;

    public Car(String name, String imageLink, String description, String make, String model, int year) {
        this.name = name;
        this.imageLink = imageLink;
        this.description = description;
        this.make = make;
        this.model = model;
        this.year = year;
        
    }

}