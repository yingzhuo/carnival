## carnival-spring-boot-starter-id

ID生成器是应用中常用的工具。

```java
public interface IdGenerator<I> {

    public I nextId();

}

public interface StringIdGenerator extends IdGenerator<String> {
}
```
本插件实现了一些常用ID生成算法。可将`StringIdGenerator`的实现注入到任何需要的地方。

```
carnival.id.enabled=true       #本插件在classpath时默认为启用
carnival.id.algorithm=uuid32   #id生成算法
```

可选择的ID生成算法:

* uuid32 (默认)
* uuid36
* snowflake
