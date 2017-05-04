package com.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeTest {

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.parse("2015-11-04 15:55:57", formatter);
        System.out.println(now.minusHours(1).format(formatter));

        String t = formatter.format(LocalDateTime.now());
        System.out.println(t);
    }
}
