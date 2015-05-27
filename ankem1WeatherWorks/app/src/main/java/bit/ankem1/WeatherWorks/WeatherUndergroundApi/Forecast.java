package bit.ankem1.WeatherWorks.WeatherUndergroundApi;

/**
 * Created by matt on 18/05/15.
 */
// This class represents a forecast JSON object returned from WeatherUnderground
public class Forecast
{
    Txt_forecast txt_forecast;
    Simple_forecast simpleforecast;

    public Forecast() {

    }

    public Txt_forecast getTxt_forecast()
    {
        return txt_forecast;
    }
    public  Simple_forecast getSimpleforecast()
    {
        return simpleforecast;
    }
}
