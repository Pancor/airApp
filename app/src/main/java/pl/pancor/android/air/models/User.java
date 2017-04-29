package pl.pancor.android.air.models;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @PrimaryKey
    private int id;

    @Nullable
    private Double latitude;

    @Nullable
    private Double longitude;

    public User(){}

    /**
     * Object, taht contains information about user
     * @param id        unique user id
     * @param latitude  latitude of user location, when user last time requested
     *                  to get nearest station
     * @param longitude longitude of user location, when user last time requested
     *                  to get nearest station
     */
    public User(int id, @NonNull Double latitude, @NonNull Double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(@Nullable Double latitude) {
        this.latitude = latitude;
    }

    @Nullable
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(@Nullable Double longitude) {
        this.longitude = longitude;
    }
}
