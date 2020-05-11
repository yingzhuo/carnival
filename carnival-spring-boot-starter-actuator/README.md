# 说明

本项目目前属于实验阶段。

#### 依赖

```xml
<dependency>
  <groupId>com.github.yingzhuo</groupId>
  <artifactId>carnival-spring-boot-starter-actuator</artifactId>
  <version>${carnival.version}</version>
</dependency>
```

#### (1) NoteEndpoint

如果存在以下md文件之一，则`NoteEndpoint`将自动被添加。

* classpath:note.md,
* classpath:NOTE.md,
* classpath:META-INF/note.md,
* classpath:META-INF/NOTE.md

访问`/acutator/note`即可看到由markdown文档生成的html页面。
