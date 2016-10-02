package camonia.appchallenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import camonia.appchallenge.net.ApiClient;
import camonia.appchallenge.net.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static  String apiKey = "xOPdGtGYVSdbUGNJFPvbkG473XUh1usc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //getData
        getData();

    }

    public void getData(){
    //        JsonObject json = new JsonObject();
    //        json.addProperty("origin", "NYC");
    //        json.addProperty("destination", "SIN");
    //        json.addProperty("departure_date", "2016-11-16--2016-11-30");
    //        json.addProperty("one-way", "false");
    //        json.addProperty("duration", "1--15");
    //        json.addProperty("direct", "false");
    //        json.addProperty("apikey", "xOPdGtGYVSdbUGNJFPvbkG473XUh1usc");
    //
    //        Ion.with(this).load(ApiClient.FLIGHT_URL)
    //                .setJsonObjectBody(json)
    //                .asJsonObject()
    //                .setCallback(new FutureCallback<JsonObject>() {
    //                    @Override
    //                    public void onCompleted(Exception e, JsonObject result) {
    //                        try{
    //                            Log.e("Result", result.toString());
    //                        }catch (Exception error){
    //                            Log.e("error", e.toString());
    //
    //                        }
    //
    ////                        if (result.get("results").getAsString() != null) {
    ////                            //go directly to user profile activity
    ////                            //load user details to local storate
    ////                        }else{
    ////                            Log.e("MainActivity", "Error");
    ////                        }
    //                    }
    //                });

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<FlightResponse> call = apiService.getFlight(apiKey,"NYC","SIN","2016-11-11--2016-11-20","1--15","false","false");
        call.enqueue(new Callback<FlightResponse>() {
            @Override
            public void onResponse(Call<FlightResponse> call, Response<FlightResponse> response) {
                int statusCode = response.code();
                Log.d("Reponse",response.toString());

                List<Flight> flights = response.body().getResults();
                Log.d("Flight","Number flights received: " + flights.size());
                Log.d("Flight",flights.get(0).getAirline());
                Log.d("Flight",flights.get(0).getDestination());
                Log.d("Flight",flights.get(0).getPrice());
                Log.d("Flight",flights.get(0).getDeparture_date());
                Log.d("Flight",flights.get(0).getReturn_date());
            }

            @Override
            public void onFailure(Call<FlightResponse> call, Throwable t) {
                Log.d("FlightError","Error");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
