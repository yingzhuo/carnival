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

import com.github.yingzhuo.carnival.openfeign.autoconfig.FeignBeanAutoConfig;
import com.github.yingzhuo.carnival.openfeign.autoconfig.FeignClientAutoConfig;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @since 1.6.17
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({FeignClientAutoConfig.class, FeignBeanAutoConfig.class})
public @interface EnableFeignClients {

    @AliasFor("basePackages")
    public String[] value() default {};

    @AliasFor("value")
    public String[] basePackages() default {};

    public Class<?>[] basePackageClasses() default {};

}
