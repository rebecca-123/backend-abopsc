package com.nighthawk.spring_portfolio.mvc.lights;
import java.lang.Math;

public class Light {
    boolean on;
    double luminosity;
    int quality;

    public Light(boolean on, double luminosity, int quality) {
        this.on = on;
        this.luminosity = luminosity;
        this.quality = quality;
    }

    public Light() {
        this.on = true;
        this.luminosity = 50 + 200 * Math.random();
        this.quality = (int) (50 + 50 * Math.random());
    }

    public void lightSwitch() {
        this.on = !this.on;
    }

    public boolean getOn() {
        return this.on;
    }

    public void decrementLight() {
        if (this.luminosity > 0) {
            this.luminosity -= 1000 * (double) 1/quality;
        }
        else {
            this.luminosity = 0;
        }
    }

    public String toString() {
        return( "{" + 
            "\"on\": " + on + "," +
            "\"luminosity\": " +  luminosity + "," + 
            "\"quality\": " + quality + 
            "}" );
    }


}
