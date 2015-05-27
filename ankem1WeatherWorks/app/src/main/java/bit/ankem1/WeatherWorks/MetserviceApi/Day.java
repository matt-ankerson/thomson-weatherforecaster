package bit.ankem1.WeatherWorks.MetserviceApi;

/**
 * Created by matt on 17/05/15.
 */
// This class represents a Day object returned from Metservice
public class Day
{
    private String date;        // The date of this day
    private String dow;         // The word representation of day of week
    private String forecast;    // Description of the weather for this day
    private String forecastWord;// Short description of the weather
    private String issuedAt;    // Date and time of this forecast
    private String min;         // Min temperature for this day
    private String max;         // Max temperature for this day
    private RiseSet riseSet;    // Sun and moon information

    public Day() {

    }

    public String getDate()
    {
        return date;
    }
    public String getDow()
    {
        return dow;
    }
    public String getForecast()
    {
        return forecast;
    }
    public String getForecastWord()
    {
        return forecastWord;
    }
    public String getIssuedAt()
    {
        return issuedAt;
    }
    public String getMin()
    {
        return min;
    }
    public String getMax()
    {
        return max;
    }
    public RiseSet getRiseSet()
    {
        return riseSet;
    }
}
