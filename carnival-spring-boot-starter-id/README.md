# 说明

本插件专门用来生成ID。

```xml
<dependency>
    <groupId>com.github.yingzhuo</groupId>
    <artifactId>carnival-spring-boot-starter-xml</artifactId>
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
    enabled: true
    algorithm: snowflake_long
```

### 雪花算法 (String)

```yaml
carnival:
  id:
    enabled: true
    algorithm: snowflake_long
```

在需要生成ID的地方，注入`IdGenerator<?>`对象即可使用。如：

```java
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private IdGenerator<Long> idGenerator;

    @Override
    public User save(User user) {
        user.setId(idGenerator.generateId());
        return null;
    }
    
}
```

编程时应当注意`IdGenerator`的泛型类型。
