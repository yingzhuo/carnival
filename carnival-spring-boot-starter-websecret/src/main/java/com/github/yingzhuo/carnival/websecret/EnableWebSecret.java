package com.github.yingzhuo.carnival.websecret;

import com.github.yingzhuo.carnival.websecret.autoconfig.WebSecretConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 应卓
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(WebSecretConfiguration.class)
public @interface EnableWebSecret {
}
