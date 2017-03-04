
package pl.pancor.android.air.models.station;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Time {

    @SerializedName("s")
    @Expose
    private String s;
    @SerializedName("tz")
    @Expose
    private String tz;
    @SerializedName("v")
    @Expose
    private long v;

    private String convertedDate;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public long getV() {
        return v;
    }

    public void setV(long v) {
        this.v = v;
    }

    public String getConvertedDate(){

        if (convertedDate != null){

            return convertedDate;
        } else {

            SimpleDateFormat sdf = new SimpleDateFormat("kk:mm dd-MM-yy");
            convertedDate = sdf.format(v * 1000);

            return convertedDate;
        }
    }
}
