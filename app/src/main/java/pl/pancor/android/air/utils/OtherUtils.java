package pl.pancor.android.air.utils;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    /**
     * Checks if given {@param time} is more than hour
     * @param time String date in format yyyy-MM-dd kk:mm:ss
     * @return true if given {@param time} is less than hour
     */
    public static boolean isTimeUpToDate(String time){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        long hour = 1000 * 60 * 60;

        Calendar c = Calendar.getInstance();
        Date currentTime = c.getTime();

        try {
            Date stationTime = sdf.parse(time);
            long timeDifference = currentTime.getTime() - stationTime.getTime();
            return timeDifference <= hour;

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gives all digits subscript
     * @param text String with digits to convert
     * @return     Spanned with subscript digits
     */
    @SuppressWarnings("deprecation")
    public static Spanned getSpannedWithSubscriptNumbers(String text){

        String subscript = "";
        for (int i = 0; i < text.length(); i++) {

            char c = text.charAt(i);

            if (Character.isDigit(c) || String.valueOf(c).equals("."))
                subscript += "<small><sub>" + c + "</sub></small>";
             else
                subscript += c;
        }

        Spanned spanned;
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            spanned = Html.fromHtml(subscript, Html.FROM_HTML_MODE_LEGACY);
        else
            spanned = Html.fromHtml(subscript);

        return spanned;
    }
}
