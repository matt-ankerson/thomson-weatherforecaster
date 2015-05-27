package bit.ankem1.WeatherWorks.WeatherUndergroundApi;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by matt on 18/05/15.
 */

public class WeatherUndergroundRestClient
{
    private static IWeatherUnderground REST_CLIENT;
    private static String ROOT = "http://api.wunderground.com";

    // Constructor is hidden because this is a singleton
    private WeatherUndergroundRestClient()
    {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(ROOT)
                .setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(IWeatherUnderground.class);    // Rest client instance
    }

    public static IWeatherUnderground get()
    {
        if (REST_CLIENT == null)
        {
            new WeatherUndergroundRestClient();
        }
        return REST_CLIENT;
    }
}
