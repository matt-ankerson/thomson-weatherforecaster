package bit.ankem1.WeatherWorks;

/**
 * Created by matt on 8/06/15.
 */
public class Coordinate
{
    private double latitude;
    private double longitude;

    public Coordinate(double lat, double lon)
    {
        this.latitude = lat;
        this.longitude = lon;
    }

    public double getLatitude()
    {
        return latitude;
    }
    public double getLongitude()
    {
        return longitude;
    }

    public void setLatitude(double newLat)
    {
        this.latitude = newLat;
    }
    public void setLongitude(double newLon)
    {
        this.longitude = newLon;
    }
}
