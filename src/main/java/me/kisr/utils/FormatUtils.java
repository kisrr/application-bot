package me.kisr.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public enum FormatUtils {
    ;

    public static String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy KK:mm:ss");
        return (dateFormat.format(new Date()));
    }
}
