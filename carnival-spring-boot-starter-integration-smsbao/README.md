## carnival-spring-boot-starter-integration-smsbao

[短信宝(smsbao)](http://www.smsbao.com/)是上海一家提供短信发送服务的公司。本插件用于集成该公司提供的服务。

```java
public interface SmsbaoManager {
    boolean send(String phoneNumber, String content);
}
```

配置

```properties
carnival.integration.smsbao.enabled=ture
carnival.integration.smsbao.mode=regular
carnival.integration.smsbao.username=username
carnival.integration.smsbao.password=password
```
