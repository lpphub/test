package com.demo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeDemo {
    public static void main(String[] args) {
        System.out.println(Instant.now());
        System.out.println(new Date());
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        int index = 0;
        do {
            System.out.println("测试:" + index++);
        } while (index < 3);
    }
}
