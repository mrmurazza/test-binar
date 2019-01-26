package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {
    public static String formatToPrettyDate(Date date) {
        TimeZone jakartaTimeZone = TimeZone.getTimeZone("Asia/Jakarta");
        DateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSSS");
        formatter.setTimeZone(jakartaTimeZone);
        return formatter.format(date);
    }
}
