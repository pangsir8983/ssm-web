package com.pangsir.helper.dataType;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * 类说明: 获取文件类型的工具类,需要配置使用commons包
 *
 * @Author: 胖先生
 * @Create: 2016-05-25 18:10
 * @Home: http://www.cnblogs.com/pangxiansheng/
 */

public class ContentTypeUtil {

    private static CompositeConfiguration config = null;

    public static String getContentType(String fileName){

        int lastIndexOf = fileName.lastIndexOf(".");
        if(lastIndexOf<0){
            throw new RuntimeException("获取文件后缀失败，请以.分隔:"+fileName);
        }
        String key = fileName.substring(lastIndexOf);


        if(!key.startsWith(".")){            key="."+key;
        }
        key=key.toLowerCase();

        if(config==null) {
            config = new CompositeConfiguration();
            try {
                config.addConfiguration(new PropertiesConfiguration("contenttype.properties"));
            } catch (ConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        return config.getString(key, "application/octet-stream");
    }

    public static void main(String[] args) {
        System.out.println(getContentType("asdfas.PDF"));
    }
}
