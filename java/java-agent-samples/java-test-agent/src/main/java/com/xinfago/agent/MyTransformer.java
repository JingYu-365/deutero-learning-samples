package com.xinfago.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检测方法的执行时间
 */
public class MyTransformer implements ClassFileTransformer {

    final static String prefix = "\nlong startTime = System.currentTimeMillis();\n";
    final static String postfix = "\nlong endTime = System.currentTimeMillis();\n";

    // 被处理的方法列表
    final static Map<String, List<String>> methodMap = new HashMap<String, List<String>>();

    /**
     * 设置需要重写的方法
     */
    public MyTransformer() {
        add("com.xinfago.agent.demo.Demo.sayHello");
        add("com.xinfago.agent.demo.Demo.sayHello2");
    }

    private void add(String methodString) {
        String className = methodString.substring(0, methodString.lastIndexOf("."));
        String methodName = methodString.substring(methodString.lastIndexOf(".") + 1);
        List<String> list = methodMap.get(className);
        if (list == null) {
            list = new ArrayList<String>();
            methodMap.put(className, list);
        }
        list.add(methodName);
    }

    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classFileBuffer) throws IllegalClassFormatException {
        className = className.replace("/", ".");
        // 判断加载的class的包路径是不是需要监控的类
        if (methodMap.containsKey(className)) {
            CtClass ctclass = null;
            try {
                // 使用全称,用于取得字节码类<使用javassist>
                ClassPool classPool = ClassPool.getDefault();
                ctclass = classPool.get(className);
                for (String methodName : methodMap.get(className)) {
                    String outputStr = "\nSystem.out.println(\"this method " + methodName
                            + " cost:\" +(endTime - startTime) +\"ms.\");";

                    // 得到这方法实例
                    CtMethod ctmethod = ctclass.getDeclaredMethod(methodName);
                    // 新定义一个方法叫做比如sayHello$old
                    String newMethodName = methodName + "$old";
                    // 将原来的方法名字修改
                    ctmethod.setName(newMethodName);

                    // 创建新的方法，复制原来的方法，名字为原来的名字
                    CtMethod newMethod = CtNewMethod.copy(ctmethod, methodName, ctclass, null);

                    // 构建新的方法体
                    StringBuilder bodyStr = new StringBuilder();
                    bodyStr.append("{");
                    bodyStr.append(prefix);
                    // 调用原有代码，类似于method();($$)表示所有的参数
                    bodyStr.append(newMethodName).append("($$);\n");
                    bodyStr.append(postfix);
                    bodyStr.append(outputStr);
                    bodyStr.append("}");

                    // 替换新方法
                    newMethod.setBody(bodyStr.toString());
                    // 增加新方法
                    ctclass.addMethod(newMethod);
                }
                return ctclass.toBytecode();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }
}