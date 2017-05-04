package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by linshaokang on 15/7/29.
 */
public class ConfigUtils {
    public static final String CONFIG_FILE = "/Data.conf";
    private static Properties props = null;
    static {
        props = new Properties();
        InputStream in = null;
        try {
            in = ConfigUtils.class.getResourceAsStream(CONFIG_FILE);
            props.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException var14) {
                var14.printStackTrace();
            }

        }
    }

    public static final String readValue(String key) {
        return props.getProperty(key);
    }

    public static void main(String[] args) {
        System.out.println(ConfigUtils.readValue("app.type.code"));
    }
}
