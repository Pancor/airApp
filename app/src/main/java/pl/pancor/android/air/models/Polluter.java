package pl.pancor.android.air.models;


import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Polluter extends RealmObject {

    @NonNull @SerializedName("name")
    private String name;

    @NonNull @SerializedName("value")
    private Double value;

    @NonNull @SerializedName("alert_value")
    private Double alertValue;

    public Polluter(){}

    /**
     * Use this to create Polluter by given name and value.
     * @param name  name of polluter
     * @param value value of polluter
     */
    public Polluter(@NonNull String name, @NonNull Double value, @NonNull Double alertValue){

        this.name = name;
        this.value = value;
        this.alertValue = alertValue;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public Double getValue() {
        return value;
    }

    @NonNull
    public Double getAlertValue() {
        return alertValue;
    }

    @Override
    public String toString() {
        return "Polluter " + name;
    }
}
