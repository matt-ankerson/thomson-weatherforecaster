package bit.ankem1.WeatherWorks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bit.ankem1.WeatherWorks.MetserviceApi.Day;
import bit.ankem1.WeatherWorks.WeatherUndergroundApi.ForecastDay;
import bit.ankem1.WeatherWorks.WeatherUndergroundApi.SimpleForecastDay;

// This activity displays information from all weather providers
// If we reach this activity, we can safely use the data in WeatherStore.
public class AllProvidersScreen extends ActionBarActivity
{
    private final double KELVIN = 273.15;
    private final double CONVERTMULTIPLIER = 18;
    private final double CONVERTDIVISOR = 5;
    private final int MAX_DAYS = 9;
    private final int MIN_DAYS = 0;

    // The forecastday index
    int dayNumber;

    // Expandable ListView
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    TextView txtTitle;
    Button btnNext;
    Button btnPrevious;

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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_providers_screen);

        // Restore preferences
        SharedPreferences settings = getSharedPreferences("provider_location", 0);
        // Our default location will be Dunedin.
        String city = settings.getString("location", "dunedin");

        // Set basic ui settings
        setTitle("Weather Works | " + city);

        // Get references to controls
        txtTitle = (TextView)findViewById(R.id.txtAllProvidersTitle);
        btnNext = (Button)findViewById(R.id.btnNext);
        btnPrevious = (Button)findViewById(R.id.btnPrevious);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // Get the day number from the intent
        Intent intent = getIntent();
        dayNumber = intent.getIntExtra("day", 0);   // Use the first day as default

        // Get all data from the WeatherStore
        getAllInfoFromWeatherStore();

        // prepare list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(AllProvidersScreen.this, listDataHeader, listDataChild);

        // set up list adapter
        expListView.setAdapter(listAdapter);

        txtTitle.setText("All Providers: " + wtitle + " " + mdate);

        // Wire up click handlers
        btnPrevious.setOnClickListener(new BtnPrevListener());
        btnNext.setOnClickListener(new BtnNextListener());
    }

    // Inner class to handle button click events for btnNext
    public class BtnNextListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            // Launch this activity again, passing in the next day's numeric value

            // Don't try go forwards if this is the last day
            if(dayNumber == MAX_DAYS)
            {
                // Report that there's no more days to see that way
                Toast.makeText(AllProvidersScreen.this, "Can't go forward any further.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                // Show the previous day by creating an intent and launching this activity.
                Intent refreshIntent = new Intent(AllProvidersScreen.this, AllProvidersScreen.class);

                int nextDay = (dayNumber += 1);

                refreshIntent.putExtra("day", nextDay);

                startActivity(refreshIntent);
            }
        }
    }

    // Inner class to handle button click events for btnPrevious
    public class BtnPrevListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            // Launch this Activity again, passing in the previous day's numeric value.

            // Don't try go backwards if this is the first day
            if (dayNumber == MIN_DAYS)
            {
                // Report that there's no more days to see that way
                Toast.makeText(AllProvidersScreen.this, "Can't go back any further.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                // Show the previous day by creating an intent and launching this activity.
                Intent refreshIntent = new Intent(AllProvidersScreen.this, AllProvidersScreen.class);

                int nextDay = (dayNumber -= 1);

                refreshIntent.putExtra("day", nextDay);

                startActivity(refreshIntent);
            }
        }
    }

    private void prepareListData()
    {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Add child data headers

        listDataHeader.add("Overviews");
        listDataHeader.add("Temperatures");
        listDataHeader.add("Rain");
        listDataHeader.add("Snow");
        listDataHeader.add("Wind");
        listDataHeader.add("Humidity");
        listDataHeader.add("Pressure");
        listDataHeader.add("Rise / Set");

        // Add all child data

        List<String> descriptions = new ArrayList<>();
        descriptions.add(mforecast);
        descriptions.add(odescription);
        descriptions.add(wdescription);

        DecimalFormat df = new DecimalFormat("####0.00");

        List<String> temperatures = new ArrayList<>();
        temperatures.add("High: " + mmax + " (metsvc)");
        temperatures.add("High: " + df.format(omax) + " (owm)");
        temperatures.add("High: " + wtempHighC + " (wu)");
        temperatures.add("Low: " + mmin + " (metsvc)");
        temperatures.add("Low: " + df.format(omin) + " (owm)");
        temperatures.add("Low: " + wtempLowC + " (wu)");

        List<String> rain = new ArrayList<>();
        rain.add(wrainMm_allDay + " mm rain all day." + " (wu)");
        rain.add(wrainMm_day + " mm rain daytime." + " (wu)");
        rain.add(wrainMm_night + " mm rain nighttime." + " (wu)");
        rain.add(oclouds + " % cloudiness." + " (owm)");

        List<String> snow = new ArrayList<>();
        snow.add(wsnowCm_allDay + " cm snow all day." + " (wu)");
        snow.add(wsnowCm_day + " cm snow daytime." + " (wu)");
        snow.add(wsnowCm_night + " cm snow nighttime." + " (wu)");

        // Convert meters per second to kilometers per hour using this formula.
        double ospeedKph = (ospeed * CONVERTMULTIPLIER)/CONVERTDIVISOR;

        List<String> wind = new ArrayList<>();
        wind.add("Speed: " + df.format(ospeedKph) + " kph." + " (owm)");
        wind.add("Speed: " + wwindKph + " kph." + " (wu)");
        wind.add("Max Speed: " + wwindKphMax + " kph." + " (wu)");
        wind.add("Direction: " + df.format(odeg) + " degrees." + " (owm)");
        wind.add("Direction: " + wwindDirection + "." + " (wu)");

        List<String> humidity = new ArrayList<>();
        humidity.add("Average: " + ohumidity + "." + " (owm)");
        humidity.add("Average: " + waveHumidity + "." + " (wu)");
        humidity.add("Min: " + wminHumidity + "." + " (wu)");
        humidity.add("Max: " + wmaxHumidity + "." + " (wu)");

        List<String> pressure = new ArrayList<>();
        pressure.add("Pressure: " + opressure + " (owm)");

        List<String> riseSet = new ArrayList<>();
        riseSet.add("Sun Rise: " + msunRise + "." + " (metsvc)");
        riseSet.add("Sun Set: " + msunSet + "." + " (metsvc)");
        riseSet.add("Moon Rise: " + mmoonRise + "." + " (metsvc)");
        riseSet.add("Moon Set: " + mmoonSet + "." + " (metsvc)");

        // Put all child data

        listDataChild.put(listDataHeader.get(0), descriptions);
        listDataChild.put(listDataHeader.get(1), temperatures);
        listDataChild.put(listDataHeader.get(2), rain);
        listDataChild.put(listDataHeader.get(3), snow);
        listDataChild.put(listDataHeader.get(4), wind);
        listDataChild.put(listDataHeader.get(5), humidity);
        listDataChild.put(listDataHeader.get(6), pressure);
        listDataChild.put(listDataHeader.get(7), riseSet);
    }

    public static String padLeft(String s, int n)
    {
        return String.format("%1$" + n + "s", s);
    }

    // Get all data we need from the WeatherStore
    public void getAllInfoFromWeatherStore()
    {
        //-------------------------
        // Weather Underground
        //-------------------------

        // Get the array of text forecasts
        ForecastDay[] allTextForecasts = WeatherStore.getInstance().getWuResponse().getForecast().getTxt_forecast().getForecastday();
        ArrayList<ForecastDay> dayForecasts = new ArrayList<>();

        // Get only the day forecasts, which correspond to the information from the Simple forecasts
        for(int i = 0; i < allTextForecasts.length; i++)
        {
            if(i % 2 == 0)  // Get every second element (the day forecasts)
            {
                dayForecasts.add(allTextForecasts[i]);
            }
        }

        // Get the weather description from the text forecast.
        wtitle = dayForecasts.get(dayNumber).getTitle();
        wdescription = dayForecasts.get(dayNumber).getFcttext_metric();

        // Get the current Simple ForecastDay object
        SimpleForecastDay fd = WeatherStore.getInstance().getWuResponse().getForecast().getSimpleforecast().getForecastday()[dayNumber];

        wtempHighC = fd.getHigh().getCelcius();
        wtempLowC = fd.getLow().getCelcius();
        wconditions = fd.getConditions();
        wrainInches_allDay = fd.getQpf_allday().getIn();
        wrainInches_day = fd.getQpf_day().getIn();
        wrainInches_night = fd.getQpf_night().getIn();
        wrainMm_allDay = fd.getQpf_allday().getMm();
        wrainMm_day = fd.getQpf_day().getMm();
        wrainMm_night = fd.getQpf_night().getMm();
        wsnowInches_allDay = fd.getSnow_allday().getIn();
        wsnowInches_day = fd.getSnow_day().getIn();
        wsnowInches_night = fd.getSnow_night().getIn();
        wsnowCm_allDay = fd.getSnow_allday().getCm();
        wsnowCm_day = fd.getSnow_day().getCm();
        wsnowCm_night = fd.getSnow_night().getCm();
        wwindMph = fd.getAvewind().getMph();
        wwindKph = fd.getAvewind().getKph();
        wwindMphMax = fd.getMaxwind().getMph();
        wwindKphMax = fd.getMaxwind().getKph();
        wwindDirection = fd.getAvewind().getDir();
        waveHumidity = fd.getAvehumidity();
        wminHumidity = fd.getMinhumidity();
        wmaxHumidity = fd.getMaxhumidity();

        //-------------------------
        // Metservice
        //-------------------------

        // Get the current day
        Day d = WeatherStore.getInstance().getMetserviceResponse().getDays()[dayNumber];

        mdate = d.getDate();
        mdow = d.getDow();
        mforecast = d.getForecast();
        mforecastWord = d.getForecastWord();
        missuedAt = d.getIssuedAt();
        mmin = d.getMin();
        mmax = d.getMax();
        mday = d.getRiseSet().getDay();
        mid = d.getRiseSet().getId();
        mlocation = d.getRiseSet().getLocation();
        mmoonRise = d.getRiseSet().getMoonRise();
        mmoonSet = d.getRiseSet().getMoonSet();
        msunRise = d.getRiseSet().getSunRise();
        msunSet = d.getRiseSet().getSunSet();

        //-------------------------
        // Open Weather Map
        //-------------------------

        bit.ankem1.WeatherWorks.OpenWeatherMapApi.Day owmd = WeatherStore.getInstance().getOwmResponse().getList()[dayNumber];

        odt = owmd.getDt();
        oday = owmd.getTemp().getDay() - KELVIN;
        omin = owmd.getTemp().getMin() - KELVIN;
        omax = owmd.getTemp().getMax() - KELVIN;
        onight = owmd.getTemp().getNight() - KELVIN;
        oeve = owmd.getTemp().getEve() - KELVIN;
        omorn = owmd.getTemp().getMorn() - KELVIN;
        opressure = owmd.getPressure();
        ohumidity = owmd.getHumidity();
        oid = owmd.getWeather()[0].getId();
        odescription = owmd.getWeather()[0].getDescription();
        oicon = owmd.getWeather()[0].getIcon();
        omain = owmd.getWeather()[0].getMain();
        ospeed = owmd.getSpeed();
        odeg = owmd.getDeg();
        oclouds = owmd.getClouds();
        orain = owmd.getRain();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_all_providers_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }
}
