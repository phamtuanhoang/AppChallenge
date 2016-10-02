package camonia.appchallenge;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuanhoang.pham on 2/10/16.
 */
public class Flight {


    @SerializedName("destination")
    private String destination;
    @SerializedName("departure_date")
    private String departure_date;
    @SerializedName("return_date")
    private String return_date;
    @SerializedName("price")
    private String price;
    @SerializedName("airline")
    private String airline;


    public Flight(String destination,  String departure_date, String return_date, String price, String airline) {
        this.destination = destination;
        this.departure_date = departure_date;
        this.return_date = return_date;
        this.price = price;
        this.airline = airline;
    }
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }
}
