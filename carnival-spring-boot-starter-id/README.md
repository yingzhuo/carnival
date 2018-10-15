# 说明

本插件专门用来生成ID。

```xml
<dependency>
    <groupId>com.github.yingzhuo</groupId>
    <artifactId>carnival-spring-boot-starter-id</artifactId>
    <version>${current.version}</version>
</dependency>
```

在`application.yml`里，你需要配置这个插件。

### 32位UUID

```yaml
carnival:
  id:
    enabled: true
    algorithm: uuid_32
```

### 36位UUID

```yaml
carnival:
  id:
    enabled: true
    algorithm: uuid_36
```

### 雪花算法 (Long)

```yaml
carnival:
  id:
    enable: true
    algorithm: snowflake
    snowflake:
      worker-id: 0        # 取值范围 0-31
      data-center-id: 0   # 取值范围 0-31
```

在需要生成ID的地方，注入`IdGenerator<?>`对象即可使用。如：

```java
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private IdGenerator<Long> idGenerator;

    @Override
    public User save(User user) {
        user.setId(idGenerator.nextId());
        return null;
    }
    
}
```
