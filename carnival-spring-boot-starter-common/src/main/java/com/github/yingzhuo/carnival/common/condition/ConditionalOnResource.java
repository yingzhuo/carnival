/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.condition;

import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @see org.springframework.boot.autoconfigure.condition.ConditionalOnResource
 * @since 1.10.20
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Conditional(ConditionalOnResourceCondition.class)
public @interface ConditionalOnResource {

    /**
     * locations
     */
    @AliasFor("locations")
    public String[] value() default {};

    /**
     * locations
     */
    @AliasFor("value")
    public String[] locations() default {};

    /**
     * 存在性
     */
    public Existence existence() default Existence.ALL;

    /**
     * 类型
     */
    public Type type() default Type.ANY;

    public enum Existence {
        ALL, NONE, ANY
    }

    public enum Type {
        FILE, DIRECTORY, ANY
    }

}
