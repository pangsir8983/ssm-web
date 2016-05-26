package com.pangsir.helper.script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 类说明: 通过Java执行JS脚本
 *
 * @Author: 胖先生
 * @Create: 2016-05-25 23:30
 * @Home: http://www.cnblogs.com/pangxiansheng/
 */

public class ScriptHelper {
    /**

     *

     * <p>执行JS函数</p>

     * @param fileName

     * @param params

     * @return

     * @throws Exception

     */
    public static Object execute(String fileName, Object... params) throws Exception{
        return execute(new File(fileName), params);
    }

    public static Object execute(File file, Object... params) throws Exception{
        Object result = null;
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            FileReader reader = new FileReader(file);   // 载入指定脚本

            engine.eval(reader);
            if(engine instanceof Invocable) {
                Invocable invoke = (Invocable)engine;    // 调用merge方法，并传入两个参数

                result = invoke.invokeFunction("html2bbcode", params);
            }
            reader.close();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return result;
    }

    /**

     *

     * <p>执行JS函数</p>

     * @param express

     * @param params

     * @return

     * @throws ScriptException

     */
    public static Object execute(String express, Map<String, Object> params) throws ScriptException{
        Object result = null;
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            if(params == null){
                params = new HashMap<String,Object>();
            }
            Iterator<Map.Entry<String,Object>> iter = params.entrySet().iterator();
            Map.Entry<String,Object> entry = null;
            while(iter.hasNext()){
                entry = iter.next();
                engine.put(entry.getKey(), entry.getValue());
            }
            result = engine.eval(express);
        } catch (ScriptException e) {
            throw new ScriptException(e);
        }
        return result;
    }

    /**

     *

     * <p>执行字符串计算</p>

     * @param express

     * @param params

     * @return

     * @throws ScriptException

     */
    public static Double calculate(String express, Map<String, Double> params) throws ScriptException {
        Double result = null;
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            if(params == null){
                params = new HashMap<String,Double>();
            }
            Iterator<Map.Entry<String,Double>> iter = params.entrySet().iterator();
            Map.Entry<String,Double> entry = null;
            while(iter.hasNext()){
                entry = iter.next();
                engine.put(entry.getKey(), entry.getValue());
            }
            result = (Double)engine.eval(express);
        } catch (ScriptException e) {
            throw new ScriptException(e);
        }
        return result;
    }

}
