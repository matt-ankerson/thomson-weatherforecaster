package bit.ankem1.WeatherWorks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bit.ankem1.WeatherWorks.MetserviceApi.Day;
import bit.ankem1.WeatherWorks.WeatherUndergroundApi.ForecastDay;
import bit.ankem1.WeatherWorks.WeatherUndergroundApi.SimpleForecastDay;

// This activity displays information from weather underground
// If we reach this activity, we can safely use the data in WeatherStore.
public class WUScreen extends ActionBarActivity
{
    private final int MAX_DAYS = 9;
    private final int MIN_DAYS = 0;

    // Controls
    TextView txtWuTitle;
    TextView txtWuDescription;
    TextView txtWuTemperature;
    TextView txtWuConditions;
    TextView txtWuPrecipitation;
    TextView txtWuPrecipitationSnow;
    TextView txtWuWind;
    TextView txtWuHumidity;
    Button btnWUNext;
    Button btnWUPrevious;


    // The forecastday index
    int dayNumber;

    // WeatherUnderground data (txt_forecast array)
    String title;
    String description;

    // WeatherUnderground data (simpleforecast array)
    String tempHighC;
    String tempLowC;
    String conditions;
    double rainInches_allDay;
    double rainInches_day;
    double rainInches_night;
    double rainMm_allDay;
    double rainMm_day;
    double rainMm_night;
    double snowInches_allDay;
    double snowInches_day;
    double snowInches_night;
    double snowCm_allDay;
    double snowCm_day;
    double snowCm_night;
    double windMph;
    double windKph;
    double windMphMax;
    double windKphMax;
    String windDirection;
    double aveHumidity;
    double minHumidity;
    double maxHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuscreen);

        // Restore preferences
        SharedPreferences settings = getSharedPreferences("provider_location", 0);
        // Our default location will be Dunedin.
        String city = settings.getString("location", "dunedin");

        // Set basic ui settings
        setTitle("Weather Works | " + city);

        // Get references to controls
        txtWuTitle = (TextView)findViewById(R.id.txtWuTitle);
        txtWuDescription = (TextView)findViewById(R.id.txtWuDescription);
        txtWuConditions = (TextView)findViewById(R.id.txtWuConditions);
        txtWuTemperature = (TextView)findViewById(R.id.txtWuTemperature);
        txtWuHumidity = (TextView)findViewById(R.id.txtWuHumidity);
        txtWuPrecipitation = (TextView)findViewById(R.id.txtWuPrecipitation);
        txtWuPrecipitationSnow = (TextView)findViewById(R.id.txtWuPrecipitationSnow);
        txtWuWind = (TextView)findViewById(R.id.txtWuWind);

        btnWUNext = (Button)findViewById(R.id.btnWUNext);
        btnWUPrevious = (Button)findViewById(R.id.btnWUPrevious);

        // Get the day number from the intent
        Intent intent = getIntent();
        dayNumber = intent.getIntExtra("day", 0);   // Use the first day as default

        // Get information from the WeatherStore
        getAllInfoFromWeatherStore();

        // Populate all of our text fields
        populateTextFields();

        // Wire up click handlers
        btnWUPrevious.setOnClickListener(new BtnPrevListener());
        btnWUNext.setOnClickListener(new BtnNextListener());
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
                Toast.makeText(WUScreen.this, "Can't go forward any further.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                // Show the previous day by creating an intent and launching this activity.
                Intent refreshIntent = new Intent(WUScreen.this, WUScreen.class);

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
                Toast.makeText(WUScreen.this, "Can't go back any further.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                // Show the previous day by creating an intent and launching this activity.
                Intent refreshIntent = new Intent(WUScreen.this, WUScreen.class);

                int nextDay = (dayNumber -= 1);

                refreshIntent.putExtra("day", nextDay);

                startActivity(refreshIntent);
            }
        }
    }

    // Use data belonging to this class to populate our screen controls
    public void populateTextFields()
    {
        // Metservice conveniently provides a text representation of today's date,
        //      try to get that if it's available.
        Day metsvcDay = WeatherStore.getInstance().getMetserviceResponse().getDays()[dayNumber];

        if(metsvcDay != null)
        {
            String date = metsvcDay.getDate();
            txtWuTitle.setText("WU: " + title + " " + date);
        }
        else    // else it's not available, don't worry about it.
        {
            txtWuTitle.setText("WU: " + title);
        }

        // Set the rest of the text controls
        txtWuDescription.setText(description);
        txtWuConditions.setText(conditions);
        txtWuTemperature.setText("High: " + tempHighC + " Low: " + tempLowC);
        txtWuHumidity.setText("Max: " + maxHumidity + "\nMin: " + minHumidity + "\nAve: " + aveHumidity);
        txtWuPrecipitation.setText("Day (in): " + rainInches_day +
                                    "\nNight (in): " + rainInches_night +
                                    "\nTotal (in): " + rainInches_allDay +
                                    "\n\nDay (mm): " + rainMm_day +
                                    "\nNight (mm): " + rainMm_night +
                                    "\nTotal (mm): " + rainMm_allDay);
        txtWuPrecipitationSnow.setText("Day (in): " + snowInches_day +
                                        "\nNight (in): " + snowInches_night +
                                        "\nTotal (in): " + snowInches_allDay +
                                        "\n\nDay (cm): " + snowCm_day +
                                        "\nNight (cm): " + snowCm_night +
                                        "\nTotal (cm): " + snowCm_allDay);
        txtWuWind.setText("Average mph: " + windMph +
                            "\nAverage kph: " + windKph +
                            "\n\nMax mph: " + windMphMax +
                            "\nMax kph: " + windKphMax +
                            "\n\nDirection: " + windDirection);
    }

    // Get all data we need from the WeatherStore
    public void getAllInfoFromWeatherStore()
    {
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
        title = dayForecasts.get(dayNumber).getTitle();
        description = dayForecasts.get(dayNumber).getFcttext_metric();

        // Get the current Simple ForecastDay object
        SimpleForecastDay fd = WeatherStore.getInstance().getWuResponse().getForecast().getSimpleforecast().getForecastday()[dayNumber];

        tempHighC = fd.getHigh().getCelcius();
        tempLowC = fd.getLow().getCelcius();
        conditions = fd.getConditions();
        rainInches_allDay = fd.getQpf_allday().getIn();
        rainInches_day = fd.getQpf_day().getIn();
        rainInches_night = fd.getQpf_night().getIn();
        rainMm_allDay = fd.getQpf_allday().getMm();
        rainMm_day = fd.getQpf_day().getMm();
        rainMm_night = fd.getQpf_night().getMm();
        snowInches_allDay = fd.getSnow_allday().getIn();
        snowInches_day = fd.getSnow_day().getIn();
        snowInches_night = fd.getSnow_night().getIn();
        snowCm_allDay = fd.getSnow_allday().getCm();
        snowCm_day = fd.getSnow_day().getCm();
        snowCm_night = fd.getSnow_night().getCm();
        windMph = fd.getAvewind().getMph();
        windKph = fd.getAvewind().getKph();
        windMphMax = fd.getMaxwind().getMph();
        windKphMax = fd.getMaxwind().getKph();
        windDirection = fd.getAvewind().getDir();
        aveHumidity = fd.getAvehumidity();
        minHumidity = fd.getMinhumidity();
        maxHumidity = fd.getMaxhumidity();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_wuscreen, menu);
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
