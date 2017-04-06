package pl.pancor.android.air.utils.location;

public class LocationUtils {

    /**
     * Counts distance between two positions
     * @return distance in kilometers, if it return -1.0, then an error occurred
     */
    public static Double getDistance(Double lat1, Double lng1, Double lat2, Double lng2){

        float[] results = new float[2];

        android.location.Location.distanceBetween(lat1, lng1, lat2, lng2, results);

        return (Math.round(results[0] / 100.0) / 10.0);
    }
}
