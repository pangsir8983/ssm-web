package com.pangsir.helper.reflect;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘文铭
 * @描述
 * 	Java反射工具类
 *
 */
public class ReflectHelper {
	private ReflectHelper(){}
    
    /**
     * 将 javaBean 转化为 Map
     * @param obj
     * @return key为obj的属性名称，value为obj属性值的Map
     */
    public static Map<String, Object> convertJavaBeanToMap(Object obj) {
            return convertJavaBeanToMap(obj, null);
    }
    
    /**
     * 将JavaBean对象转为Map对象
     * @param obj
     * @param notNeedProps 不需要的属性的名称
     * @return key为obj的属性名称，value为obj属性值的Map(不包含 notNeedProp 中的属性)
     */
    public static Map<String, Object> convertJavaBeanToMap(Object obj, String[] notNeedProps){
            Map<String, Object> map = new LinkedHashMap<String, Object>();
            if(obj == null)
                    return map;
            
            List<String> notNeedPropList = (notNeedProps == null || notNeedProps.length == 0) ? null : Arrays.asList(notNeedProps);
            Method[] methods = obj.getClass().getMethods(); ////取出bean里的所有方法
            Object[] emptyObjs = new Object[0];
            for (int i = 0; i < methods.length; i++) {
                    String methodName = methods[i].getName(); //方法名
                    String returnTypeName = methods[i].getReturnType().getName(); //返回类型的名称
                    
                    //没有返回类型
                    if("void".equals(returnTypeName)){
                            continue;
                    }
                    //参数个数不为0
                    if (methods[i].getParameterTypes().length != 0){
                            continue;
                    }
                    
                    String prop = null;
                    if(methodName.startsWith("get") && !"getClass".equals(methodName)){
                            prop = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
                    }else if(methodName.startsWith("is") && ("boolean".equals(returnTypeName) || "java.lang.Boolean".equals(returnTypeName))){
                            prop = methodName;
                    }else{
                            continue;
                    }
                    
                    //不需要此属性
                    if(notNeedPropList != null && notNeedPropList.contains(prop)){
                            continue;
                    }
                    
                    try {
                            Object value =methods[i].invoke(obj, emptyObjs);
                            map.put(prop, value);
                    } catch (Exception e) {
                            e.printStackTrace();
                    }
            }
            return map;
    }

    /**
     * 通过反射机制给 bean 设置属性值(调用 setX() 方法设置)， 对于 value 的元素不会进行设置
     * @param bean JavaBean对象
     * @param map 属性名称(key)和属性值(value)的Map集合
     * @throws InvocationTargetException
     * @throws IllegalAccessException 
     * @throws NoSuchMethodException 
     * @throws IllegalArgumentException 
     * @throws SecurityException 
     */
    public static void convertMapToJavaBean(Object bean, Map<String, Object> map) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
            for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
                    String propName = iter.next();
                    Object propValue = map.get(propName);
                    if(!(propName == null || propValue == null)){
                            setPropertyForBean(bean, propName, propValue);
                    }
            }
    }
    
    /**
     * 通过反射机制给 bean 设置属性值(调用 setX() 方法设置), 对于propValues中为null的元素不会进行设置
     * @param bean JavaBean对象
     * @param propNames 属性名称
     * @param propValues 属性值(必须和属性名称一一对应)
     * @throws InvocationTargetException
     * @throws IllegalAccessException 
     * @throws NoSuchMethodException 
     * @throws IllegalArgumentException 
     * @throws SecurityException 
     */
    public static void setPropertysForBean(Object bean, String[] propNames, Object[] propValues) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
            for (int i = 0; i < propNames.length; i++) {
                    String propName = propNames[i];
                    Object propValue = propValues[i];
                    if(!(propName == null || propValue == null)){
                            setPropertyForBean(bean, propName, propValue);
                    }
            }
    }
    
    /**
     * 通过反射机制给 bean 设置属性值, 调用 setX() 方法
     * @param bean JavaBean对象
     * @param propName 属性名称
     * @param propValue 属性值
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public static void setPropertyForBean(Object bean, String propName, Object propValue) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
            String methodName = "set" + Character.toUpperCase(propName.charAt(0))+propName.substring(1);
            invokeMethod(bean, methodName, propValue);
     }
    
    /**
     * 通过反射调用指定对象的对应方法
     * @param owner 调用的方法所属类的实例对象
     * @param methodName 方法名称
     * @param params  参数数组
     * @return 调用的方法返回的对象, 如果没有找着对应的方法，则返回null
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public static Object invokeMethod(Object owner, String methodName, Object... params) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
            Class<?> ownerClass = owner.getClass();
            Class<?>[] paramClasses = new Class[params.length];
            for (int i = 0, j = params.length; i < j; i++) {
                    paramClasses[i] = params[i].getClass();
            }
            Method method = ownerClass.getMethod(methodName, paramClasses);
            return method == null ? null : method.invoke(owner, params);
    }
    
    /**
     * 将 Javabean 中 String 类型的属性值进行转码
     * @param bean javaBean对象
     * @param oldCharset String类型属性值的原编码
     * @param newCharset String类型属性值的新编码
     * @throws UnsupportedEncodingException 如果传入的 oldCharset 或 newCharset 不是被支持的编码
     */
    public static void convertStringEncoding(Object bean, String oldCharset, String newCharset) throws UnsupportedEncodingException{
            Class<?> beanClass = bean.getClass();
            Method[] methods = beanClass.getMethods();      ////取出bean里的所有方法
            Method getMethod = null;
            for (int i = 0; i < methods.length; i++) {
                    String methodName = methods[i].getName(); //方法名
                    Class<?>[] parameterTypes = methods[i].getParameterTypes(); //参数类型
                    //set 方法，且参数个数为1，参数类型为 String
                    if(methodName.startsWith("set") && parameterTypes.length == 1 && "java.lang.String".equals(parameterTypes[0].getName())){
                            try {
                                    getMethod = beanClass.getMethod("get"+methodName.substring(3), new Class[]{});  //get方法
                                    String str = (String)getMethod.invoke(bean, new Object[]{});    //get方法返回的字符串
                                    if(str != null && !"".equals(str.trim()))
                                            methods[i].invoke(bean, new String(str.getBytes(oldCharset), newCharset));
                            } catch (NoSuchMethodException  e) {
                                    e.printStackTrace();
                            } catch (IllegalArgumentException e) {
                                    e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                            }
                    }
            }
    }
}
