package com.nighthawk.spring_portfolio.database.lab;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CarLab { // CarLab is a class that represents a car
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    public CarLab(String name, double price) {
        this.name = name;
        this.price = price;

    }

    public CarLab(String name, double price, int mileage, int gallons, double mpg, String model, String make) {
        this.name = name;
        this.price = price;
        this.mileage = mileage;
        this.gallons = gallons;
        this.mpg = mpg;
        this.model = model;
        this.make = make;

    }

    @Column()
    private double price;

    // Hack: add columns for other attributes, (ex total mileage, model, make of
    // car) demonstrating knowledge of different data types
    @Column()
    private int mileage;

    @Column()
    private int gallons;

    @Column()
    private double mpg;

    @Column()
    private String model;

    @Column()
    private String make;

    // Hack: demonstrate another operation on a different data type
    public static void calcMpg(String car1Name, double car1Price) {
        CarLab car1 = new CarLab(car1Name, car1Price);

        car1.setMpg((double) car1.getMileage() / car1.getGallons());
    }

    public static String cheaperCar(String car1Name, double car1Price, String car2Name, double car2Price) {
        CarLab car1 = new CarLab(car1Name, car1Price);
        CarLab car2 = new CarLab(car2Name, car2Price);

        if (car1.getPrice() < car2.getPrice()) {
            return car1.getName();
        } else {
            return car2.getName();
        }
    }

    public static String cheaperCar(CarLab car1, CarLab car2) { // Overloading the cheaperCar method to allow both
                                                                // String and CarLab objects to be passed in
        if (car1.getPrice() < car2.getPrice()) {
            return car1.getName();
        } else {
            return car2.getName();
        }
    }

    public static int acceleration(double s1, double s2, double time) {
        return (Math.abs((int) ((s2 - s1) / time)));
    }

    public String toString() {
        return ("{" +
                "\"name\": " + name + "," +
                "\"price\": " + price +
                "}");
    }

    public static void main(String[] args) {
        String cheaperCarName = cheaperCar("Toyota", 20000.0, "Honda", 25000.0);
        System.out.println("The cheaper car is: " + cheaperCarName);

        System.out.println("The acceleration is: " + acceleration(0, 60, 10) + " m/s^2");
    }
}