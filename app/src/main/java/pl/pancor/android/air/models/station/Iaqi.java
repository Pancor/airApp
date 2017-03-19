
package pl.pancor.android.air.models.station;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pl.pancor.android.air.R;

public class Iaqi {

    @SerializedName("co")
    @Expose
    private Co co;
    @SerializedName("d")
    @Expose
    private D d;
    @SerializedName("h")
    @Expose
    private H h;
    @SerializedName("no2")
    @Expose
    private No2 no2;
    @SerializedName("o3")
    @Expose
    private O3 o3;
    @SerializedName("p")
    @Expose
    private P p;
    @SerializedName("pm10")
    @Expose
    private Pm10 pm10;
    @SerializedName("pm25")
    @Expose
    private Pm25 pm25;
    @SerializedName("so2")
    @Expose
    private So2 so2;
    @SerializedName("t")
    @Expose
    private T t;
    @SerializedName("w")
    @Expose
    private W w;
    @SerializedName("wd")
    @Expose
    private Wd wd;

    private ArrayList<Polluter> polluters = new ArrayList<>();

    public Co getCo() {
        return co;
    }

    public void setCo(Co co) {
        this.co = co;
    }

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }

    public H getH() {
        return h;
    }

    public void setH(H h) {
        this.h = h;
    }

    public No2 getNo2() {
        return no2;
    }

    public void setNo2(No2 no2) {
        this.no2 = no2;
    }

    public O3 getO3() {
        return o3;
    }

    public void setO3(O3 o3) {
        this.o3 = o3;
    }

    public P getP() {
        return p;
    }

    public void setP(P p) {
        this.p = p;
    }

    public Pm10 getPm10() {
        return pm10;
    }

    public void setPm10(Pm10 pm10) {
        this.pm10 = pm10;
    }

    public Pm25 getPm25() {
        return pm25;
    }

    public void setPm25(Pm25 pm25) {
        this.pm25 = pm25;
    }

    public So2 getSo2() {
        return so2;
    }

    public void setSo2(So2 so2) {
        this.so2 = so2;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public W getW() {
        return w;
    }

    public void setW(W w) {
        this.w = w;
    }

    public Wd getWd() {
        return wd;
    }

    public void setWd(Wd wd) {
        this.wd = wd;
    }


    /**
     * some ugly stuff, i don't know, how to better handle this POJO
     * @return ArrayList of Polluters
     */
    public ArrayList<Polluter> getPolluters(){

        if (polluters.size() <= 0) {
            if (co != null)
                polluters.add(new Polluter(R.string.CO, co.getV(), 10000));
            if (no2 != null)
                polluters.add(new Polluter(R.string.NO2, no2.getV(), 200));
            if (o3 != null)
                polluters.add(new Polluter(R.string.O3, o3.getV(), 120));
            if (pm10 != null)
                polluters.add(new Polluter(R.string.PM10, pm10.getV(), 50));
            if (pm25 != null)
                polluters.add(new Polluter(R.string.PM2_5, pm25.getV(), 25));
            if (so2 != null)
                polluters.add(new Polluter(R.string.SO2, so2.getV(), 350));

/*  TODO: is it temperature, pressure and what...?
            if (t != null)
                polluters.add(new Polluter("T", t.getV(), 0));
            if (p != null)
                polluters.add(new Polluter("P", p.getV(), 0));
            if (d != null)
                polluters.add(new Polluter("D", d.getV()));
            if (h != null)
                polluters.add(new Polluter("H", h.getV()));
            if (w != null)
                polluters.add(new Polluter("W", w.getV()));
            if (wd != null)
                polluters.add(new Polluter("WD", wd.getV()));
*/
        }
        return polluters;
    }
}
