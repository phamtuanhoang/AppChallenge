package camonia.appchallenge.net;

import org.json.JSONArray;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tuanhoang.pham on 19/7/16.
 */
public interface ApiInterface {

    @GET("flights/inspiration-search/")
    Call<FlightResponse> getFlight(@Query("apikey") String apiKey,
                                   @Query("origin") String origin,
                                   @Query("destination") String destination,
                                   @Query("departure_date") String departureDate,
                                   @Query("duration") String duration,
                                   @Query("one-way") String oneWay,
                                   @Query("direct") String direct);

    @GET("atibeacon/beacons/1/")
    Call<JSONArray> getBeacon(@Query("appID") String appId,
                              @Query("appKey") String apiKey,
                              @Query("airportCode") String airportCode,
                              @Query("preservePendingNewBeacons") String pendingBeacon);

}



