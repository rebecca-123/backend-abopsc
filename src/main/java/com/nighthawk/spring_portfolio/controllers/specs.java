package com.nighthawk.spring_portfolio.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class specs {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://car-specs.p.rapidapi.com/v2/cars/makes/%7BmakeId%7D/models");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // Add Request Headers
            con.setRequestMethod("GET");
            con.setRequestProperty("X-RapidAPI-Host", "car-specs.p.rapidapi.com");
            con.setRequestProperty("X-RapidAPI-Key", "032f716b5amsh1241419d17ff651p1d3c54jsna32e706ab7f6");

            // Send the GET Request
            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            // Read the Response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print the Response
            System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
