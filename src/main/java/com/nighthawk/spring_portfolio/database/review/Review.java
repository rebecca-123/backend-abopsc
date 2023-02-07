package com.nighthawk.spring_portfolio.database.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String name;

    @Column()
    private String email;

    @Column()
    private String phone;

    @Column()
    private int stars;

    @Column()
    private String comments;

    public Review(String name, String email, String phone, int stars, String comments) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.stars = stars;
        this.comments = comments;
    }

}