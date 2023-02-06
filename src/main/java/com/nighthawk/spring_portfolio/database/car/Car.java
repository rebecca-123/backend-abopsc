package com.nighthawk.spring_portfolio.database.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public Car(String name, String imageLink, String description) {
        this.name = name;
        this.imageLink = imageLink;
        this.description = description;
    }

}