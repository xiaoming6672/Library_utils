package com.zhang.library.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Gson解析工具类
 *
 * @author ZhangXiaoMing 2020-01-03 17:42 星期五
 */
public class JsonUtils {

    public static final Gson GSON;

    static {
        GSON = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
//                .setPrettyPrinting()
                .create();
    }

    private JsonUtils() {
    }


    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static String toJson(Object object, Class<?> clazz) {
        return GSON.toJson(object, clazz);
    }


    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    public static <T> T fromJson(Gson gson, String json, Type type) {
        if (gson == null)
            return fromJson(json, type);

        return gson.fromJson(json, type);
    }


    public static <T> T fromMap(Map map, Type type) {
        if (map == null) {
            return null;
        } else {
            String json = toJson(map);
            return fromJson(json, type);
        }
    }

    public static <T> List<T> toList(String json) {
        try {
            return GSON.fromJson(json, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> Map<String, T> toMap(String gsonString) {
        return GSON.fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
    }
}
