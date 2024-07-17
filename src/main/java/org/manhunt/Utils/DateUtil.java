package org.manhunt.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
        return simpleDateFormat.format(new Date());
    }
}
