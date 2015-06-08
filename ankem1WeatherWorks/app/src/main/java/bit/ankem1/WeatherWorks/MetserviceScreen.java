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

import bit.ankem1.WeatherWorks.MetserviceApi.Day;

// This activity shows all information from Metservice
public class MetserviceScreen extends ActionBarActivity
{
    private final int MAX_DAYS = 9;
    private final int MIN_DAYS = 0;

    // Screen controls
    TextView txtMetserviceTitle;
    TextView txtMetDescription;
    TextView txtMetIssuedAt;
    TextView txtMetTemperature;
    TextView txtMetLocation;
    TextView txtMetSunRiseSet;
    TextView txtMetMoonRiseSet;
    Button btnMSNext;
    Button btnMSPrevious;

    // The forecast day index
    int dayNumber;

    // Forecast data
    String date;
    String dow;
    String forecast;
    String forecastWord;
    String issuedAt;
    String min;
    String max;
    String day;
    String id;
    String location;
    String moonRise;
    String moonSet;
    String sunRise;
    String sunSet;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metservice_screen);

        // Get references to controls
        txtMetserviceTitle = (TextView)findViewById(R.id.txtMetserviceTitle);
        txtMetDescription = (TextView)findViewById(R.id.txtMetDescription);
        txtMetIssuedAt = (TextView)findViewById(R.id.txtMetIssuedAt);
        txtMetLocation = (TextView)findViewById(R.id.txtMetLocation);
        txtMetTemperature = (TextView)findViewById(R.id.txtMetTemperature);
        txtMetSunRiseSet = (TextView)findViewById(R.id.txtMetSunRiseSet);
        txtMetMoonRiseSet = (TextView)findViewById(R.id.txtMetMoonRiseSet);

        btnMSNext = (Button)findViewById(R.id.btnMSNext);
        btnMSPrevious = (Button)findViewById(R.id.btnMSPrevious);

        // Get the day number from the intent
        Intent intent = getIntent();
        dayNumber = intent.getIntExtra("day", 0);   // Use the first day as default

        // Get all data from the WeatherStore
        getAllInfoFromWeatherStore();

        // Populate our textviews
        populateTextViews();

        // Wire up click handlers
        btnMSPrevious.setOnClickListener(new BtnPrevListener());
        btnMSNext.setOnClickListener(new BtnNextListener());

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
                Toast.makeText(MetserviceScreen.this, "Can't go forward any further.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                // Show the previous day by creating an intent and launching this activity.
                Intent refreshIntent = new Intent(MetserviceScreen.this, MetserviceScreen.class);

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
                Toast.makeText(MetserviceScreen.this, "Can't go back any further.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                // Show the previous day by creating an intent and launching this activity.
                Intent refreshIntent = new Intent(MetserviceScreen.this, MetserviceScreen.class);

                int nextDay = (dayNumber -= 1);

                refreshIntent.putExtra("day", nextDay);

                startActivity(refreshIntent);
            }
        }
    }

    // Use data belonging to this class to populate our screen controls
    public void populateTextViews()
    {
        txtMetserviceTitle.setText("Metservice: " + dow);

        txtMetDescription.setText(forecastWord + ". " + forecast);
        txtMetIssuedAt.setText(issuedAt);
        txtMetLocation.setText(location);
        txtMetTemperature.setText("High: " + max +
                                    "\nLow: " + min);
        txtMetSunRiseSet.setText("Rise: " + sunRise +
                                    "\nSet: " + sunSet);
        txtMetMoonRiseSet.setText("Rise: " + moonRise +
                                    "\nSet: " + moonSet);
    }

    // Get all data we need from the WeatherStore
    public void getAllInfoFromWeatherStore()
    {
        // Get the current day
        Day d = WeatherStore.getInstance().getMetserviceResponse().getDays()[dayNumber];

        date = d.getDate();
        dow = d.getDow();
        forecast = d.getForecast();
        forecastWord = d.getForecastWord();
        issuedAt = d.getIssuedAt();
        min = d.getMin();
        max = d.getMax();
        day = d.getRiseSet().getDay();
        id = d.getRiseSet().getId();
        location = d.getRiseSet().getLocation();
        moonRise = d.getRiseSet().getMoonRise();
        moonSet = d.getRiseSet().getMoonSet();
        sunRise = d.getRiseSet().getSunRise();
        sunSet = d.getRiseSet().getSunSet();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_metservice_screen, menu);
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
