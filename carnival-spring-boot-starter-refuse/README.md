## carnival-spring-boot-starter-refuse

有些时候，需要零时性的关闭一些HTTP请求，使之失效。这时候这个插件就能派上用场。

```java
@org.springframework.context.annotation.Configuration
@org.springframework.context.annotation.ImportResource(locations = {"classpath:/spring-refusing.xml"})
public class ApplicationConfigRefusing {
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:lang="http://www.springframework.org/schema/lang"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/lang http://www.springframework.org/schema/lang/spring-lang.xsd">

    <lang:defaults refresh-check-delay="100000"/>

    <lang:groovy id="refuseConfigLoader" script-source="classpath:/RefuseConfigLoaderImpl.groovy"/>

</beans>
```

```groovy
import com.github.yingzhuo.carnival.refuse.RefuseConfig
import com.github.yingzhuo.carnival.refuse.RefuseConfigLoader

class RefuseConfigLoaderImpl implements RefuseConfigLoader {

    @Override
    RefuseConfig load() {
        RefuseConfig config = new RefuseConfig("默认拒绝访问理由")
        config.addConfig("/echo", "本路径拒绝访问理由")
        return config
    }

}
```

**> 注意:** 建议使用`Groovy`动态配置。
