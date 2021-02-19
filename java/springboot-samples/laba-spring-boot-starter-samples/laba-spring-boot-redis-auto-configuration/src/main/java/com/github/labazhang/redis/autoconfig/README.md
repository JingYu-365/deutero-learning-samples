实现流程：
- 通过代码方式配置 RedisTemplate
- 通过配置文件 properties 配置 Redis 连接信息
- 通过 配置 META-INF/spring.factories 扩展自动配置
---
注意点：
- 依赖
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <!-- 支持 自动配置处理 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-configuration-processor</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```
- 自动配置发现
```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  com.github.labazhang.redis.autoconfig.LabaRedisAutoConfiguration
```