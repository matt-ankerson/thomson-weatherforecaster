package bit.ankem1.WeatherWorks.WeatherUndergroundApi;

/**
 * Created by matt on 21/05/15.
 */
// This class represents a High JSON object returned by WeatherUnderground
public class Temperature
{
    private String fahrenheit;
    private String celsius;

    public Temperature() {

    }

    public String getFahrenheit()
    {
        return fahrenheit;
    }
    public String getCelcius()
    {
        return celsius;
    }
}
