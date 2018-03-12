## carnival-spring-boot-starter-password

口令加密工具，在应用中唱唱需要对终端用户提供的口令加密并持久化，本插件实现了一些常用口令加密算法。可将`PasswordEncrypter`的实现注入到任何需要的地方。

```
carnival.password.enabled=true   #本插件在classpath时默认为启用
carnival.password.algorithm=md5  #口令加密算法
```

可选择的加密算法如下:

* md2
* md5 (默认)
* sha1
* sha256
* bcrypt
