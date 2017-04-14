package pl.pancor.android.air.models;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class Station {

    @SerializedName("id_station")
    private final int id;

    @NonNull @SerializedName("station_name")
    private final String name;

    @SerializedName("aq_index")
    private final int aqIndex;

    @NonNull @SerializedName("update_time")
    private final String updateTime;

    @NonNull @SerializedName("latitude")
    private final Double latitude;

    @NonNull @SerializedName("longitude")
    private final Double longitude;

    @Nullable @SerializedName("polluters")
    private final List<Polluter> polluters;

    /**
     * Use this to create new Station object.
     * @param id         id of the station
     * @param name       name of the station (also it's an address of the station)
     * @param aqIndex    it's air quality index, int from -1 to 5, where -1 is when no data is available
     * @param updateTime time of last update of air quality
     * @param latitude   latitude of the station
     * @param longitude  longitude of the station
     * @param polluters  List of the {@link Polluter} object
     */
    public Station(int id, @NonNull String name, @NonNull Double latitude, int aqIndex,
                   @NonNull String updateTime, @NonNull Double longitude, @Nullable List<Polluter> polluters){

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
    public List<Polluter> getPolluters() {
        return polluters;
    }

    @Override
    public String toString() {
        return "Station " + name;
    }
}
