package bit.ankem1.WeatherWorks;

import android.app.Application;

/**
 * Created by matt on 17/05/15.
 */
// This is the Application class and it will create the instance of our
// WeatherStore singleton.
public class WeatherWorksApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        // Initialize the singleton so its instance
        // is bound to the application process.
        initSingletons();
    }

    protected void initSingletons()
    {
        // Initialize the instance of MySingleton
        WeatherStore.initInstance();
    }
}
