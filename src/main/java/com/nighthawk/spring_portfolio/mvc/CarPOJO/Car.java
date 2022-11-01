package com.nighthawk.spring_portfolio.mvc.CarPOJO;

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
    private String car;

    private String brand;

    private int like;  
    private int dislike;  
}
