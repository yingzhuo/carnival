## 说明

`carnival-spring-boot-starter-id`用来生成ID。支持以下算法

* Random UUID
* Snowflake

#### 依赖

```xml
<dependency>
  <groupId>com.github.yingzhuo</groupId>
  <artifactId>carnival-spring-boot-starter-id</artifactId>
  <version>${carnival.version}</version>
</dependency>
```

#### 如何配置

(1). Snowflake

```yaml
carnival:
  id:
    enabled: true
    algorithm: snowflake
    snowflake:
      data-center-id: 0
      worker-id: 0
```

(2). UUID

```yaml
carnival:
  id:
    enabled: true
    algorithm: uuid_36
```

#### 在代码中使用

```java
import com.github.yingzhuo.carnival.id.util.IdGeneratorUtils;

String id = IdGeneratorUtils.nextId();
```
