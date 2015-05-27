package bit.ankem1.WeatherWorks.WeatherUndergroundApi;


/**
 * Created by matt on 21/05/15.
 */
// This class represents a SIMPLE forecastday JSON object returned by WeatherUnderground
public class SimpleForecastDay
{
    private int period;                 // Index in the SimpleForecastDay array
    private Temperature high;           // Temperature high/low
    private Temperature low;
    private String conditions;          // Short description of weather
    private Precipitation qpf_allday;   // Precipitation for the whole day
    private Precipitation qpf_day;
    private Precipitation qpf_night;
    private Snow snow_allday;           // Amount of snow all day
    private Snow snow_day;
    private Snow snow_night;
    private Wind maxwind;                // Wind conditions for this time period
    private Wind avewind;
    private double avehumidity;          // Humidity conditions for this time period
    private double maxhumidity;
    private double minhumidity;

    public SimpleForecastDay() {

    }

    public int getPeriod()
    {
        return period;
    }
    public Temperature getHigh()
    {
        return high;
    }
    public Temperature getLow()
    {
        return low;
    }
    public String getConditions()
    {
        return conditions;
    }
    public Precipitation getQpf_allday()
    {
        return qpf_allday;
    }
    public Precipitation getQpf_day()
    {
        return qpf_day;
    }
    public Precipitation getQpf_night()
    {
        return qpf_night;
    }
    public Snow getSnow_allday()
    {
        return snow_allday;
    }
    public Snow getSnow_day()
    {
        return snow_day;
    }
    public Snow getSnow_night()
    {
        return snow_night;
    }
    public Wind getMaxwind()
    {
        return maxwind;
    }
    public Wind getAvewind()
    {
        return avewind;
    }
    public double getAvehumidity()
    {
        return avehumidity;
    }
    public double getMaxhumidity()
    {
        return maxhumidity;
    }
    public double getMinhumidity()
    {
        return minhumidity;
    }

}
