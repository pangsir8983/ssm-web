package com.pangsir.app.basic.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 类说明: 所有的领域模型的父类
 *
 * @Author: 胖先生
 * @Create: 2016-05-12 12:31
 * @Home: http://www.cnblogs.com/pangxiansheng/
 */

public abstract class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
