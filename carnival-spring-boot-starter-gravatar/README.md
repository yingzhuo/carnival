## 说明

`carnival-spring-boot-starter-gravator` 是通过[gravatar.com](https://cn.gravatar.com/)生成人物头像的小工具。

#### 依赖

```xml
<dependency>
  <groupId>com.github.yingzhuo</groupId>
  <artifactId>carnival-spring-boot-starter-gravatar</artifactId>
  <version>${carnival.version}</version>
</dependency>
```

#### 配置

```yaml
carnival:
  gravatar:
    enabled: true
    default-image: monster
    rating: general_audience
```

#### 在代码中使用

```java
String avatarUrl = GravatarUtils.create("yingzhor@gmail.com");
```


