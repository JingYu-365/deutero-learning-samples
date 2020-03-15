package me.jkong.js;

import lombok.SneakyThrows;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

/**
 * @author JKong
 * @version v1.0
 * @description 使用 Java 执行 JavaScript 函数
 * @date 2020/2/4 4:07 下午.
 */
public class JavaExecuteJavaScriptFunctionWithParams {

    public static void main(String[] args) {
        // 1. 创建引擎
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        // 2. 读取js文件
        String jsFileName = "./script/test.js";
        URL resource = JavaExecuteJavaScriptFunctionWithParams.class.getClassLoader().getResource(jsFileName);

        FileReader reader = null;
        try {
            // 3. 获取指定脚本 reader
            if (resource != null) {
                reader = new FileReader(resource.getPath());
            }

            // 4. 引擎加载脚本 reader
            engine.eval(reader);
            if (engine instanceof Invocable) {
                // 5. 调用add方法，并传入两个参数
                Invocable invoke = (Invocable) engine;
                Object param1 = new Integer(5);
                Object param2 = new Integer(3);
                Double c = (Double) invoke.invokeFunction("add", param1, param2);
                // c = merge(4, 3);
                System.out.println("c = " + c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("execute completed!");
    }
}