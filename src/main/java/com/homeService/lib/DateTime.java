package com.homeService.lib;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateTime {
    public DateTime() {}

    public String getCurrentDate() {
        Date date = new Date();

        return "";
    }

    public String millisToString(long millis) {
        Date date = new Date(millis);
        return format(date);
    }
    public String format(Date date) {
        return new SimpleDateFormat("[yyyy.MM.dd HH:mm:ss.S]").format(date);
    }
}
