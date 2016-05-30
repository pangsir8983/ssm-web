package com.pangsir.app.basic.exception;

import java.util.Collection;
import java.util.Vector;

/**
 * 类说明: 扩展Exception增加属性和异常栈
 * --- 模拟宋立珍提供的部分代码
 *
 * @Author: 胖先生
 * @Create: 2016-05-30 14:09
 * @Home: http://www.cnblogs.com/pangxiansheng/
 */

public class AppException extends  java.lang.Exception {

    private Vector vectorErrorMsg;

    public AppException(String errorMsg){
        vectorErrorMsg = new Vector(2);
        vectorErrorMsg.add(errorMsg);
    }

    public AppException(String errorMsg,AppException ae){
        this(errorMsg);
        setStackTrace(ae.getStackTrace());
        vectorErrorMsg.addAll(getErrorList());
    }

    private Collection getErrorList(){
        return vectorErrorMsg;
    }

    public AppException(String errorMsg,Exception ex){
        this(errorMsg);
        setStackTrace(ex.getStackTrace());
        //获取错误的信息
        String message = ex.getMessage();
        /* 定制
        if(null!=message&&(message.length()==7||message.length()>7&&' '==message.charAt(7))){
            vectorErrorMsg.add(message);
        }*/
        if (null!=message&&message.trim().length()>0){
            vectorErrorMsg.add(message);
        }
    }
    @Override
    public String getMessage(){
        return (String)vectorErrorMsg.firstElement();
    }

    public String getAllMessage(){
        StringBuilder result = new StringBuilder();
        for(int i=0,length = vectorErrorMsg.size();i<length;i++){
            result.append(String.valueOf(vectorErrorMsg.get(i)));
            if(i!=length-1){
                result.append("\r\n");
            }
        }

        return result.toString();

    }

}
