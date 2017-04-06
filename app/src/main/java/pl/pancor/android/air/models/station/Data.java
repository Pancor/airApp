
package pl.pancor.android.air.models.station;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("aqi")
    @Expose
    private Double aqi;
    @SerializedName("idx")
    @Expose
    private Double idx;
    @SerializedName("attributions")
    @Expose
    private List<Attribution> attributions = null;
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("dominentpol")
    @Expose
    private String dominentpol;
    @SerializedName("iaqi")
    @Expose
    private Iaqi iaqi;
    @SerializedName("time")
    @Expose
    private Time time;

    public Double getAqi() {
        return aqi;
    }

    public void setAqi(Double aqi) {
        this.aqi = aqi;
    }

    public Double getIdx() {
        return idx;
    }

    public void setIdx(Double idx) {
        this.idx = idx;
    }

    public List<Attribution> getAttributions() {
        return attributions;
    }

    public void setAttributions(List<Attribution> attributions) {
        this.attributions = attributions;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getDominentpol() {
        return dominentpol;
    }

    public void setDominentpol(String dominentpol) {
        this.dominentpol = dominentpol;
    }

    public Iaqi getIaqi() {
        return iaqi;
    }

    public void setIaqi(Iaqi iaqi) {
        this.iaqi = iaqi;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
