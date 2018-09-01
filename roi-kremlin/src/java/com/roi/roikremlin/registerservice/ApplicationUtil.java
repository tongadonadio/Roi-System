package com.roi.roikremlin.registerservice;

import java.util.List;
import java.util.Map;

public class ApplicationUtil {
    
    public static <K,V> Boolean isNotNullAndEmpty(Map<K,V> map) {
        return map != null && !map.isEmpty();
    }

    public static Boolean isNotNullAndEmpty(String value) {
        return value != null && !value.isEmpty();
    }
    
    public static <T> Boolean isNotNullAndEmpty(T[] values) {
        return values != null && values.length > 0;
    }
    
    public static <T> Boolean isNotNullAndEmpty(List<T> values) {
        return values != null && !values.isEmpty();
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
    
    public static String removeFromString(String original, String substring) {
       String result = original.substring(substring.length());
       return result != null ? result.trim() : result;
    }
}
