package bit.ankem1.WeatherWorks.WeatherUndergroundApi;

/**
 * Created by matt on 18/05/15.
 */
// This class represents the detailed JSON forecast object returned from WeatherUnderground
public class Txt_forecast
{
    private String date;                // The date that this forecast was retrieved
    private ForecastDay[] forecastday;  // Array of days (and nights as this api returns)

    public Txt_forecast()
    {

    }

    public String getDate()
    {
        return date;
    }
    public ForecastDay[] getForecastday()
    {
        return forecastday;
    }
}
