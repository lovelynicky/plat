package com.xiangzhu.plat.utils;

import java.security.Key;
import java.util.*;

/**
 * Created by liluoqi on 15/4/22.
 */
public class MapUtils {

    public enum SortType {
        ASC,
        DESC
    }

    public static Map<Object, Object> convertToMap(Object... objects) {
        try {
            Map<Object, Object> map = new HashMap<Object, Object>();
            if (objects.length % 2 != 0 || objects.length == 0) {
                return map;
            }
            for (int index = 0; index < objects.length - 1; index = index + 2) {
                map.put(objects[index], objects[index + 1]);
            }
            return map;
        } catch (Exception e) {
            return new HashMap<Object, Object>();
        }
    }

    public static Map<String, Object> convertStringObjectMap(Object... objects) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            if (objects.length % 2 != 0 || objects.length == 0) {
                return map;
            }
            for (int index = 0; index < objects.length - 1; index = index + 2) {
                map.put(String.valueOf(objects[index]), objects[index + 1]);
            }
            return map;
        } catch (Exception e) {
            return new HashMap<String, Object>();
        }
    }

    public static Map<String, Object> convertToStringKeyMap(Object... objects) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            if (objects.length % 2 != 0 || objects.length == 0) {
                return map;
            }
            for (int index = 0; index < objects.length - 1; index = index + 2) {
                map.put(String.valueOf(objects[index]), objects[index + 1]);
            }
            return map;
        } catch (Exception e) {
            return new HashMap<String, Object>();
        }
    }


    public static Map<String, String> convertStringToMap(String... objects) {
        try {
            Map<String, String> map = new HashMap<String, String>();
            if (objects.length % 2 != 0 || objects.length == 0) {
                return map;
            }
            for (int index = 0; index < objects.length - 1; index = index + 2) {
                map.put(objects[index], objects[index + 1]);
            }
            return map;
        } catch (Exception e) {
            return new HashMap<String, String>();
        }
    }

    public static Map<String, String> convertStringToLinkedMap(String... objects) {
        try {
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
            if (objects.length % 2 != 0 || objects.length == 0) {
                return map;
            }
            for (int index = 0; index < objects.length - 1; index = index + 2) {
                map.put(objects[index], objects[index + 1]);
            }
            return map;
        } catch (Exception e) {
            return new LinkedHashMap<String, String>();
        }
    }

    public static ArrayList<String> getSortedKeyString(Map<String, String> map, SortType sortType) {
        ArrayList<String> arrayList = new ArrayList<String>();
        Set<String> keySet = map.keySet();
        if (sortType == null || SortType.ASC.equals(sortType)) {
            arrayList.addAll(keySet);
            Collections.sort(arrayList, new Comparator<String>() {
                @Override
                public int compare(String before, String after) {
                    return before.compareTo(after) >= 0 ? 1 : -1;
                }
            });
        } else {
            arrayList.addAll(keySet);
            Collections.sort(arrayList, new Comparator<String>() {
                @Override
                public int compare(String before, String after) {
                    return before.compareTo(after) <= 0 ? 1 : -1;
                }
            });
        }
        return arrayList;
    }
}
