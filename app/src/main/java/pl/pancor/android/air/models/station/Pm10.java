
package pl.pancor.android.air.models.station;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pm10 {

    @SerializedName("v")
    @Expose
    private Double v;

    public Double getV() {
        return v;
    }

    public void setV(Double v) {
        this.v = v;
    }

}
