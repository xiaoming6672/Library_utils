package com.zhang.library.utils.utils;

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
        GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    private JsonUtils() {
    }

    public static String objectToJson(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T jsonToObject(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    public static <T> T mapToObject(Map map, Type type) {
        if (map == null) {
            return null;
        } else {
            String json = objectToJson(map);
            return jsonToObject(json, type);
        }
    }

    public static <T> List<T> jsonToList(String json) {
        try {
            return GSON.fromJson(json, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> Map<String, T> GsonToMaps(String gsonString) {
        return GSON.fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
    }
}
