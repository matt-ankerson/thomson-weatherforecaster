package bit.ankem1.WeatherWorks;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.HashMap;

import bit.ankem1.WeatherWorks.OpenWeatherMapApi.Day;
import bit.ankem1.WeatherWorks.OpenWeatherMapApi.OpenWeatherMapResponse;
import bit.ankem1.WeatherWorks.OpenWeatherMapApi.OpenWeatherMapRestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

// This Activity shows all forecast information from OpenWeatherMap
public class OpenWeatherMapScreen extends ActionBarActivity
{
    private final double KELVIN = 273.15;
    private final int MAX_DAYS = 9;
    private final int MIN_DAYS = 0;

    // index for this forecast day
    int dayNumber;

    // Controls
    TextView txtOwmTitle;
    TextView txtOWMDescription;
    TextView txtOWMTemperature;
    TextView txtOWMPressure;
    TextView txtOWMHumidity;
    TextView txtOWMWind;
    TextView txtOWMPrecipitation;
    Button btnOWMNext;
    Button btnOWMPrevious;

    // Forecast data for this day
    int dt;
    // All temperature info for this day
    double day;     // Temperatures from OWM are in Kelvin, (subtract 273.15 to convert to Celsius)
    double min;
    double max;
    double night;
    double eve;
    double morn;
    double pressure;
    double humidity;
    // Additional weather info for this day
    int id;
    String main;
    String description;
    String icon;
    double speed;
    double deg;
    double clouds;
    double rain;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_weather_map_screen);

        // Set basic ui settings
        setTitle("Weather Works");

        // Get references to the controls
        txtOwmTitle = (TextView)findViewById(R.id.txtOwmTitle);
        txtOWMDescription = (TextView)findViewById(R.id.txtOWMDescription);
        txtOWMHumidity = (TextView)findViewById(R.id.txtOWMHumidity);
        txtOWMPrecipitation = (TextView)findViewById(R.id.txtOWMPrecipitation);
        txtOWMPressure = (TextView)findViewById(R.id.txtOWMPressure);
        txtOWMTemperature = (TextView)findViewById(R.id.txtOWMTemperature);
        txtOWMWind = (TextView)findViewById(R.id.txtOWMWind);

        btnOWMNext = (Button)findViewById(R.id.btnOWMNext);
        btnOWMPrevious = (Button)findViewById(R.id.btnOWMPrevious);

        // Get the day number from the intent
        Intent intent = getIntent();
        dayNumber = intent.getIntExtra("day", 0);   // Use the first day as default

        // Get all data from the WeatherStore
        getAllInfoFromWeatherStore();

        // Populate our textviews
        populateTextViews();

        // Wire up click handlers
        btnOWMPrevious.setOnClickListener(new BtnPrevListener());
        btnOWMNext.setOnClickListener(new BtnNextListener());
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
                Toast.makeText(OpenWeatherMapScreen.this, "Can't go forward any further.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                // Show the previous day by creating an intent and launching this activity.
                Intent refreshIntent = new Intent(OpenWeatherMapScreen.this, OpenWeatherMapScreen.class);

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
                Toast.makeText(OpenWeatherMapScreen.this, "Can't go back any further.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                // Show the previous day by creating an intent and launching this activity.
                Intent refreshIntent = new Intent(OpenWeatherMapScreen.this, OpenWeatherMapScreen.class);

                int nextDay = (dayNumber -= 1);

                refreshIntent.putExtra("day", nextDay);

                startActivity(refreshIntent);
            }
        }
    }


    // Populate our TextViews with data belonging to this class
    public void populateTextViews()
    {
        // Metservice conveniently provides a text representation of today's date,
        //      try to get that if it's available.
        bit.ankem1.WeatherWorks.MetserviceApi.Day metsvcDay = WeatherStore.getInstance().getMetserviceResponse().getDays()[dayNumber];

        if(metsvcDay != null)
        {
            String dow = metsvcDay.getDow();
            String date = metsvcDay.getDate();
            txtOwmTitle.setText("Opn Wthr Map: " + dow + " " + date);
        }
        else    // else it's not available, don't worry about it.
        {
            txtOwmTitle.setText("Opn Wthr Map: ");
        }

        DecimalFormat df = new DecimalFormat("####0.00");

        txtOWMDescription.setText(main + "(" + description + ").");
        txtOWMTemperature.setText("Min: " + df.format(min) +
                                    "\nMax: " + df.format(max) +
                                    "\nMorning: " + df.format(morn) +
                                    "\nDay: " + df.format(day) +
                                    "\nEvening: " + df.format(eve) +
                                    "\nNight: " + df.format(night));
        txtOWMWind.setText("Kph: " + df.format(speed) +
                            "\nDirection: " + df.format(deg) + " degrees");
        txtOWMPressure.setText("Pressure: " + df.format(pressure));
        txtOWMPrecipitation.setText("Clouds: " + df.format(clouds) + " %" +
                                    "\nRain: " + df.format(rain) + " mm for prev 3hrs");
        txtOWMHumidity.setText("Humidity: " + df.format(humidity));
    }

    // Get all info we need from the WeatherStore
    public void getAllInfoFromWeatherStore()
    {
        Day d = WeatherStore.getInstance().getOwmResponse().getList()[dayNumber];

        dt = d.getDt();
        day = d.getTemp().getDay() - KELVIN;
        min = d.getTemp().getMin() - KELVIN;
        max = d.getTemp().getMax() - KELVIN;
        night = d.getTemp().getNight() - KELVIN;
        eve = d.getTemp().getEve() - KELVIN;
        morn = d.getTemp().getMorn() - KELVIN;
        pressure = d.getPressure();
        humidity = d.getHumidity();
        id = d.getWeather()[0].getId();
        description = d.getWeather()[0].getDescription();
        icon = d.getWeather()[0].getIcon();
        main = d.getWeather()[0].getMain();
        speed = d.getSpeed();
        deg = d.getDeg();
        clouds = d.getClouds();
        rain = d.getRain();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_open_weather_map_screen, menu);
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
