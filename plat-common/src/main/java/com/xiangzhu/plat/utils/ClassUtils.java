package com.xiangzhu.plat.utils;

/**
 * Created by liluoqi on 2017/7/16.
 * Class 工具类
 */
public class ClassUtils {
    /**
     * 根据名字获取class
     * eg:com.xiangzhu.plat.domain.schedule.TestJob
     * @return Class
     */
    public static Class getClassByName(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }
}
