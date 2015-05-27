package bit.ankem1.WeatherWorks.MetserviceApi;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by matt on 17/05/15.
 */
public class MetserviceRestClient
{
    private static IMetservice REST_CLIENT;
    private static String ROOT = "http://metservice.com/publicData";

    // Constructor is hidden because this is a singleton
    private MetserviceRestClient()
    {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(ROOT)
                .setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(IMetservice.class);    // Rest client instance
    }

    public static IMetservice get()
    {
        if (REST_CLIENT == null)
        {
            new MetserviceRestClient();
        }
        return REST_CLIENT;
    }
}
