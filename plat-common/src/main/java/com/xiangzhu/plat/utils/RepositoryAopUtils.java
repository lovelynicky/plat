package com.xiangzhu.plat.utils;

import org.apache.ibatis.binding.MapperProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * Created by liluoqi on 2017/7/11.
 * Repository数据库操作类获取代理对象工具类
 * mybatis使用JDK动态代理，使用注解@Autowired这样操作获取的实际上是代理MapperProxy对象
 */
public class RepositoryAopUtils {
    /**
     * 获取 目标对象
     *
     * @param proxy 代理对象
     * @return 数据库操作对象的类型
     * @throws Exception
     */
    public static Object getClass(Object proxy) throws Exception {
        if (!isProxy(proxy)) {
            return proxy;//不是代理对象
        }
        return getJdkProxyTargetClass(proxy);
    }

    private static Object getJdkProxyTargetClass(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        MapperProxy mapperProxy = getMapperProxy(proxy);
        Field mapperInterface = mapperProxy.getClass().getDeclaredField("mapperInterface");
        mapperInterface.setAccessible(true);
        return mapperInterface.get(mapperProxy);
    }

    public static MapperProxy getMapperProxy(Object proxy) throws Exception{
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        return (MapperProxy) h.get(proxy);
    }

    private static boolean isProxy(Object proxy) {
        return proxy instanceof Proxy;
    }

}
