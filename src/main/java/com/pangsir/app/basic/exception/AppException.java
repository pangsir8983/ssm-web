package com.pangsir.app.basic.exception;

/**
 * 类说明: 整个项目异常的基础实现
 *
 * @Author: 胖先生
 * @Create: 2016-05-12 11:35
 * @Home: http://www.cnblogs.com/pangxiansheng/
 */

public class AppException extends RuntimeException {

    public AppException() {
        super();
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
