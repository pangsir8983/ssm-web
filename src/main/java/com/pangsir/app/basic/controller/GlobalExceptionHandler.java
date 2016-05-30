package com.pangsir.app.basic.controller;

import com.pangsir.app.basic.exception.AppException;
import com.pangsir.app.basic.exception.RuntimeException;
import org.slf4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * 类说明: SpringMVC的全局异常处理分析
 * <p>注意使用注解@ControllerAdvice作用域是全局Controller范围:</p>
 * 可应用到所有
 * (1) @RequestMapping类
 * (2) 方法上的@ExceptionHandler、@InitBinder、@ModelAttribute，在这里是@ExceptionHandler
 *
 * @Author: 胖先生
 * @Create: 2016-05-30 15:07
 * @Home: http://www.cnblogs.com/pangxiansheng/
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final static String ERROR_101;//代表方法没有找到

    private final static String ERROR_102;//数据校验失败

    private final static String RUNTIME_EXCEPTION_PAGE;//数据校验失败

    static {
        ERROR_101 = "erorr_hp_101";
        ERROR_102 = "erorr_data_102";

        RUNTIME_EXCEPTION_PAGE = "error_page";
    }

    /**
     * 找不到HTTP犯法支持异常
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public String httpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request, AppException e) throws AppException {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        request.setAttribute("errorMsg", "没有找到你的请求的方法," + request.getServletPath());
        return ERROR_101;
    }

    /**
     * 验证不成功的异常信息
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public String methodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e) throws Exception {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        BindingResult bindingResult = e.getBindingResult();

        FieldError firstError = bindingResult.getFieldErrors().get(0);

        request.setAttribute("errorMsg", firstError.getDefaultMessage());

        // result.setError(HohistarExceptionReason.BIZ_10074, firstError.getField(), firstError.getDefaultMessage());

        return ERROR_102;
    }




    @ExceptionHandler(value = RuntimeException.class)
    public String defaultExceptionHandler(HttpServletRequest request, RuntimeException ex) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null)
            throw ex;

        String throwClassName = ex.getStackTrace()[0].getClassName();
        Logger logger = getLogger(throwClassName);
        logger.error("GlobalExceptionHandler catch a Server Exception: ", ex);

        // Otherwise setup and send the user to a default error-view.

        request.setAttribute("errorMsg",ex.getMessage());


        return RUNTIME_EXCEPTION_PAGE;
    }


}
