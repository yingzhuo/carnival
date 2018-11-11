# carnival-spring-boot-starter-password

本插件专门用于加密密码

```xml
<dependency>
    <groupId>com.github.yingzhuo</groupId>
    <artifactId>carnival-spring-boot-starter-password</artifactId>
    <version>${current.version}</version>
</dependency>
```

在`application.yml`里，你需要配置这个插件。

```yaml
carnival:
  password:
    enabled: true
    algorithm: bcrypt
```

现支持的加密算法有:

* bcrypt
* md5
* md2
* sha1
* sha256
* base64

在需要处理password的地方，注入`PasswordEncrypter`对象即可使用。如：

```java
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private PasswordEncrypter passwordEncrypter;

    @Override
    public User save(User user) {
        String hashedPwd = passwordEncrypter.encrypt(user.getPassword);
        return null;
    }
    
}
```

编程时应当注意`IdGenerator`的泛型类型。
