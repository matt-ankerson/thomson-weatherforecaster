package bit.ankem1.WeatherWorks.MetserviceApi;

/**
 * Created by matt on 21/05/15.
 */
// This class represents a RiseSet JSON object returned from Metservice
public class RiseSet
{
    private String day;            // String representation of current date
    private String id;
    private String location;       // Where measurements were taken
    private String moonRise;
    private String moonSet;
    private String sunRise;
    private String sunSet;

    public RiseSet()
    {

    }

    public String getDay()
    {
        return day;
    }
    public String getId()
    {
        return id;
    }
    public String getLocation()
    {
        return location;
    }
    public String getMoonRise()
    {
        return moonRise;
    }
    public String getMoonSet()
    {
        return moonSet;
    }
    public String getSunRise()
    {
        return sunRise;
    }
    public String getSunSet()
    {
        return sunSet;
    }
}
