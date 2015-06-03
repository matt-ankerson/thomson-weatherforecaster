package bit.ankem1.WeatherWorks;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

// This activity shows the next 10 days available for forecast
public class ForecastDaysScreen extends ActionBarActivity
{
    private final int TIME_PERIOD_IN_DAYS = 10;
    public final int ALLPROVIDERS = 0;
    public final int METSERVICE = 1;
    public final int OPENWEATHERMAP = 2;
    public final int WEATHERUNDERGROUND = 3;
    // Controls
    ListView lvUpcomingDays;
    int provider;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecast_list);

        // Get the intent
        Intent intent = getIntent();
        provider = intent.getIntExtra("provider", METSERVICE); // Use Metservice as the default

        // Get references to controls
        lvUpcomingDays = (ListView)findViewById(R.id.lvUpcomingDays);

        // Get an array of dates between noe and 10 days time
        Calendar calendar = new GregorianCalendar();
        Date start = calendar.getTime();
        calendar.add(Calendar.DATE, TIME_PERIOD_IN_DAYS);
        Date end = calendar.getTime();

        ArrayList<Date> days = getDaysInBetween(start, end);

        // Get a String representation of our list of dates.
        //      We also need to account for WeatherUnderground which
        //      provides nightly text-based forecasts too.
        ArrayList<String> stringDays;

        if(provider == WEATHERUNDERGROUND) {
            //stringDays = convertDatesWithNightsToString(days);
            stringDays = convertDatesToString(days);    // <<-- let's just stick to the standard 10 day format.
        }
        else {
            stringDays = convertDatesToString(days);
        }

        // Populate the listview with the 10 days from now.
        populateDaysListView(stringDays);

        // Bind click handler for ListView
        lvUpcomingDays.setOnItemClickListener(new ListViewClickHandler());
    }

    // Inner class for handling ListView click events
    public class ListViewClickHandler implements AdapterView.OnItemClickListener
    {

        // Event handling code for selecting an item from the ListView
        @Override
        public void onItemClick(AdapterView<?> list, View itemView, int position, long id)
        {
            ActionBarActivity nextActivity;

            // Create the Activity we want to launch, based on our chosen provider.
            // We also need to assess whether or not the appropriate data is available.
            //      - don't try to use data that doesn't exist.

            boolean proceed = true;

            if(provider == METSERVICE)
            {
                nextActivity = new MetserviceScreen();
                if(WeatherStore.getInstance().getMetserviceResponse() == null)
                    proceed = false;
            }
            else
            {
                if(provider == OPENWEATHERMAP)
                {
                    nextActivity = new OpenWeatherMapScreen();
                    if(WeatherStore.getInstance().getOwmResponse() == null)
                        proceed = false;
                }
                else
                {
                    if(provider == ALLPROVIDERS)
                    {
                        nextActivity = new AllProvidersScreen();
                        // check that ALL data exists in the WeatherStore
                        if((WeatherStore.getInstance().getOwmResponse() == null) ||
                                (WeatherStore.getInstance().getWuResponse() == null) ||
                                (WeatherStore.getInstance().getMetserviceResponse() == null))
                            proceed = false;
                    }
                    else // provider == WEATHERUNDERGROUND
                    {
                        nextActivity = new WUScreen();
                        if (WeatherStore.getInstance().getWuResponse() == null)
                            proceed = false;
                    }
                }
            }

            // Create an intent
            Intent goToIntent = new Intent(ForecastDaysScreen.this, nextActivity.getClass());

            // Put a integer value in the intent to indicate which day we chose.
            goToIntent.putExtra("day", position);

            // If the data we need was successfully loaded
            if(proceed) {
                startActivity(goToIntent);
            }
            else {
                // Report the absence of data
                Toast.makeText(ForecastDaysScreen.this, "Error loading data.\nReturn to home screen to reload.", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public void populateDaysListView(ArrayList<String> days)
    {
        // Create an ArrayAdapter
        ArrayAdapter daysAdapter = new ArrayAdapter(ForecastDaysScreen.this, android.R.layout.simple_list_item_1, days);
        lvUpcomingDays.setAdapter(daysAdapter);
    }

    // Return a list of dates between the specified dates
    public static ArrayList<Date> getDaysInBetween(Date startdate, Date enddate)
    {
        ArrayList<Date> dates = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate))
        {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    // Accept a list of dates, return a string representation of that list
    public static ArrayList<String> convertDatesToString(ArrayList<Date> dates)
    {
        ArrayList<String> stringDates = new ArrayList<>();

        for(int i = 0; i < dates.size(); i++)
        {
            Format formatter = new SimpleDateFormat("E dd MMM");
            String s = formatter.format(dates.get(i));
            stringDates.add(s);
        }

        return stringDates;
    }

    // Accept a list of dates, return a string representation of that list.
    //      Include an entry for each night.
    public static ArrayList<String> convertDatesWithNightsToString(ArrayList<Date> dates)
    {
        ArrayList<String> stringDates = new ArrayList<>();

        for(int i = 0; i < dates.size(); i++)
        {
            Format formatter = new SimpleDateFormat("E dd MMM");
            String day = formatter.format(dates.get(i));
            stringDates.add(day + " (day)");
            String night = formatter.format(dates.get(i));
            stringDates.add(night + " (night)");
        }

        return stringDates;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forecast_days_screen, menu);
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
