package bit.ankem1.WeatherWorks.WeatherUndergroundApi;

/**
 * Created by matt on 21/05/15.
 */
// This class represents a Precipitation JSON object returned by WeatherUnderground
public class Precipitation
{
    private double in;
    private double mm;

    public Precipitation()
    {

    }

    public double getIn()
    {
        return in;
    }
    public double getMm()
    {
        return mm;
    }
}
