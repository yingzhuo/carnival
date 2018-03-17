## carnival-spring-boot-starter-debug

这是一个debug辅助插件。在SpringMVC程序中，可以对每个请求记录日志。

```properties
carnival.debug.enabled=true     #默认开启
carnival.debug.profile=debug    #生效的profile
carnival.debug.log-level=debug  #日志级别
carnival.debug.logger-name=xxx  #日志名称
```

> **注意：** 请在你的应用正确配置`logback/log4j`
