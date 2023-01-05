package com.nighthawk.spring_portfolio.mvc.lights;

public class LightBoard {
    private Light[][] lights;

    public LightBoard(int numRows, int numCols) {
        this.lights = new Light[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                lights[row][col] = new Light(); 
            }
        }
    }

    public String toString() { 
        String outString = "[";
        // 2D array nested loops, used for reference
        for (int row = 0; row < lights.length; row++) {
            for (int col = 0; col < lights[row].length; col++) {
                outString += 
                // data
                "{" + 
                "\"row\": " + row + "," +
                "\"column\": " + col + "," +
                "\"light\": " + lights[row][col] +   // extract toString data
                "}," ;
            }
        }
        // remove last comma, newline, add square bracket, reset color
        outString = outString.substring(0,outString.length() - 1) + "]";
		return outString;
    }

    public void decrementLights() {
        for (int row = 0; row < lights.length; row++) {
            for (int col = 0; col < lights[row].length; col++) {
                lights[row][col].decrementLight();
                if (lights[row][col].luminosity < 1) {
                    lights[row][col].luminosity = 1000;
                }
            }
        }  
    }

    public static void main(String[] args) {
        // create and display LightBoard
        LightBoard lightBoard = new LightBoard(5, 5);
        System.out.println(lightBoard);  // use toString() method
    }
}
