package com.xiangzhu.plat.utils;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
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
        return instance().getGsonBuilder().registerTypeAdapter(new TypeToken<Map<String, Object>>() {
        }.getType(), new MapTypeAdapter()).create().fromJson(jsonMap, new TypeToken<Map<String, Object>>() {
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
        Map<String, Object> map = new HashMap<>();
        map.put("amount", 10);
        String json = JsonUtils.toJson(map);
        Map<String, Object> mapJson = JsonUtils.fromJsonMap(json);
        for (String key : mapJson.keySet()) {
            System.out.println(mapJson.get(key));
        }
    }

    static class MapTypeAdapter extends TypeAdapter<Object> {
        @Override
        public Object read(JsonReader in) throws IOException {
            JsonToken token = in.peek();
            switch (token) {
                case BEGIN_ARRAY:
                    List<Object> list = new ArrayList<Object>();
                    in.beginArray();
                    while (in.hasNext()) {
                        list.add(read(in));
                    }
                    in.endArray();
                    return list;

                case BEGIN_OBJECT:
                    Map<String, Object> map = new LinkedTreeMap<String, Object>();
                    in.beginObject();
                    while (in.hasNext()) {
                        map.put(in.nextName(), read(in));
                    }
                    in.endObject();
                    return map;

                case STRING:
                    return in.nextString();

                case NUMBER:
                    /*
                     * 改写数字的处理逻辑，将数字值分为整型与浮点型。
                     */
                    double dbNum = in.nextDouble();

                    // 数字超过long的最大值，返回浮点类型
                    if (dbNum > Long.MAX_VALUE) {
                        return dbNum;
                    }

                    // 判断数字是否为整数值
                    long lngNum = (long) dbNum;
                    if (dbNum == lngNum) {
                        return lngNum;
                    } else {
                        return dbNum;
                    }

                case BOOLEAN:
                    return in.nextBoolean();

                case NULL:
                    in.nextNull();
                    return null;

                default:
                    throw new IllegalStateException();
            }
        }

        @Override
        public void write(JsonWriter out, Object value) throws IOException {
            // 序列化无需实现
        }
    }
}
