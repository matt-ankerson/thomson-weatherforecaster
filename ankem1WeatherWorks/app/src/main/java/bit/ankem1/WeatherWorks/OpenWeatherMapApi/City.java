package bit.ankem1.WeatherWorks.OpenWeatherMapApi;

/**
 * Created by matt on 15/05/15.
 */

// Represents a JSON City object
// For our purposes, we are treating a city like a generic location.
public class City
{
    private int id;         // The city id
    private String name;    // The city name
    private Coord coord;    // The coordinates for this location

    public City() {

    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public Coord getCoord()
    {
        return coord;
    }
}


