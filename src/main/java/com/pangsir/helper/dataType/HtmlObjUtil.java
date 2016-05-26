package com.pangsir.helper.dataType;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 类说明: HTML的转码操作
 *
 * @Author: 胖先生
 * @Create: 2016-05-25 19:53
 * @Home: http://www.cnblogs.com/pangxiansheng/
 */

public class HtmlObjUtil {

    private static final Logger log = LoggerFactory.getLogger(HtmlObjUtil.class);

    /**
     * 将页面提交的数据字符串进行替换,防止出现页面混乱
     *
     * @param param
     * @return
     * @throws IllegalAccessException
     */
    public static Object replaceStringHtml(Object param) throws IllegalAccessException {
        if (param != null) {

            if (JavaTypeUtils.isBasicType(param)) {

                if (param.getClass().equals(JavaBasicTypeEnum.STRING.getsClass())) {
                    return StringEscapeUtils.escapeHtml4(param.toString());
                }

                return param;
            }

            if (JavaTypeUtils.isArray(param)) {
                Object[] objectArray = (Object[]) param;
                for (int i = 0; i < objectArray.length; i++) {
                    Object object = objectArray[i];
                    if (object == null) {
                        continue;
                    }
                    objectArray[i] = replaceStringHtml(object);
                }

                return objectArray;
            }

            if (JavaTypeUtils.isCollection(param)) {
                Collection collection = (Collection) param;

                Collection replaceCollection = new ArrayList();

                Iterator iterator = collection.iterator();
                while (iterator.hasNext()) {
                    Object nextObj = iterator.next();
                    if (nextObj == null) {
                        continue;
                    }
                    Object o = replaceStringHtml(nextObj);
                    iterator.remove();
                    replaceCollection.add(o);
                }

                collection.addAll(replaceCollection);

                return collection;
            }

            if (JavaTypeUtils.isMap(param)) {
                Map map = (Map) param;
                Set set = map.keySet();
                for (Object obj : set) {
                    Object mapValue = map.get(obj);
                    if (mapValue == null) {
                        continue;
                    }
                    Object o = replaceStringHtml(mapValue);
                    map.put(obj, o);
                }

                return map;
            }

            Field[] declaredFields = param.getClass().getDeclaredFields();
            for (Field field : declaredFields) {

                field.setAccessible(true);
                int modifiers = field.getModifiers();
                if (modifiers >= 24) {
                    continue;
                }

                Object o = field.get(param);

                if (o == null) {
                    continue;
                }

                Object replaceObj = replaceStringHtml(o);

                try {
                    BeanUtils.setProperty(param, field.getName(), replaceObj);
                } catch (InvocationTargetException e) {
                    if (log.isErrorEnabled()) {
                        log.error("Error", e);
                    }
                }

            }

            return param;
        }

        return param;
    }

    /**
     * 将html标签转为字符串
     * @param param
     * @return
     * @throws IllegalAccessException
     */
    public static Object replaceHtmlString(Object param) throws IllegalAccessException {
        if (param != null) {

            if (JavaTypeUtils.isBasicType(param)) {

                if (param.getClass().equals(JavaBasicTypeEnum.STRING.getsClass())) {
                    return StringEscapeUtils.unescapeHtml4(param.toString());
                }

                return param;
            }

            if (JavaTypeUtils.isArray(param)) {
                Object[] objectArray = (Object[]) param;
                for (int i = 0; i < objectArray.length; i++) {
                    Object object = objectArray[i];
                    if (object == null) {
                        continue;
                    }
                    objectArray[i] = replaceStringHtml(object);
                }

                return objectArray;
            }

            if (JavaTypeUtils.isCollection(param)) {
                Collection collection = (Collection) param;

                Collection replaceCollection = new ArrayList();

                Iterator iterator = collection.iterator();
                while (iterator.hasNext()) {
                    Object nextObj = iterator.next();
                    if (nextObj == null) {
                        continue;
                    }
                    Object o = replaceStringHtml(nextObj);
                    iterator.remove();
                    replaceCollection.add(o);
                }

                collection.addAll(replaceCollection);

                return collection;
            }

            if (JavaTypeUtils.isMap(param)) {
                Map map = (Map) param;
                Set set = map.keySet();
                for (Object obj : set) {
                    Object mapValue = map.get(obj);
                    if (mapValue == null) {
                        continue;
                    }
                    Object o = replaceStringHtml(mapValue);
                    map.put(obj, o);
                }

                return map;
            }

            Field[] declaredFields = param.getClass().getDeclaredFields();
            for (Field field : declaredFields) {

                field.setAccessible(true);
                int modifiers = field.getModifiers();
                if (modifiers >= 24) {
                    continue;
                }

                Object o = field.get(param);

                if (o == null) {
                    continue;
                }

                Object replaceObj = replaceStringHtml(o);

                try {
                    BeanUtils.setProperty(param, field.getName(), replaceObj);
                } catch (InvocationTargetException e) {
                    if (log.isErrorEnabled()) {
                        log.error("Error", e);
                    }
                }

            }

            return param;
        }

        return param;
    }
}
