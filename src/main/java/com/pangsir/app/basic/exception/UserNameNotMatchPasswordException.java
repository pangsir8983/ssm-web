package com.pangsir.app.basic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 类说明: 账号或者密码不匹配异常信息
 *
 * @Author: 胖先生
 * @Create: 2016-05-30 15:39
 * @Home: http://www.cnblogs.com/pangxiansheng/
 */
@ResponseStatus(reason="用户名与密码不匹配",value= HttpStatus.FORBIDDEN)
public class UserNameNotMatchPasswordException extends  RuntimeException {
    private static final long serialVersionUID = 1L;
    public UserNameNotMatchPasswordException(String message) {
        super(message);
    }

}
