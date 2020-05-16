# 说明

本插件用来解决表单重复提交的问题。无论是有状态的后端还是无状态的后端均可使用本插件。对于每个需要确保不能重复提交的表单来说，提交之前必须向服务器申请一个提交令牌。提交表单时，必须将令牌一并提交。

#### 依赖

```xml
<dependency>
    <groupId>com.github.yingzhuo</groupId>
    <artifactId>carnival-spring-boot-starter-jedis</artifactId>
    <version>${carnival.version}</version>
</dependency>
<dependency>
    <groupId>com.github.yingzhuo</groupId>
    <artifactId>carnival-spring-boot-starter-restful-norepeated</artifactId>
    <version>${carnival.version}</version>
</dependency>
```

#### 配置

本插件无需配置非常简单。但是由于依赖于[Jedis](https://github.com/xetorthio/jedis)实现。使用时务必配置好[carnival-spring-boot-starter-jedis](https://github.com/yingzhuo/carnival/tree/master/carnival-spring-boot-starter-jedis)。

```yaml
carnival:
  no-repeated:
    enable: true
    token:
      time-to-live: 5m
```

#### 使用

```java
import com.github.yingzhuo.carnival.restful.norepeated.NoRepeated;
import com.github.yingzhuo.carnival.restful.norepeated.NoRepeatedToken;

@RestController
public class MyController {

    /**
     * 申请令牌
     */
    @GetMapping("/test")
    public String test() {
        return NoRepeatedToken.create();
    }

    /**
     * 需要检查不可重复提交的接口
     */
    @NoRepeated     // 加一个元注释即可
    @GetMapping("/content")
    public String content() {
        return "content";
    }

}
```

默认情况下 令牌通过名为`_norepeated_token`的参数传递。 其实这是一个名为`NoRepeatedTokenParser`接口所决定的。如下:

```java
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@FunctionalInterface
public interface NoRepeatedTokenParser {

    public Optional<String> parse(NativeWebRequest request);

}
```

若有需要，可以自己实现这个接口并加入到`spring`的上下文中。

```java
import org.springframework.stereotype.Component;

@Component
public class MyParser implements NoRepeatedTokenParser {

    // ...
}
```

#### 注意事项

* 如果同时使用[carnival-spring-boot-starter-jedis-lock](https://github.com/yingzhuo/carnival/tree/master/carnival-spring-boot-starter-jedis-lock)和本插件，那么两者会共用同一个Jedis资源池。
