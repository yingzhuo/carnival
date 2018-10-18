# 说明

本插件借助`Redis`实现了分布式锁。

```xml
<dependency>
    <groupId>com.github.yingzhuo</groupId>
    <artifactId>carnival-spring-boot-starter-redis-distributed-lock</artifactId>
    <version>${current.version}</version>
</dependency>
```

当然，你需要在`application.yml`中配置`Redis`相关信息。

```yaml
carnival:
  redis-distributed-lock:
    enabled: true
    jedis:
      host: "localhost"
      port: 6379
      password: "my_password"
```

配置好之后，就可以使用分布式锁了。

例子1:

```java
final String lockKey = "abc";
final boolean locked  = DistributedLock.lock(lockKey, 3000L);

if (!locked) {
    throw new CannotGetLockException();
}

try {
    // 这里是临界区代码
} finally {
    DistributedLock.release(lockKey);
}
```

例子2:

```java
final String lockKey = "abc";
DistributedLock.waitAndRun(lockKey, 3000L, () -> {      // 不等到拿到分布式锁，本方法不会退出。
    // 这里是临界区代码
});
```
