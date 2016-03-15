package kbc.cognizant.com.kbc1.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cts_mobility5 on 3/15/16.
 */
public class Utils {

    public static String getCurrentDate(){
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date date = new Date(currentTimeMillis);
        return sdf.format(date).toString();
    }

}
