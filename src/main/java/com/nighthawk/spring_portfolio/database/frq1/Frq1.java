// package com.nighthawk.spring_portfolio.database.frq1;
// import java.util.*;

// public class Frq1 {
  
//     ArrayList<Integer> carList = new ArrayList<Integer>();
//     /**Constructs a CarSort object that represents year.
//      * Precondition: year >= 0
//      */

//     public Frq1(int year){
//         carList = new ArrayList<Integer>();
//         if (year == 0){
//         carList.add(new Integer(0));
//         }
//         while (year > 0){
//         carList.add(0, new Integer(year % 10));
//         year /= 10;
//         }
//     } 

//     public boolean isStrictlyIncreasing(){
//         for (int i = 0; i < carList.size()-1; i++){
//         if (carList.get(i).intValue() >= carList.get(i+1).intValue()){
//         return false;
//             }
//         }
//         return true;
//     }   
// }
