package com.test;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by linshaokang on 15/7/20.
 */
public class TestJava {
    public static void main(String[] args) throws ParseException {
        System.out.println("测试");
        List<String> list = new ArrayList<String>();
        list.add("测试01");
        list.add("测试02");
        list.add("测试03");

        String[] arr = new String[]{"测试04", "测试05", "测试06"};

        System.out.println(list);
        System.out.println(Arrays.toString(arr));

        String time = "2015-07-30 16:12:56.2";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.parse(time));

    }

    @Test
    public void test() {
        System.out.println("测试程序……");
        try {

//            new ErrorTest().test();
        } catch (TestException e) {
            System.out.println("e:" + e.getMessage());
        }
    }
}