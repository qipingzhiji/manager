package com.weiqiaoshiyan.student.manager.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by zhang_htao on 2019/8/1.
 */
public class ParseTimeUtils {

    public  static final  String FORMATTER = "yyyy-MM-dd HH:mm:ss";

    private ParseTimeUtils(){}

    public  static long timeMillis(String timeStr,String formatter){
        LocalDateTime parse = LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern(formatter));
        ZonedDateTime zdt = parse.atZone(ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }
}
