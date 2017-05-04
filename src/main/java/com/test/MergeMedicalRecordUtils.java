package com.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class MergeMedicalRecordUtils {

    public static <T, K extends T> T merge(T obj1, K obj2) {
        if (obj1 == null || obj2 == null) return null;
        try {
            Field[] fields = obj1.getClass().getDeclaredFields();
            T newObj = (T) obj1.getClass().newInstance();
            for (Field f : fields) {
                f.setAccessible(true);
                Object v1 = f.get(obj1);
                Object v2 = f.get(obj2);
                if (isExclude(f.getName()) || !isConfictByValue(v1, v2)) {
                    Method m = obj1.getClass().getMethod("set" + toFirstLetterUpperCase(f.getName()), f.getType());
                    if (v1 == null && v2 != null) {
                        m.invoke(newObj, v2);
                    } else {
                        m.invoke(newObj, v1);
                    }
                } else {
                    //解决冲突字段的合并

                }
            }
            return newObj;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    public static <T, K extends T> boolean isConfict(T obj1, K obj2) {
        boolean flag = false;
        try {
            Field[] fields = obj1.getClass().getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                Object v1 = f.get(obj1);
                Object v2 = f.get(obj2);
                if (!isExclude(f.getName()) && isConfictByValue(v1, v2)) {
                    flag = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return flag;
    }

    private static boolean isExclude(String fieldName){
        Set<String> excludeFields = new HashSet<String>();
        excludeFields.add("name");
        excludeFields.add("Ver");
        excludeFields.add("UploadKey");
        excludeFields.add("ServerUpdateTime");
        if (excludeFields.contains(fieldName)) return true;
        return false;
    }

    private static boolean isConfictByValue(Object o1, Object o2) {
        if (o1 != null && o2 != null && !equals(o1, o2)) {
            return true;
        }
        return false;
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

    private static String toFirstLetterUpperCase(String str) {
        if (str == null || str.length() < 2) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
    }
}
