package com.pangsir.app.basic.exception;

/**
 * 类说明: 整个项目异常的基础实现
 * <p>
 *    添加错误代码属性,一种简单的封装
 *    在原有的基础的方法上,增加了新的属性,这样更容易让我们对其进行判断和封装
 *
 * </p>
 *
 * @Author: 胖先生
 * @Create: 2016-05-12 11:35
 * @Update: 2016-05-28 17:30 重构了一下
 * @Home: http://www.cnblogs.com/pangxiansheng/
 */

public class RuntimeException extends java.lang.RuntimeException {
    /**
     * 默认错误代码
     */
    private final static String GENERIC = "ERROR";

    private String errorCode;


    /*public RuntimeException() {
        super();
    }*/

    public RuntimeException(String message) {
        //利用通用错误代码
        this(GENERIC, message, null);
    }

    public RuntimeException(Throwable cause, String errorCode) {
        this(errorCode, null, cause);
    }

    public RuntimeException(String message, Throwable cause) {
        //利用通用错误代码
        this(GENERIC, message, cause);
    }

    public RuntimeException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
