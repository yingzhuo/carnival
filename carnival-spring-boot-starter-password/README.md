# 说明

密码先加密再持久化是常见的操作。本插件用来完成此功能。

#### 依赖

```xml
<dependency>
  <groupId>com.github.yingzhuo</groupId>
  <artifactId>carnival-spring-boot-starter-password</artifactId>
  <version>${carnival.version}</version>
</dependency>
```

#### 核心接口

`com.github.yingzhuo.carnival.password.PasswordEncrypter`

```java
public interface PasswordEncrypter {

    public static final String EMPTY_SALT = "";

    public String encrypt(String rawPassword, String leftSalt, String rightSalt);

    public String encrypt(String rawPassword);

    public String encrypt(String rawPassword, String rightSalt);

    public boolean matches(String rawPassword, String encryptedPassword);

    public boolean matches(String rawPassword, String leftSalt, String rightSalt, String encryptedPassword);

    public boolean matches(String rawPassword, String rightSalt, String encryptedPassword);

}
```

#### 简单配置

支持以下算法:

* md5
* sha1
* sha256
* bcrypt
* none

```yaml
carnival:
    password:
      algorithm: <算法>
      repeat: 1
```

#### 其他工具

* `RepeatPasswordEncrypter`: 这是一个装饰器，如果需要多次加密请使用。
