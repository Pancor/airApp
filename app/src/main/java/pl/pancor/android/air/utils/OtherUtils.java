package pl.pancor.android.air.utils;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class OtherUtils {

    /**
     * Counts difference between user time zone and weather station time zone, then this difference
     * is added (or deduct) to weather station time. It's done to show user time zone date,
     * not weather station date.
     * @param timeZone weather station time zone in format +00:00
     * @param time weather station date in seconds
     * @return String date in format kk:mm dd-MM-yy
     */
    public static String getConvertedDate(String timeZone, long time) {

        String convertedDate;
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm dd-MM-yy");

        int stationTimeZone = TimeZone.getTimeZone("GMT" + timeZone).getRawOffset();
        int currentTimeZone = TimeZone.getDefault().getRawOffset();
        int difference;

        time *= 1000;

        if (stationTimeZone > currentTimeZone) {

            difference = stationTimeZone - currentTimeZone;
            convertedDate = sdf.format(time - difference);
        } else {

            difference = currentTimeZone - stationTimeZone;
            convertedDate = sdf.format(time + difference);
        }
        return convertedDate;
    }
}
