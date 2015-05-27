package bit.ankem1.WeatherWorks.OpenWeatherMapApi;

/**
 * Created by matt on 15/05/15.
 */

// This class represents a single day in a long range forecast.
public class Day
{
    private int dt;
    private Temperature temp;   // All temperature info for this day
    private double pressure;
    private double humidity;
    private Weather[] weather;  // Additional weather info for this day
    private double speed;
    private double deg;
    private double clouds;
    private double rain;

    public Day() {

    }

    public int getDt()
    {
        return dt;
    }
    public Temperature getTemp()
    {
        return temp;
    }
    public double getPressure()
    {
        return pressure;
    }
    public double getHumidity()
    {
        return humidity;
    }
    public Weather[] getWeather()
    {
        return weather;
    }
    public double getSpeed()
    {
        return speed;
    }
    public double getDeg()
    {
        return deg;
    }
    public double getClouds()
    {
        return clouds;
    }
    public double getRain()
    {
        return rain;
    }
}
