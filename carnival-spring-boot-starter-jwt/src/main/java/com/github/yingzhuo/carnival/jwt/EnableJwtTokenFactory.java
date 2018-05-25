package com.github.yingzhuo.carnival.jwt;

import com.github.yingzhuo.carnival.jwt.autoconfig.TokenFactoryConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(TokenFactoryConfiguration.class)
public @interface EnableJwtTokenFactory {
}
