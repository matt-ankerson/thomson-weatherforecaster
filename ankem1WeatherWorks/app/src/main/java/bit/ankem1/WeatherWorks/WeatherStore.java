package bit.ankem1.WeatherWorks;

import android.graphics.Point;

import bit.ankem1.WeatherWorks.MetserviceApi.MetserviceResponse;
import bit.ankem1.WeatherWorks.OpenWeatherMapApi.OpenWeatherMapResponse;
import bit.ankem1.WeatherWorks.WeatherUndergroundApi.WeatherUndergroundResponse;

/**
 * Created by matt on 17/05/15.
 */
// The purpose of this class is to wrap our weather response object hierarchies
// in a singleton object
public class WeatherStore
{
    private static WeatherStore instance;

    private OpenWeatherMapResponse owmResponse;
    private MetserviceResponse metserviceResponse;
    private WeatherUndergroundResponse weatherUndergroundResponse;
    private Point coordinates;

    // Constructor hidden because this is a singletom
    private WeatherStore()
    {

    }

    public static void initInstance()
    {
        if (instance == null)
        {
            // Create the instance
            instance = new WeatherStore();
        }
    }

    // Return the single instance of this class
    public static WeatherStore getInstance()
    {
        return instance;
    }

    // Accessor functions

    public OpenWeatherMapResponse getOwmResponse()
    {
        return owmResponse;
    }
    public MetserviceResponse getMetserviceResponse()
    {
        return metserviceResponse;
    }
    public WeatherUndergroundResponse getWuResponse()
    {
        return weatherUndergroundResponse;
    }
    public Point getCoordinates()
    {
        return coordinates;
    }


    // Modifier functions

    public void setOwmResponse(OpenWeatherMapResponse owmResponse)
    {
        this.owmResponse = owmResponse;
    }
    public void setMetserviceResponse(MetserviceResponse metserviceResponse)
    {
        this.metserviceResponse = metserviceResponse;
    }
    public void setWuResponse(WeatherUndergroundResponse weatherUndergroundResponse)
    {
        this.weatherUndergroundResponse = weatherUndergroundResponse;
    }
    public void setCoordinates(Point newCoordinates)
    {
        this.coordinates = newCoordinates;
    }
}
