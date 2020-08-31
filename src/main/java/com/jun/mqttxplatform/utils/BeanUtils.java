package com.jun.mqttxplatform.utils;

import com.jun.mqttxplatform.exception.BeanException;
import org.springframework.util.Assert;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * javabean -> map 工具类
 *
 * @author wujun
 * @date 2018/7/24 12:02
 */
public class BeanUtils {

    /**
     * 将javabean 转换为 map
     *
     * @param target 待转换对象
     * @return
     */
    public static <U> Map<String, Object> bean2map(U target) {

        Map<String, Object> map = new HashMap<>();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());

            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (Objects.equals("class", propertyDescriptor.getName())) {
                    continue;
                }

                String key = propertyDescriptor.getName();
                Object value = propertyDescriptor.getReadMethod().invoke(target);

                if (value != null) {
                    map.put(key, value);
                }
            }
        } catch (IntrospectionException |
                IllegalAccessException |
                InvocationTargetException e) {
            throw new BeanException(e.getMessage(), e);
        }

        return map;
    }

    /**
     * map 转 javabean
     *
     * @param map   待装换key-value Map
     * @param clazz 目标类型
     * @return T实例
     */
    public static <R> R map2bean(Map<String, Object> map, Class<R> clazz) {
        Assert.notEmpty(map, "待转换map不能为空");

        try {
            R r = clazz.newInstance();

            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                if (Objects.equals("class", propertyDescriptor.getName())) {
                    continue;
                }

                String key = propertyDescriptor.getName();

                if (map.containsKey(key)) {
                    Object val = map.get(key);

                    //为了处理基础类型的装换关系，所以加上了类型判别
                    if (propertyDescriptor.getPropertyType().isAssignableFrom(Byte.class) && val instanceof Integer) {
                        propertyDescriptor.getWriteMethod().invoke(r, ((Integer) val).byteValue());
                    } else if (propertyDescriptor.getPropertyType().isAssignableFrom(Long.class) && val instanceof Integer) {
                        propertyDescriptor.getWriteMethod().invoke(r, ((Integer) val).longValue());
                    } else if (propertyDescriptor.getPropertyType().isAssignableFrom(Float.class) && val instanceof Double) {
                        propertyDescriptor.getWriteMethod().invoke(r, ((Double) val).floatValue());
                    } else {
                        propertyDescriptor.getWriteMethod().invoke(r, val);
                    }
                }
            }

            return r;
        } catch (IntrospectionException |
                IllegalAccessException |
                InstantiationException |
                InvocationTargetException e) {
            throw new BeanException(e.getMessage(), e);
        }
    }

    /**
     * javabean之间属性复制
     *
     * @param source     数据源bean
     * @param clazz 目标类型
     * @return 属性复制后的对象
     */
    public static <R, U> R bean2bean(U source, Class<R> clazz) {
        Map<String, Object> objectMap = bean2map(source);

        return map2bean(objectMap, clazz);
    }
}
