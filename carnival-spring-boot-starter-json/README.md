## carnival-spring-boot-starter-json

本插件包含常见的`Jackson`自动配置。

* 使`Jackson`支持`JSR 310`
* 使`Jackson`支持`Java8 API`
* 使`Jackson`支持`Kotlin API`
* 使`Jackson`支持`Scala API`

**注意**: 如果您的项目使用`kotlin`语言编写，务必在classpath中加入以下依赖:

```xml
<dependency>
    <groupId>com.fasterxml.jackson.module</groupId>
    <artifactId>jackson-module-kotlin</artifactId>
    <version>${jackson.version}</version>
</dependency>
```

**注意**: 如果您的项目使用`Scala`语言编写，务必在classpath中加入以下依赖:

```xml
<dependency>
    <groupId>com.fasterxml.jackson.module</groupId>
    <artifactId>jackson-module-scala_2.12</artifactId>
    <version>${jackson.version}</version>
</dependency>
```

本插件在classpath中时，自动生效。无需配置。