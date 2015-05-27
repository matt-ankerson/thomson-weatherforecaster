package bit.ankem1.WeatherWorks.MetserviceApi;

/**
 * Created by matt on 17/05/15.
 */
// This class represents the Response object from the Metservice api
public class MetserviceResponse
{
    private Day[] days;
    private String locationIPS;

    public MetserviceResponse() {

    }

    public Day[] getDays()
    {
        return days;
    }
    public String getLocationIPS()
    {
        return locationIPS;
    }
}
