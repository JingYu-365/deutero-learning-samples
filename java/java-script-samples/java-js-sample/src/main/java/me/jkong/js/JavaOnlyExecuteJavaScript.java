package me.jkong.js;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @author JKong
 * @version v1.0
 * @description 使用Java操作JavaScript语言脚本
 * @date 2020/2/4 3:03 下午.
 */
public class JavaOnlyExecuteJavaScript {
    
    public static void main(String[] args) {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("javascript");
        try {
            
            //执行js代码
            se.eval("var a=3; var b=4; print(a+b)");
            
            // 不能调用浏览器中定义的js函数 // 错误，会抛出alert引用不存在的异常
//            se.eval("alert(\"js alert\");");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("execute completed!");
    }
}