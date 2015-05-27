package bit.ankem1.WeatherWorks.WeatherUndergroundApi;

/**
 * Created by matt on 21/05/15.
 */
// This class represents the SimpleForecast JSON object returned by WeatherUnderground
public class Simple_forecast
{
    SimpleForecastDay[] forecastday;

    public Simple_forecast() {

    }

    public SimpleForecastDay[] getForecastday()
    {
        return forecastday;
    }
}
