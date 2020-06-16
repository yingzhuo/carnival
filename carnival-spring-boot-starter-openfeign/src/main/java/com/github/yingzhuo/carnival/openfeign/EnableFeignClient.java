/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign;

import com.github.yingzhuo.carnival.openfeign.autoconfig.FeignClientRegistrar;
import com.github.yingzhuo.carnival.openfeign.autoconfig.FeignCoreAutoConfig;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 应卓
 * @since 1.6.17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({FeignClientRegistrar.class, FeignCoreAutoConfig.class})
public @interface EnableFeignClient {

    @AliasFor("basePackages")
    public String[] value() default {};

    @AliasFor("value")
    public String[] basePackages() default {};

    public Class<?>[] basePackageClasses() default {};

}
