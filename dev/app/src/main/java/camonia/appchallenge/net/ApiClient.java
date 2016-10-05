package camonia.appchallenge.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tuanhoang.pham on 19/7/16. 7e8f60e325cd06e164799af1e317d7a7
 */
public class ApiClient {

    public static final String BEACON_END_POINT = "https://cube.api.aero/";
//    public static final String BASE_URL = "https://api.sandbox.amadeus.com/v1.2/";
//    public static final String FLIGHT_URL = BASE_URL+ "flights/inspiration-search/";

    //create retrofit instance
    private static Retrofit retrofit = null;

    //need to set up client
    public static Retrofit getClient (){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BEACON_END_POINT).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
