package bit.ankem1.WeatherWorks;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

// This activity displays information from all weather providers
// If we reach this activity, we can safely use the data in WeatherStore.
public class AllProvidersScreen extends ActionBarActivity
{
    // The forecastday index
    int dayNumber;

    //---------------------
    // Weather Underground
    //---------------------

    // WeatherUnderground data (txt_forecast array)
    String wtitle;
    String wdescription;

    // WeatherUnderground data (simpleforecast array)
    String wtempHighC;
    String wtempLowC;
    String wconditions;
    double wrainInches_allDay;
    double wrainInches_day;
    double wrainInches_night;
    double wrainMm_allDay;
    double wrainMm_day;
    double wrainMm_night;
    double wsnowInches_allDay;
    double wsnowInches_day;
    double wsnowInches_night;
    double wsnowCm_allDay;
    double wsnowCm_day;
    double wsnowCm_night;
    double wwindMph;
    double wwindKph;
    double wwindMphMax;
    double wwindKphMax;
    String wwindDirection;
    double waveHumidity;
    double wminHumidity;
    double wmaxHumidity;

    //---------------------
    // Metservice
    //---------------------

    // Forecast data
    String mdate;
    String mdow;
    String mforecast;
    String mforecastWord;
    String missuedAt;
    String mmin;
    String mmax;
    String mday;
    String mid;
    String mlocation;
    String mmoonRise;
    String mmoonSet;
    String msunRise;
    String msunSet;

    //---------------------
    // Open Weather Map
    //---------------------

    // Forecast data for this day
    int odt;
    // All temperature info for this day
    double oday;     // Temperatures from OWM are in Kelvin, (subtract 273.15 to convert to Celsius)
    double omin;
    double omax;
    double onight;
    double oeve;
    double omorn;
    double opressure;
    double ohumidity;
    // Additional weather info for this day
    int oid;
    String omain;
    String odescription;
    String oicon;
    double ospeed;
    double odeg;
    double oclouds;
    double orain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_providers_screen);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_providers_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
