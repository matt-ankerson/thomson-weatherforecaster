package bit.ankem1.WeatherWorks;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

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

    // index for this forecast day
    int dayNumber;

    // Controls
    TextView txtOWMDescription;
    TextView txtOWMTemperature;
    TextView txtOWMPressure;
    TextView txtOWMHumidity;
    TextView txtOWMWind;
    TextView txtOWMPrecipitation;

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

        // Get references to the controls
        txtOWMDescription = (TextView)findViewById(R.id.txtOWMDescription);
        txtOWMHumidity = (TextView)findViewById(R.id.txtOWMHumidity);
        txtOWMPrecipitation = (TextView)findViewById(R.id.txtOWMPrecipitation);
        txtOWMPressure = (TextView)findViewById(R.id.txtOWMPressure);
        txtOWMTemperature = (TextView)findViewById(R.id.txtOWMTemperature);
        txtOWMWind = (TextView)findViewById(R.id.txtOWMWind);

        // Get the day number from the intent
        Intent intent = getIntent();
        dayNumber = intent.getIntExtra("day", 0);   // Use the first day as default

        // Get all data from the WeatherStore
        getAllInfoFromWeatherStore();

        // Populate our textviews
        populateTextViews();
    }


    // Populate our TextViews with data belonging to this class
    public void populateTextViews()
    {
        txtOWMDescription.setText(main + "(" + description + ").");
        txtOWMTemperature.setText("Min: " + min +
                                    "\nMax: " + max +
                                    "\nMorning: " + morn +
                                    "\nDay: " + day +
                                    "\nEvening: " + eve +
                                    "\nNight: " + night);
        txtOWMWind.setText("Kph: " + speed +
                            "\nDirection: " + deg + " degrees");
        txtOWMPressure.setText("Pressure: " + pressure);
        txtOWMPrecipitation.setText("Clouds: " + clouds + " %" +
                                    "\nRain: " + rain + " mm for prev 3hrs");
        txtOWMHumidity.setText("Humidity: " + humidity);
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
        getMenuInflater().inflate(R.menu.menu_open_weather_map_screen, menu);
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
