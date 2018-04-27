package com.yjy.test.game.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.Assert;

public class MyBeanUtils {
    /**
     * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数.
     */
    public static Object getFieldValue(final Object object,
                                       final String fieldName) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + object + "]");
        }

        makeAccessible(field);

        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("never happend exception!", e);
        }
        return result;
    }

    /**
     * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
     */
    public static void setFieldValue(final Object object,
                                     final String fieldName, final Object value) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + object + "]");
        }

        makeAccessible(field);

        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("never happend exception!", e);
        }
    }

    /**
     * 循环向上转型,获取对象的DeclaredField.
     */
    protected static Field getDeclaredField(final Object object,
                                            final String fieldName) {
        Assert.notNull(object);
        return getDeclaredField(object.getClass(), fieldName);
    }

    /**
     * 循环向上转型,获取类的DeclaredField.
     */
    @SuppressWarnings("rawtypes")
    protected static Field getDeclaredField(final Class clazz,
                                            final String fieldName) {
        Assert.notNull(clazz);
        Assert.hasText(fieldName);
        for (Class superClass = clazz; superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 强制转换fileld可访问.
     */
    protected static void makeAccessible(final Field field) {
        if (!Modifier.isPublic(field.getModifiers())
                || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    public static Object getSimpleProperty(Object bean, String propName)
            throws IllegalArgumentException, SecurityException,
            IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        return bean.getClass().getMethod(getReadMethod(propName)).invoke(bean);
    }

    private static String getReadMethod(String name) {
        return "get" + name.substring(0, 1).toUpperCase(Locale.ENGLISH)
                + name.substring(1);
    }

    /**
     * 获得同时有get和set的field和value。
     *
     * @param bean
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Map describe(Object bean) {
        Map des = new HashMap();
        PropertyDescriptor desor[] = PropertyUtils.getPropertyDescriptors(bean);
        String name = null;
        for (int i = 0; i < desor.length; i++) {
            if (desor[i].getReadMethod() != null
                    && desor[i].getWriteMethod() != null) {
                name = desor[i].getName();
                try {
                    des.put(name, PropertyUtils.getProperty(bean, name));
                } catch (Exception e) {
                    throw new RuntimeException("属性不存在：" + name);
                }
            }
        }
        return des;
    }
}
