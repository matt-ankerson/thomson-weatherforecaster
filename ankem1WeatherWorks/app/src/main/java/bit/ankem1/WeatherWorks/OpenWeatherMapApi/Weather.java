package bit.ankem1.WeatherWorks.OpenWeatherMapApi;

/**
 * Created by matt on 14/05/15.
 */
public class Weather
{
    private int id;
    private String main;
    private String description;
    private String icon;

    public Weather()
    {

    }

    public int getId()
    {
        return id;
    }
    public String getMain()
    {
        return main;
    }
    public String getDescription()
    {
        return description;
    }
    public String getIcon()
    {
        return icon;
    }
}
