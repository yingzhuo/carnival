# 说明

本插件用来生成[Gravatar](http://cn.gravatar.com/)头像的URL。

```xml
<dependency>
    <groupId>com.github.yingzhuo</groupId>
    <artifactId>carnival-spring-boot-starter-gravatar</artifactId>
    <version>${current.version}</version>
</dependency>
```

你需要配置本插件。

```yaml
carnival:
  gravatar:
    enabled: true
    type: china
    default-image: monster
    rating: restricted
```

在需要生成头像URL的地方，注入`GravatarFactory`对象即可使用。如：

```java
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private GravatarFactory gravatarFactory;

    @Override
    public User save(User user) {
        user.setAvatar(gravatarFactory.create(email, size));
        return null;
    }
    
}
```