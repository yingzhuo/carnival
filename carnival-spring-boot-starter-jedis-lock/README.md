# 说明

分布式锁是非常常见的工具。本插件借助Redis完成分布式锁的功能。

#### 依赖

```xml
<dependency>
    <groupId>com.github.yingzhuo</groupId>
    <artifactId>carnival-spring-boot-starter-jedis</artifactId>
    <version>${carnival.version}</version>
</dependency>
<dependency>
    <groupId>com.github.yingzhuo</groupId>
    <artifactId>carnival-spring-boot-starter-jedis-lock</artifactId>
    <version>${carnival.version}</version>
</dependency>
```

#### 配置

本插件无需配置非常简单。但是由于分布式锁依赖于[Jedis](https://github.com/xetorthio/jedis)实现。使用时务必配置好[carnival-spring-boot-starter-jedis](https://github.com/yingzhuo/carnival/tree/master/carnival-spring-boot-starter-jedis-lock)。

```yaml
carnival:
  distributed-lock:
    enabled: true
    time-to-live: 5s  # 分布式锁最大锁定时间，超过时锁将自动打开。
    redis-key:
      prefix: "my-prefix-"
      suffix: ""
```

#### 使用

```java
import com.github.yingzhuo.carnival.jedis.lock.DistributedLock;

try {
    if (DistributedLock.lock("key")) {
        // 执行实际需要的业务
    } else {
        throw new UnsupportedOperationException("系统正忙");
    }
} finally {
    DistributedLock.release("key");
}
```

#### 注意事项

* 如果同时使用[carnival-spring-boot-starter-restful-norepeated](https://github.com/yingzhuo/carnival/tree/master/carnival-spring-boot-starter-restful-norepeated)和本插件，那么两者会共用同一个Jedis资源池。
