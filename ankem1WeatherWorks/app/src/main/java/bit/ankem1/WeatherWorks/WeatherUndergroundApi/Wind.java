package bit.ankem1.WeatherWorks.WeatherUndergroundApi;

/**
 * Created by matt on 21/05/15.
 */
// This class represents a Wind JSON object returned by WeatherUnderground
public class Wind
{
    private double mph;
    private double kph;
    private String dir;
    private double degrees;

    public Wind()
    {

    }

    public double getMph()
    {
        return mph;
    }
    public double getKph()
    {
        return kph;
    }
    public String getDir()
    {
        return dir;
    }
    public double getDegrees()
    {
        return degrees;
    }
}
