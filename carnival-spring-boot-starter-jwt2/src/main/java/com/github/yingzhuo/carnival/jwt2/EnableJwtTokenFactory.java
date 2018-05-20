package com.github.yingzhuo.carnival.jwt2;

import com.github.yingzhuo.carnival.jwt2.autoconfig.TokenFactoryConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(TokenFactoryConfiguration.class)
public @interface EnableJwtTokenFactory {
}
