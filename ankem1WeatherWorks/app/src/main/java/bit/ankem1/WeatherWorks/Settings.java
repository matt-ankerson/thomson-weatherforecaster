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

// This activity is intended to allow the user to select a location for Metservice
public class Settings extends ActionBarActivity
{
    // Screen controls
    Spinner spnLocations;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Get references to controls
        btnConfirm = (Button)findViewById(R.id.btnConfirm);
        spnLocations = (Spinner)findViewById(R.id.spnLocation);

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

            // Create a new Intent
            Intent returnIntent = new Intent();

            // Load up the return value. (the selected item in the location spinner)
            returnIntent.putExtra("location", location);

            // Set the termination code flag
            setResult(Activity.RESULT_OK, returnIntent);

            // Pop this activity off the activity stack so we return to the calling activity.
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
