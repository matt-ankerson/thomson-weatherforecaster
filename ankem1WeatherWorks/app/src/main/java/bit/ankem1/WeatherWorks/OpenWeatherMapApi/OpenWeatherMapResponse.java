package bit.ankem1.WeatherWorks.OpenWeatherMapApi;

/**
 * Created by matt on 14/05/15.
 */

// This is a response to a call for daily forecast information
public class OpenWeatherMapResponse
{
    private int cod;             // Response code
    private String message;      // Time taken to complete request
    private City city;           // Location corresponding to given coordinates
    private int cnt;             // Number of days requested for this forecast
    private Day[] list;          // List of days containing forecast information

    public OpenWeatherMapResponse() {

    }

    public int getCod()
    {
        return cod;
    }
    public String getMessage()
    {
        return message;
    }
    public City getCity()
    {
        return city;
    }
    public int getCnt()
    {
        return cnt;
    }
    public Day[] getList()
    {
        return list;
    }


}
