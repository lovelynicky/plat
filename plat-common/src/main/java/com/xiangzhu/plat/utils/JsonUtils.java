package com.xiangzhu.plat.utils;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liluoqi on 2017/7/15.
 * json工具类 使用gson
 */
public class JsonUtils {

    private GsonBuilder gsonBuilder;

    private static JsonUtils jsonUtils = new JsonUtils();

    private JsonUtils() {
        this.gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    private static JsonUtils instance() {
        if (jsonUtils == null) {
            jsonUtils = new JsonUtils();
        }
        return jsonUtils;
    }

    public static JsonUtils excludeFieldsWithoutExposeAnnotation() {
        return instance().withGsonBuilder(instance().getGsonBuilder().excludeFieldsWithoutExposeAnnotation());
    }

    public static JsonUtils serializeNulls() {
        return instance().withGsonBuilder(instance().getGsonBuilder().serializeNulls());
    }

    public static String toJson(Object object) {
        return instance().getGsonBuilder().create().toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return instance().getGsonBuilder().create().fromJson(json, clazz);
    }

    /**
     * 反序列化map对象，注意当T或者K为java bean时 必须先对T或K进行json序列化
     * 在获得返序列化的map后在对T或者K进行反序列化
     *
     * @param jsonMap map json字符串
     * @param <T> key
     * @param <K> value
     * @return map
     */
    public static <T, K> Map<T, K> fromJsonMap(String jsonMap) {
        return instance().getGsonBuilder().create().fromJson(jsonMap, new TypeToken<Map<T, K>>() {
        }.getType());
    }

    public static <T> List<T> fromListJson(String jsonArray) {
        return instance().getGsonBuilder().create().fromJson(jsonArray, new TypeToken<List<T>>() {
        }.getType());
    }

    public GsonBuilder getGsonBuilder() {
        return gsonBuilder;
    }

    public JsonUtils withGsonBuilder(GsonBuilder gsonBuilder) {
        this.gsonBuilder = gsonBuilder;
        return this;
    }

    static class Test {
        private String value;

        public Test(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        Map<String, Test> map = new HashMap<>();
        map.put("1", new Test("1"));
        map.put("2", new Test("2"));
        String json = JsonUtils.toJson(map);
        Map<String, Test> mapJson = JsonUtils.fromJsonMap(json);
        for (String key : mapJson.keySet()) {
            System.out.println(mapJson.get(key).getValue());
        }
    }
}
