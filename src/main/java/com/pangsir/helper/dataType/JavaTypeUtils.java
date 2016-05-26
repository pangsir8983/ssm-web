package com.pangsir.helper.dataType;

import java.util.*;

/**
 * 类说明: 判断Java的数据类型
 *
 * @Author: 胖先生
 * @Create: 2016-05-25 19:51
 * @Home: http://www.cnblogs.com/pangxiansheng/
 */

public class JavaTypeUtils {
    /**
     * 判断是否是基本类型
     * @param obj  输入对象
     * @return
     */
    public static boolean isBasicType(Object obj){
        boolean isBasicType = JavaBasicTypeEnum.isBasicType(obj.getClass());
        return isBasicType;
    }

    /**
     * 判断是否是数组
     * @param obj
     * @return
     */
    public static boolean isArray(Object obj){
        return obj.getClass().isArray();
    }

    /**
     * 判断是否是集合
     * @param obj
     * @return
     */
    public static boolean isCollection(Object obj){
        Set<Class> classSet = getAllExtendAndImplementsClass(obj.getClass());
        for(Class c:classSet){
            if(c.equals(Collection.class)){
                return true;
            }
        }
        return false;
    }

    private static Set<Class> getAllExtendAndImplementsClass(Class c){
        Set<Class> classSet=new HashSet<Class>();
        //获取接口
        Class[] interfaces = c.getInterfaces();
        if(interfaces!=null) {
            classSet.addAll(Arrays.asList(interfaces));
            for(Class in:interfaces){
                classSet.addAll(getAllExtendAndImplementsClass(in));
            }
        }
        //获取父类
        Class superclass = c.getSuperclass();
        if(superclass!=null) {
            classSet.add(superclass);
            classSet.addAll(getAllExtendAndImplementsClass(superclass));
        }
        return classSet;
    }

    /**
     * 判断是否是list
     * @param obj
     * @return
     */
    public static boolean isList(Object obj){
        Set<Class> classSet = getAllExtendAndImplementsClass(obj.getClass());
        for(Class c:classSet){
            if(c.equals(List.class)){
                return true;
            }
        }
        return false;
    }
    /**
     * 判断是否是map
     * @param obj
     * @return
     */
    public static boolean isMap(Object obj){
        Set<Class> classSet = getAllExtendAndImplementsClass(obj.getClass());
        for(Class c:classSet){
            if(c.equals(Map.class)){
                return true;
            }
        }
        return false;
    }
}
