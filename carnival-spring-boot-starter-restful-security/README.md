# 说明

本插件是专注于`RESTful`风格服务的鉴权框架。本插件逻辑简单明了。每一次请求都将检查令牌；通过令牌查询用户实体。最后通过元注释的配置检查角色/权限。如果插件判定请求为非法，则抛出异常。异常由本插件用户来处理。

### 核心概念

* (1) `Token` - 令牌

令牌是指每次Http请求带给服务器的一小团数据，这一小团数据用来表示请求发出者的身份。

```java
package com.github.yingzhuo.carnival.restful.security.token;

import java.io.Serializable;

public interface Token extends Serializable {
}
```

很明显地，上面的代码是一个高度抽象的标记型接口。它还有两个子类型方便使用`UsernamePasswordToken`和`StringToken`。显然本插件的用户如果可以自行定义符合业务要求的`Token`实现。

* (2) `TokenParser` - 令牌解析器

令牌解析器是用来从`HttpServletRequest`中分析出`Token`的部件。

```java
package com.github.yingzhuo.carnival.restful.security.parser;

import com.github.yingzhuo.carnival.common.parser.Parser;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import lombok.val;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@FunctionalInterface
public interface TokenParser extends Parser<NativeWebRequest, Token> {

    public Optional<Token> parse(NativeWebRequest webRequest, Locale locale);

    public default TokenParser chain(TokenParser that) {
        return (webRequest, locale) -> {
            val thisOp = this.parse(webRequest, locale);

            if (thisOp.isPresent()) {
                return thisOp;
            } else {
                return Objects.requireNonNull(that).parse(webRequest, locale);
            }
        };
    }

}
```

本插件给出了若干种令牌解析器的实现。当然，本插件的用户可以自行定义复合要求的`TokenParser`实现。

* (3) `UserDetails` - 用户实体

这个类型是对登录用户的抽象，其中包含ID，用户名等常见的信息。并包含了一个`Map`型的扩展字段(payload)

```java
package com.github.yingzhuo.carnival.restful.security.userdetails;

import com.github.yingzhuo.carnival.restful.security.role.Permission;
import com.github.yingzhuo.carnival.restful.security.role.Role;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public interface UserDetails extends Serializable {

    public Object getId();

    public String getUsername();

    public String getPassword();

    public boolean isExpired();

    public boolean isLocked();
    
    public Date getDateOfBirth();

    public Collection<Role> getRoles();

    public Collection<Permission> getPermissions();

    public <U> U getNativeUser();

    public Map<String, Object> getPayload();
}
```

用户在需要的时候可以通过`UserDetails.Builder`来创建`UserDetails`实现。

* (4) `UserDetailsRealm` - 用户实体Dao

```java
package com.github.yingzhuo.carnival.restful.security.realm;

import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.val;

import java.util.Objects;
import java.util.Optional;

@FunctionalInterface
public interface UserDetailsRealm {

    public Optional<UserDetails> loadUserDetails(Token token);

    public default UserDetailsRealm chain(UserDetailsRealm that) {
        return token -> {
            val thisOp = this.loadUserDetails(token);

            if (thisOp.isPresent()) {
                return thisOp;
            } else {
                return Objects.requireNonNull(that).loadUserDetails(token);
            }
        };
    }
}
```

这个部件负责通过令牌信息获取用户实体。大部分应用用户数据都是RDB持久化的，本插接件用户应当自行实现之。

### TODO
