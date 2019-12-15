/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.meter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @since 1.3.5
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MeterLabelBeanDefinition.class)
public @interface MeterLabel {

    public String project() default "";

    public String application() default "";

    public String layer() default "";

    public String version() default "";

    public String primaryProfile() default "";

}
