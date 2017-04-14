package pl.pancor.android.air.models;


import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public final class Polluter {

    @NonNull @SerializedName("name")
    private final String name;

    @NonNull @SerializedName("value")
    private final Double value;

    /**
     * Use this to create Polluter by given name and value.
     * @param name  name of polluter
     * @param value value of polluter
     */
    public Polluter(@NonNull String name, @NonNull Double value){

        this.name = name;
        this.value = value;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Polluter " + name;
    }
}
