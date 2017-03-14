
package pl.pancor.android.air.models.station;

import android.util.Log;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pl.pancor.android.air.utils.location.Location;

public class City {

    @SerializedName("geo")
    @Expose
    private List<Double> geo = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;

    private double[] userGeo = null;

    public List<Double> getGeo() {
        return geo;
    }

    public void setGeo(List<Double> geo) {
        this.geo = geo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserGeo(double latitude, double longitude){

        userGeo = new double[]{latitude, longitude};
    }

    /**
     * @return distance between user location and station location in km
     */
    public double getDistanceToStation(){

        float[] distance = new float[2];

        android.location.Location.distanceBetween(userGeo[0], userGeo[1],
                geo.get(0), geo.get(1), distance);

        return Math.round(distance[0] / 10) / 100;
    }

}
