package bit.ankem1.WeatherWorks.OpenWeatherMapApi;

/**
 * Created by matt on 15/05/15.
 */

// This class represents a temperature
public class Temperature
{
    private double day;     // Temperatures are in Kelvin, (subtract 273.15 to convert to Celsius)
    private double min;
    private double max;
    private double night;
    private double eve;
    private double morn;

    public Temperature() {

    }

    public double getDay()
    {
        return day;
    }
    public double getMin()
    {
        return min;
    }
    public double getMax()
    {
        return max;
    }
    public double getNight()
    {
        return night;
    }
    public double getEve()
    {
        return eve;
    }
    public double getMorn()
    {
        return morn;
    }
}
