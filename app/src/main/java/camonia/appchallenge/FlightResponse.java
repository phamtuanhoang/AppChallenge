package camonia.appchallenge;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tuanhoang.pham on 2/10/16.
 */

public class FlightResponse implements Parcelable {
    @SerializedName("origin")
    private String origin;
    @SerializedName("currency")
    private String currency;
    @SerializedName("results")
    private List<Flight> flightsInfo;


    protected FlightResponse(Parcel in) {
        origin = in.readString();
        currency = in.readString();
    }

    public static final Creator<FlightResponse> CREATOR = new Creator<FlightResponse>() {
        @Override
        public FlightResponse createFromParcel(Parcel in) {
            return new FlightResponse(in);
        }

        @Override
        public FlightResponse[] newArray(int size) {
            return new FlightResponse[size];
        }
    };


    public List<Flight> getResults() {
        return flightsInfo;
    }

    public void setResults(List<Flight> results) {
        this.flightsInfo = results;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(origin);
        dest.writeString(currency);
        dest.writeList(flightsInfo);
    }
}