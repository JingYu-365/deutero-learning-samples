package me.jkong.js;

import javax.script.*;

/**
 * @author JKong
 * @version v1.0
 * @description 使用Java调用JavaScript脚本，并绑定参数。
 * @date 2020/2/4 3:36 下午.
 */
public class JavaExecuteJavaScriptWithParams {
    
    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        engine.put("a", 4);
        engine.put("b", 3);
//        Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
        try {
            // 1. 直接获取参数执行结果（只能为Double，使用Float和Integer会抛出异常）
            String result = String.valueOf(engine.eval("a+b"));
            System.out.println("result = " + result);
            
            // 2. 定义表达式执行，并获取结果
            engine.eval("c=a+b");
            Double c = (Double) engine.get("c");
            System.out.println("c = " + c);
            
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        System.out.println("execute completed!");
    }
    
}