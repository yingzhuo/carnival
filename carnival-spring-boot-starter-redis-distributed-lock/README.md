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

```java
final String lockKey = "abc";
final boolean locked  = DistributedLock.lock(lockKey);

if (!locked) {
    throw new CannotGetLockException();
}

try {
    // 这里是临界区代码
} finally {
    DistributedLock.release(lockKey);
}
```

本插件还实现了AOP元注释

```java
@Service
public class TestServiceImpl implements TestService {

    @Override
    @DistributedLock
    public void test() {
        System.out.println("test !");
    }

}
```

在进入方法时会尝试获取锁，在获取锁失败时，将抛出`CannotGetLockException`的实例。
