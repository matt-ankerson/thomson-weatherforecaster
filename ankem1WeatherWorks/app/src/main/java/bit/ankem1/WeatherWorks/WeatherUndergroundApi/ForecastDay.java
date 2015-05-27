package bit.ankem1.WeatherWorks.WeatherUndergroundApi;

/**
 * Created by matt on 18/05/15.
 */
// This class represents a single day of forecast data returned from WeatherUnderground
public class ForecastDay
{
    private int period;             // Position in the array of returned days
    private String icon;            // String representation of icon
    private String icon_url;        // URL to the icon
    private String title;           // Day of the week/night
    private String fcttext;         // Imperial description
    private String fcttext_metric;  // Metric description
    private String pop;

    public ForecastDay() {

    }

    public int getPeriod()
    {
        return period;
    }
    public String getIcon()
    {
        return icon;
    }
    public String getIcon_url()
    {
        return icon_url;
    }
    public String getTitle()
    {
        return title;
    }
    public String getFcttext()
    {
        return fcttext;
    }
    public String getFcttext_metric()
    {
        return fcttext_metric;
    }
    public String getPop()
    {
        return pop;
    }
}
