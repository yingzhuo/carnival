## carnival-spring-boot-starter-restful-security

在编写`RESTful`风格程序时，往往需要对请求进行认证与授权操作。本插件帮助你实现此功能。

#### 核心接口

```java
public interface Token extends Serializable {
}
```

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
final public class UsernamePasswordToken implements Token {
    private String username;
    private String password;
}
```

`Token`是通过HttpRequest传递的表征用户信息的数据块，本插件仅提供了一种实现：[UsernamePasswordToken](https://github.com/yingzhuo/carnival/blob/master/carnival-spring-boot-starter-restful-security/src/main/java/com/github/yingzhuo/carnival/restful/security/impl/UsernamePasswordToken.java)。如果你的应用有特殊需求，你可以自己实现`Token`

```java
public interface TokenParser {
    public Optional<Token> parse(NativeWebRequest webRequest);
}
```

`TokenParser`是用来从HttpRequest中解析出`Token`的工具，本插件提供了一种实现：[HttpBasicTokenParser](https://github.com/yingzhuo/carnival/blob/master/carnival-spring-boot-starter-restful-security/src/main/java/com/github/yingzhuo/carnival/restful/security/impl/HttpBasicTokenParser.java)，它能够解析[UsernamePasswordToken](https://github.com/yingzhuo/carnival/blob/master/carnival-spring-boot-starter-restful-security/src/main/java/com/github/yingzhuo/carnival/restful/security/impl/UsernamePasswordToken.java)。这也是默认配置，如果你不指定
`TokenParser`的任何实现的话。

```java
public interface UserDetails {

    public String getId();

    public String getUsername();

    public String getPassword();

    public boolean isExpired();

    public boolean isLocked();

    public Collection<Role> getRoles();

    public Collection<Permission> getPermissions();

    public <U> U getNativeUser(Class<U> userType);
}
```

`UserDetails`为用户账号的抽象，你应当实现这个接口。

```java
public interface UserDetailsRealm {
    public Optional<UserDetails> loadUserDetails(Token token);
}
```

`UserDetailsRealm`通过`Token`查找`UserDetails`，你的应用需要自己实现一个。

#### 如何使用?

```java
// 配置
@Configuration
@EnableRestfulSecurity
public class ConfigRestfulSecurity {

	@Bean
	public UserDetailsRealm userDetailsRealm() {
		return new MyUserDetailsRealm(); 
	}
}
```

```java

@RestController
public class MyController {

	@RequiresRoles					// 判断用户的角色
	@RequiresAuthentication			// 判断用户是否认证
	@GetMapping("/hello")
	public String hello() {
		return ....
	}
}

```
