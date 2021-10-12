# java agent技术原理及简单实现

注：本文定义-在函数执行前后增加对应的逻辑的操作统称为MOCK

## 1、引子

在某天与QA同学进行沟通时，发现QA同学有针对某个方法调用时，有让该方法停止一段时间的需求，我对这部分的功能实现非常好奇，因此决定对原理进行一些深入的了解，力争找到一种使用者尽可能少的对原有代码进行修改的方式，以达到对应的MOCK要求。

整体的感知程度可以分为三个级别：

- 硬编码
- 增加配置
- 无需任何修改

## 2、思路

在对方法进行mock，暂停以及异常模拟，在不知道其原理的情况下，进行猜想，思考其具体的实现原理，整体来说，最简单的实现模型无外乎两种：

### 2.1 朴素思路

假设存在如下的函数

```java
public Object targetMethod(){ 
    System.out.println( "运行" ); 
}
```

若想要在函数执行后暂停一段时间、返回特定mock值或抛出特定异常，那么可以考虑修改对应的函数内容：

```java
public Object targetMethod(){
      //在此处加入Sleep return 或 throw逻辑
    System.out.println("运行");
}
```

或使用类似代理的方法把对应的函数进行代理：
```java
public Object proxy(){
    //执行Sleep return 或 throw逻辑
      return targetMethod();
}
public Object targetMethod(){
    System.out.println("运行");
}
```
### 2.2 略成熟思路

在朴素思路的基础上，我们可以看出，实现类似的暂停、mock和异常功能整体实现方案无外乎两种：

- 代理模式
- 深入修改内部函数

在这两种思路的基础上，我们从代理模式开始考虑（主要是代理使用的比较多，更熟悉）

#### 2.2.1 动态代理

说起代理，最常想到的两个词语就是静态代理和动态代理，二者却别不进行详述，对于静态代理模式由于需要大量硬编码，所以完全可以不用考虑。

针对动态代理来看，开始考虑最具代表性的CGLIB进行调研。

下面的代码为一个典型的使用CGLIB进行动态代理的样例（代理的函数为PersonService.setPerson）：
```java

public class CGlibDynamicProxy implements MethodInterceptor {
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("执行前...");
        Object object = methodProxy.invokeSuper(o, objects);
        System.out.println("执行后...");
        return object;
    }

    static class PersonService {
        public PersonService() {
            System.out.println("PersonService构造");
        }

        public void setPerson() {
            System.out.println("PersonService:setPerson");
        }
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PersonService.class);
        enhancer.setCallback(new CGlibDynamicProxy());
        PersonService proxy= (PersonService)  enhancer.create();
        proxy.setPerson();
    }
}

```
从上面代码可以看出，对于CGLIB的动态代理而言，需要在原有代码中进行硬编码，且需要在对象初始化的时候，使用特定的方式进行初始化。因此若使用CGLIB完成MOCK，需要对应代码的的感知程度最高，达到了硬编码的程度。

#### 2.2.2 AspectJ

由于使用代理方式无法在不对代码进行修改的情况下完成MOCK，因此我们抛弃代理方式，考虑使用修改方法内部代码的方式进行MOCK。

基于这种思路，将目光转向了AspectJ。

在使用AspectJ时，需要定义方法执行前的函数以及方法执行后的函数：

```java
@Aspect
public class AspectJFrame {
    private Object before() {
        System.out.println("before");
        return new Object();
    }

    private Object after() {
        System.out.println("after");
        return new Object();
    }

    @Around("aroundPoint()")
    public Object doMock(ProceedingJoinPoint joinPoint) {
        Object object=null;
        before();
        try {
            object = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        after();
        return object;
    }
}
```

并通过aop.xml指定对应的切点以及对应的环绕函数

```xml
<aspectj>
    <aspects>
        <aspect name="com.test.framework.AspectJFrame">
            <before method="" pointcut=""/>
        </aspect>
    </aspects>
</aspectj>
```

但是基于以上的实现方式，需要对原有项目进行一定侵入，主要包含两部分内容：

- 在META-INF路径下增加aop.xml
- 引入对应的切面定义的jar包

通过aspectj可以完成在硬编码的情况下实现MOCK，但是这种实现方式受限于Aspectj自身局限，MOCK的功能代码在编译期就已经添加到对应的函数中了，最晚可在运行时完成MOCK功能代码的添加。这种方式主要有两个缺点：

- 对于运行中的java进行无法在不重启的条件下执行新增MOCK
- MOCK功能代码嵌入到目标函数中，无法对MOCK功能代码进行卸载，可能带来稳定性风险

## 3、 java agent介绍

由于在上述提到的各种技术都难以很好的支持在对原有项目无任何修改下完成MOCK功能的需求，在查阅资料后，将目光放至了java agent技术。

### 3.1 什么是java agent？

java agent本质上可以理解为一个插件，该插件就是一个精心提供的jar包，这个jar包通过JVMTI（JVM Tool Interface）完成加载，最终借助JPLISAgent（Java Programming Language Instrumentation Services Agent）完成对目标代码的修改。

java agent技术的主要功能如下：

- 可以在加载java文件之前做拦截把字节码做修改
- 可以在运行期将已经加载的类的字节码做变更
- 还有其他的一些小众的功能
  - 获取所有已经被加载过的类
  - 获取所有已经被初始化过了的类
  - 获取某个对象的大小
  - 将某个jar加入到bootstrapclasspath里作为高优先级被bootstrapClassloader加载
  - 将某个jar加入到classpath里供AppClassloard去加载
  - 设置某些native方法的前缀，主要在查找native方法的时候做规则匹配

### 3.2 java Instrumentation API

通过java agent技术进行类的字节码修改最主要使用的就是Java Instrumentation API。下面将介绍如何使用Java Instrumentation API进行字节码修改。

#### 3.2.1 实现agent启动方法

Java Agent支持目标JVM启动时加载，也支持在目标JVM运行时加载，这两种不同的加载模式会使用不同的入口函数，如果需要在目标JVM启动的同时加载Agent，那么可以选择实现下面的方法：

```java
[1] public static void premain(String agentArgs, Instrumentation inst); 
[2] public static void premain(String agentArgs);
```

JVM将首先寻找[1]，如果没有发现[1]，再寻找[2]。如果希望在目标JVM运行时加载Agent，则需要实现下面的方法：

```java
[1] public static void agentmain(String agentArgs, Instrumentation inst); 
[2] public static void agentmain(String agentArgs);
```

这两组方法的第一个参数AgentArgs是随同 “–javaagent”一起传入的程序参数，如果这个字符串代表了多个参数，就需要自己解析这些参数。inst是Instrumentation类型的对象，是JVM自动传入的，我们可以拿这个参数进行类增强等操作。

#### 3.2.2 指定Main-Class

Agent需要打包成一个jar包，在ManiFest属性中指定“Premain-Class”或者“Agent-Class”,且需根据需求定义Can-Redefine-Classes和Can-Retransform-Classes：

```
Manifest-Version: 1.0
preMain-Class: com.test.AgentClass
Archiver-Version: Plexus Archiver
Agent-Class: com.test.AgentClass
Can-Redefine-Classes: true
Can-Retransform-Classes: true
Created-By: Apache Maven 3.3.9
Build-Jdk: 1.8.0_112
```

#### 3.2.3 agent加载

- 启动时加载
  - 启动参数增加-javaagent:[path]，其中path为对应的agent的jar包路径
- 运行中加载
  - 使用com.sun.tools.attach.VirtualMachine加载

```java
try {
  String jvmPid = "目标进行的pid";
  logger.info("Attaching to target JVM with PID: " + jvmPid);
  VirtualMachine jvm = VirtualMachine.attach(jvmPid);
  jvm.loadAgent(agentFilePath);//agentFilePath为agent的路径
  jvm.detach();
  logger.info("Attached to target JVM and loaded Java agent successfully");
} catch (Exception e) {
  throw new RuntimeException(e);
}
```

#### 3.2.4 Instrument

instrument是JVM提供的一个可以修改已加载类的类库，专门为Java语言编写的插桩服务提供支持。它需要依赖JVMTI的Attach API机制实现。在JDK 1.6以前，instrument只能在JVM刚启动开始加载类时生效，而在JDK 1.6之后，instrument支持了在运行时对类定义的修改。要使用instrument的类修改功能，我们需要实现它提供的ClassFileTransformer接口，定义一个类文件转换器。接口中的transform()方法会在类文件被加载时调用，而在transform方法里，我们可以利用上文中的ASM或Javassist对传入的字节码进行改写或替换，生成新的字节码数组后返回。

首先可以定义如下的类转换器：

```java
public class TestTransformer implements ClassFileTransformer {
      //目标类名称，  .分隔
      private String targetClassName;
    //目标类名称，  /分隔
    private String targetVMClassName;
    private String targetMethodName;
    

    public TestTransformer(String className,String methodName){
        this.targetVMClassName = new String(className).replaceAll("\\.","\\/");
        this.targetMethodName = methodName;
        this.targetClassName=className;
    }
    //类加载时会执行该函数，其中参数 classfileBuffer为类原始字节码，返回值为目标字节码，className为/分隔
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
          //判断类名是否为目标类名
        if(!className.equals(targetVMClassName)){
            return classfileBuffer;
        }
        try {
            ClassPool classPool = ClassPool.getDefault();
            CtClass cls = classPool.get(this.targetClassName);
            CtMethod ctMethod = cls.getDeclaredMethod(this.targetMethodName);
            ctMethod.insertBefore("{ System.out.println(\"start\"); }");
            ctMethod.insertAfter("{ System.out.println(\"end\"); }");
            return cls.toBytecode();
        } catch (Exception e) {

        }
        return classfileBuffer;
    }
}
```

类转换器定义完毕后，需要将定义好的类转换器添加到对应的instrmentation中，对于已经加载过的类使用retransformClasses对类进行重新加载：

```java
public class AgentDemo {

    private static String className = "hello.GreetingController";
    private static String methodName = "getDomain";

    public static void agentmain(String args, Instrumentation instrumentation) {

        try {
            List<Class> needRetransFormClasses = new LinkedList<>();
            Class[] loadedClass = instrumentation.getAllLoadedClasses();
            for (int i = 0; i < loadedClass.length; i++) {
                if (loadedClass[i].getName().equals(className)) {
                    needRetransFormClasses.add(loadedClass[i]);
                }
            }

            instrumentation.addTransformer(new TestTransformer(className, methodName));
            instrumentation.retransformClasses(needRetransFormClasses.toArray(new Class[0]));
        } catch (Exception e) {

        }
    }

    public static void premain(String args, Instrumentation instrumentation) {
        instrumentation.addTransformer(new TestTransformer(className, methodName));
    }


}
```

从上图的代码可以看出，主方法实现了两个，分别为agentmain和premain，其中

- premain
  - 用于在启动时，类加载前定义类的TransFormer，在类加载的时候更新对应的类的字节码
- agentmain
  - 用于在运行时进行类的字节码的修改，步骤整体分为两步
    - 注册类的TransFormer
    - 调用retransformClasses函数进行类的重加载

## 4、java agent原理简述

### 4.1 启动时修改

![img](https://img2018.cnblogs.com/i-beta/1888311/201912/1888311-20191230153004224-1935880593.png)

 

 

启动时修改主要是在jvm启动时，执行native函数的Agent_OnLoad方法，在方法执行时，执行如下步骤：

- 创建InstrumentationImpl对象
- 监听ClassFileLoadHook事件
- 调用InstrumentationImpl的loadClassAndCallPremain方法，在这个方法里会去调用javaagent里MANIFEST.MF里指定的Premain-Class类的premain方法

### 4.2 运行时修改

![img](https://img2018.cnblogs.com/i-beta/1888311/201912/1888311-20191230153343876-750843539.png)

 

 

运行时修改主要是通过jvm的attach机制来请求目标jvm加载对应的agent，执行native函数的Agent_OnAttach方法，在方法执行时，执行如下步骤：

- 创建InstrumentationImpl对象
- 监听ClassFileLoadHook事件
- 调用InstrumentationImpl的loadClassAndCallAgentmain方法，在这个方法里会去调用javaagent里MANIFEST.MF里指定的Agentmain-Class类的agentmain方法

### 4.3 ClassFileLoadHook和TransFormClassFile

在4.1和4.2节中，可以看出整体流程中有两个部分是具有共性的，分别为：

- ClassFileLoadHook
- TranFormClassFile

ClassFileLoadHook是一个jvmti事件，该事件是instrument agent的一个核心事件，主要是在读取字节码文件回调时调用，内部调用了TransFormClassFile函数。

TransFormClassFile的主要作用是调用java.lang.instrument.ClassFileTransformer的tranform方法，该方法由开发者实现，通过instrument的addTransformer方法进行注册。

通过以上描述可以看出在字节码文件加载的时候，会触发ClassFileLoadHook事件，该事件调用TransFormClassFile，通过经由instrument的addTransformer注册的方法完成整体的字节码修改。

对于已加载的类，需要调用retransformClass函数，然后经由redefineClasses函数，在读取已加载的字节码文件后，若该字节码文件对应的类关注了ClassFileLoadHook事件，则调用ClassFileLoadHook事件。后续流程与类加载时字节码替换一致。

### 4.4 何时进行运行时替换？

在类加载完毕后，对应的想要替换函数可能正在执行，那么何时进行类字节码的替换呢？

由于运行时类字节码替换依赖于redefineClasses，那么可以看一下该方法的定义：

```
jvmtiError
JvmtiEnv::RedefineClasses(jint class_count, const jvmtiClassDefinition* class_definitions) {
//TODO: add locking
  VM_RedefineClasses op(class_count, class_definitions, jvmti_class_load_kind_redefine);
  VMThread::execute(&op);
  return (op.check_error());
} /* end RedefineClasses */
```

其中整体的执行依赖于VMThread，VMThread是一个在虚拟机创建时生成的单例原生线程，这个线程能派生出其他线程。同时，这个线程的主要的作用是维护一个vm操作队列(VMOperationQueue)，用于处理其他线程提交的vm operation，比如执行GC等。

VmThread在执行一个vm操作时，先判断这个操作是否需要在safepoint下执行。若需要safepoint下执行且当前系统不在safepoint下，则调用SafepointSynchronize的方法驱使所有线程进入safepoint中，再执行vm操作。执行完后再唤醒所有线程。若此操作不需要在safepoint下，或者当前系统已经在safepoint下，则可以直接执行该操作了。所以，在safepoint的vm操作下，只有vm线程可以执行具体的逻辑，其他线程都要进入safepoint下并被挂起，直到完成此次操作。

因此，在执行字节码替换的时候需要在safepoint下执行，因此整体会触发stop-the-world。

## 5、参考文档

http://lovestblog.cn/blog/2015/09/14/javaagent/

https://tech.meituan.com/2019/09/05/java-bytecode-enhancement.html

https://tech.meituan.com/2019/11/07/java-dynamic-debugging-technology.html

http://www.throwable.club/2019/06/29/java-understand-instrument-first/