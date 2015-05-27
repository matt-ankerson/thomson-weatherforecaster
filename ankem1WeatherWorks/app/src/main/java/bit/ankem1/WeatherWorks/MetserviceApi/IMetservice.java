package bit.ankem1.WeatherWorks.MetserviceApi;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by matt on 17/05/15.
 */
public interface IMetservice
{
    // Accept a string containing all our query parameters.
    @GET("/localForecast{city}")
    void getWeather(@Path("city") String city, Callback<MetserviceResponse> callback);
}
