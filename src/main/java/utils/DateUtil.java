package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Magda Sielewicz
 */
public class DateUtil {

    public static String getCurrentDateAndTime(String format) {
        Calendar calendar = Calendar.getInstance();
        return getDateText(calendar.getTime(), format);
    }

    public static String getDateText(Date dateToFormat, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(dateToFormat);
        return date;
    }

}
