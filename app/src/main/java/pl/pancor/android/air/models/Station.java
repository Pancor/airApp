package pl.pancor.android.air.models;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Station extends RealmObject {

    @PrimaryKey
    @SerializedName("id_station")
    private int id;

    @NonNull @SerializedName("station_name")
    private String name;

    @SerializedName("aq_index")
    private int aqIndex;

    @NonNull @SerializedName("update_time")
    private String updateTime;

    @NonNull @SerializedName("latitude")
    private Double latitude;

    @NonNull @SerializedName("longitude")
    private Double longitude;

    /**
     * Distance between user position and station position given in kilometers
     */
    @Nullable
    private Double distance;

    @Nullable @SerializedName("polluters")
    private RealmList<Polluter> polluters;

    public Station(){}

    /**
     * Use this to create new Station object.
     * @param id         id of the station
     * @param name       name of the station (also it's an address of the station)
     * @param aqIndex    it's air quality index, int from -1 to 5, where -1 is when no data is available
     * @param updateTime time of last update of air quality in format yyyy-MM-dd kk:mm:ss
     * @param latitude   latitude of the station
     * @param longitude  longitude of the station
     * @param polluters  List of the {@link Polluter} object
     */
    public Station(int id, @NonNull String name, @NonNull Double latitude, int aqIndex,
                   @NonNull String updateTime, @NonNull Double longitude,
                   @Nullable RealmList<Polluter> polluters){

        this.id = id;
        this.name = name;
        this.aqIndex = aqIndex;
        this.updateTime = updateTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.polluters = polluters;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public int getAqIndex() {
        return aqIndex;
    }

    @NonNull
    public String getUpdateTime() {
        return updateTime;
    }

    @NonNull
    public Double getLatitude() {
        return latitude;
    }

    @NonNull
    public Double getLongitude() {
        return longitude;
    }

    @Nullable
    public RealmList<Polluter> getPolluters() {
        return polluters;
    }

    @Override
    public String toString() {
        return "Station " + name;
    }

    @Nullable
    public Double getDistance() {
        return distance;
    }

    public void setDistance(@Nullable Double distance) {
        this.distance = distance;
    }
}
