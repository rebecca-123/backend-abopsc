package com.nighthawk.spring_portfolio.mvc.lights;

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
        this.luminosity = 1000;
        this.quality = 100;
    }

    public void lightSwitch() {
        this.on = !this.on;
    }

    public boolean getOn() {
        return this.on;
    }

    public void decrementLight() {
        if (this.luminosity > 0) {
            this.luminosity -= 10 * (double) 1/quality;
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
