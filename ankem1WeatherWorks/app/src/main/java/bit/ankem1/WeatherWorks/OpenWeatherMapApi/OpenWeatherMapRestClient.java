package bit.ankem1.WeatherWorks.OpenWeatherMapApi;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by matt on 14/05/15.
 */
public class OpenWeatherMapRestClient
{
    private static IOpenWeatherMap REST_CLIENT;
    private static String ROOT = "http://api.openweathermap.org/data/2.5/forecast";

    // Constructor is hidden because this is a singleton
    private OpenWeatherMapRestClient()
    {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(ROOT)
                .setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(IOpenWeatherMap.class);    // Rest client instance
    }

    public static IOpenWeatherMap get()
    {
        if (REST_CLIENT == null)
        {
            new OpenWeatherMapRestClient();
        }
        return REST_CLIENT;
    }


}

