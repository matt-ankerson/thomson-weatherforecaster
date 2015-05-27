package bit.ankem1.WeatherWorks.WeatherUndergroundApi;


import retrofit.Callback;
import retrofit.http.EncodedPath;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by matt on 18/05/15.
 */
public interface IWeatherUnderground
{
    // Accept a string containing all our query parameters.
    @GET("/api/{params}")
    void getWeather(@EncodedPath("params") String params, Callback<WeatherUndergroundResponse> callback);
}
