# 说明

[Jedis](https://github.com/xetorthio/jedis)是Java界最出名的Redis客户端之一。本项目为了集成spring-boot及Jedis而存在。

#### 依赖

```xml
<dependency>
    <groupId>com.github.yingzhuo</groupId>
    <artifactId>carnival-spring-boot-starter-jedis</artifactId>
    <version>${carnival.version}</version>
</dependency>
```

#### 配置

##### 配置资源池

```yaml
carnival:
  jedis:
    enable: true
    pool:
      # 最大链接数
      max-total: 8
      # 最大空闲链接
      max-idle: 8
      # 是否打开JMS监控
      jmx-enabled: false
      # 当资源池用尽后，调用者是否要等待
      block-when-exhausted: true
      # 当资源池用尽时，调用者最大等待时间
      max-wait-millis: -1
      # 向资源池借用连接时是否做连接有效性检测
      test-on-borrow: true
      # 向资源池归还连接时是否做连接有效性检测
      test-on-return: true
      # 创建新的连接时是否做连接有效性检测
      test-on-create: false
      # 检查空闲连接有效性检测
      test-while-idle: false
      # 空闲资源检测周期
      time-between-eviction-runs-millis: 1800000
      # 资源池中资源最小空闲时间，达到此值后空闲资源将被移除
      min-evictable-idle-time-millis: 1800000
      # 做空闲资源检测时，每次的采样数
      num-tests-per-eviction-run: 3
```

以上均为各配置项均有默认配置。请酌情配置。

##### 单节点模式

```yaml
carnival:
  jedis:
    enable: true
    mode: single
    single:
      node: "<IP>:<PORT>"
      password: "<PASSWORD>"
```

##### 集群模式

```yaml
carnival:
  jedis:
    enable: true
    mode: cluster
    cluster:
      nodes:
        - "<IP>:<PORT>"
        - "<IP>:<PORT>"
        - "<IP>:<PORT>"
        - "<IP>:<PORT>"
        - "<IP>:<PORT>"
        - "<IP>:<PORT>"
      password: "<PASSWORD>"
```

#### 使用

```java
import com.github.yingzhuo.carnival.jedis.util.JedisUtils;
import lombok.val;

val commands = JedisUtils.getCommands();
try {
    commands.set("my-key", "my-value");
    commands.del("my-key");
} finally {
    JedisUtils.closeCommands(commands);	// 归还到资源池
}
```

**注意:** 使用过`commands`变量后应当将其归还给资源池。