package bit.ankem1.WeatherWorks.OpenWeatherMapApi;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by matt on 14/05/15.
 */
public interface IOpenWeatherMap
{
    // Accept a query map containing all our query parameters.
    @GET("/daily/")
    void getWeather(@QueryMap Map<String, String> params, Callback<OpenWeatherMapResponse> callback);
}
