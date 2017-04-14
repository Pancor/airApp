package pl.pancor.android.air.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class OtherUtils {

    /**
     * Converts String date to String date in another format
     * @param time String date in format yyyy-MM-dd kk:mm:ss
     * @return String date in format kk:mm dd-MM-yy
     */
    public static String getConvertedDate(String time) {

        String convertedDate = null;
        SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        SimpleDateFormat endSdf = new SimpleDateFormat("kk:mm dd-MM-yy");

        try {
            Date date = startSdf.parse(time);
            convertedDate = endSdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }
}
