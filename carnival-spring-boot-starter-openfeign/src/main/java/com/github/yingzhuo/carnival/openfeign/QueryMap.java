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

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Spring MVC equivalent of OpenFeign's {@link feign.QueryMap} parameter annotation.
 *
 * @author Aram Peres
 * @see feign.QueryMap
 */
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface QueryMap {

    /**
     * @return alias for {@link #encoded()}.
     * @see feign.QueryMap#encoded()
     */
    @AliasFor("encoded")
    boolean value() default false;

    /**
     * @return Specifies whether parameter names and values are already encoded.
     * @see feign.QueryMap#encoded()
     */
    @AliasFor("value")
    boolean encoded() default false;

}
