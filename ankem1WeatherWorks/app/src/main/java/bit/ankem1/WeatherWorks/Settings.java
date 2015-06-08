package bit.ankem1.WeatherWorks;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.HashMap;

// This activity is intended to allow the user to select a location for Metservice
public class Settings extends ActionBarActivity
{
    // HashMap for mapping Places to GeoCoordinates
    HashMap<String, Coordinate> fullyQualifiedLocations;

    // Screen controls
    Spinner spnLocations;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set basic ui settings
        setTitle("Weather Works");

        // Get references to controls
        btnConfirm = (Button)findViewById(R.id.btnConfirm);
        spnLocations = (Spinner)findViewById(R.id.spnLocation);

        // Populate the hashmap containing place names and respective coordinates
        fullyQualifiedLocations = new HashMap<>();
        populateLocationHashMap();

        // Load our spinner up with available place names

        // Create an ArrayAdapter using the string array and default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.locations_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the spinner's adaptor
        spnLocations.setAdapter(adapter);

        // Set the click listener for the confirm button
        btnConfirm.setOnClickListener(new ConfirmBtnHandler());
    }

    // Inner class to handle confirm button click
    public class ConfirmBtnHandler implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            // Get the selected item from the spinner
            String location = spnLocations.getSelectedItem().toString();

            // Get the latitude and longitude from the HashMap, using the Spinner item to
            //  index the hashmap.
            double latitude = fullyQualifiedLocations.get(location).getLatitude();
            double longitude = fullyQualifiedLocations.get(location).getLongitude();

            // Create a new Intent
            Intent returnIntent = new Intent();

            // Load up the return intent
            returnIntent.putExtra("location", location);
            returnIntent.putExtra("latitude", latitude);
            returnIntent.putExtra("longitude", longitude);

            // Set the termination code flag
            setResult(Activity.RESULT_OK, returnIntent);

            // Pop this activity off the activity stack so we return to the calling activity.
            finish();
        }
    }

    public void populateLocationHashMap()
    {
        String[] textLocations = getResources().getStringArray(R.array.locations_array);

        // Populate our HashMap of fully qualified locations.
        //  It should be noted that this is bad, geo-coordinates should be obtained
        //  by reverse geo-location lookup.
        fullyQualifiedLocations.put(textLocations[0], new Coordinate(-45.249167, 169.379722));  // Alexandra
        fullyQualifiedLocations.put(textLocations[1], new Coordinate(-36.848460, 174.763332));  // Auckland
        fullyQualifiedLocations.put(textLocations[2], new Coordinate(-43.908381, 171.748567));  // Ashburton
        fullyQualifiedLocations.put(textLocations[3], new Coordinate(-43.532054, 172.636225));  // Chch
        fullyQualifiedLocations.put(textLocations[4], new Coordinate(-45.878760, 170.502798));  // Dunedin
        fullyQualifiedLocations.put(textLocations[5], new Coordinate(-46.098799, 168.945819));  // Gore
        fullyQualifiedLocations.put(textLocations[6], new Coordinate(-37.787001, 175.279253));  // Hamilton
        fullyQualifiedLocations.put(textLocations[7], new Coordinate(-46.413187, 168.353773));  // Invercargill
        fullyQualifiedLocations.put(textLocations[8], new Coordinate(-41.270632, 173.283965));  // Nelson
        fullyQualifiedLocations.put(textLocations[9], new Coordinate(-45.097512, 170.970415));  // Oamaru
        fullyQualifiedLocations.put(textLocations[10], new Coordinate(-45.031162, 168.662644));  // QTown
        fullyQualifiedLocations.put(textLocations[11], new Coordinate(-44.396972, 171.254973));  // Timaru
        fullyQualifiedLocations.put(textLocations[12], new Coordinate(-41.286460, 174.776236));  // Wellington


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_settings, menu);
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
