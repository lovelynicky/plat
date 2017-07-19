package com.xiangzhu.plat.utils;


import java.lang.reflect.*;

/**
 * Created by liluoqi on 2017/7/11.
 * 反射工具类
 */
public class ReflectUtils {
    /**
     * 根据方法名获取方法
     *
     * @return
     */
    public static <T> Method getMethodByName(String methodName, Class<T> clazz) throws NoSuchMethodException {
        Method[] methods =  clazz.getDeclaredMethods();
        for(Method method:methods){
            if(method.getName().equals(methodName)){
                return method;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> Object executeMethod(String methodName, T instance, Object... params) throws Throwable {
        return RepositoryAopUtils.getMapperProxy(instance).invoke(instance,
                getMethodByName(methodName, (Class<T>) RepositoryAopUtils.getClass(instance)),params);
    }
}
