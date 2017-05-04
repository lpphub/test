package com.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CompareUtils {

    /**
     * 对比两个对象属性的值，记录merge或confict
     *
     * @param <T>
     * @param obj1
     * @param obj2
     * @return
     */
    public static <T, K extends T> Map<String, String> compare(T obj1, K obj2) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            Field[] fields = obj1.getClass().getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                Object v1 = f.get(obj1);
                Object v2 = f.get(obj2);
                f.setAccessible(false);
                map.put(f.getName(), compareConfict(v1, v2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static <T, K extends T> boolean isConfict(T obj1, K obj2) {
        boolean flag = false;
        try {
            Field[] fields = obj1.getClass().getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                Object v1 = f.get(obj1);
                Object v2 = f.get(obj2);
                f.setAccessible(false);
                if ("confict".equals(compareConfict(v1, v2))) {
                    flag = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static <T, K extends T> boolean merge(T obj1, K obj2) {
        boolean flag = false;
        if (obj1 == null || obj2 == null) return false;
        try {
            Field[] fields = obj1.getClass().getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                Object v1 = f.get(obj1);
                Object v2 = f.get(obj2);
                if ("merge".equals(compareConfict(v1, v2))) {
                    Method m = obj1.getClass().getMethod("set" + toFirstLetterUpperCase(f.getName()), f.getType());
                    if (v1 == null && v2 != null) {
                        m.invoke(obj1, v2);
                    } else if (v2 == null && v1 != null) {
                        m.invoke(obj2, v1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static <T, K extends T> T mergeObj(T obj1, K obj2) {
        if (obj1 == null || obj2 == null) return null;
        try {
            Field[] fields = obj1.getClass().getDeclaredFields();
            T newObj = (T) obj1.getClass().newInstance();
            for (Field f : fields) {
                f.setAccessible(true);
                Object v1 = f.get(obj1);
                Object v2 = f.get(obj2);
                if ("merge".equals(compareConfict(v1, v2))) {
                    Method m = obj1.getClass().getMethod("set" + toFirstLetterUpperCase(f.getName()), f.getType());
                    if (v1 == null && v2 != null) {
//                        m.invoke(obj1, v2);
                        m.invoke(newObj, v2);
                    } else if (v2 == null && v1 != null) {
//                        m.invoke(obj2, v1);
                        m.invoke(newObj, v1);
                    }
                }
            }
            return newObj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String compareConfict(Object o1, Object o2) {
        if (o1 != null && o2 != null) {
            return "confict";
        }
        return "merge";
    }

    public static boolean equals(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            return true;
        }
        if (obj1 == null || obj2 == null) {
            return false;
        }
        return obj1.equals(obj2);
    }

    public static String toFirstLetterUpperCase(String str) {
        if (str == null || str.length() < 2) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
    }

}
