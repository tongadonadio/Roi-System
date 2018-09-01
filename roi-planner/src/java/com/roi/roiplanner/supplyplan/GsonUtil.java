package com.roi.roiplanner.supplyplan;

import com.google.gson.Gson;

public class GsonUtil {
    public static <T> String toJson(T object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
    
    public static <T> T fromJson(String json, Class<T> objectClass) {
        Gson gson = new Gson();
        return gson.fromJson(json, objectClass);
    }
}
