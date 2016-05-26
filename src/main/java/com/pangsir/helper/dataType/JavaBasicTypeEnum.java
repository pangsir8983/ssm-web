package com.pangsir.helper.dataType;

/**
 * 类说明: 获取Java的数据的基本类型包装类
 *
 * @Author: 胖先生
 * @Create: 2016-05-25 19:39
 * @Home: http://www.cnblogs.com/pangxiansheng/
 */

public enum  JavaBasicTypeEnum {
    STRING(String.class),
    INTEGER(Integer.class),
    BOOLEAN(Boolean.class),
    BYTE(Byte.class),
    LONG(Long.class),
    SHORT(Short.class),
    FLOAT(Float.class),
    DOUBLE(Double.class),
    CHAR(Character.class);

    private Class sClass;
    JavaBasicTypeEnum(Class sClass) {
        this.sClass = sClass;
    }

    protected static boolean isBasicType(Class sClass) {
        for (JavaBasicTypeEnum en : JavaBasicTypeEnum.values()) {
            Class aClass = en.getsClass();
            if (aClass.equals(sClass)) {
                return true;
            }
        }
        return false;
    }

    public Class getsClass() {
        return sClass;
    }

    public void setsClass(Class sClass) {
        this.sClass = sClass;
    }
}
