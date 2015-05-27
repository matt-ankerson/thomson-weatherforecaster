package bit.ankem1.WeatherWorks.WeatherUndergroundApi;

/**
 * Created by matt on 18/05/15.
 */
// This class represents the JSON response from WeatherUnderground
public class WeatherUndergroundResponse
{
    private Forecast forecast;

    public WeatherUndergroundResponse()
    {

    }

    public Forecast getForecast()
    {
        return forecast;
    }
}
