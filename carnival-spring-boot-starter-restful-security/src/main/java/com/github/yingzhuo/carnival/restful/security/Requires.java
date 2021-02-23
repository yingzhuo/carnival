/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security;

import com.github.yingzhuo.carnival.restful.security.auth.RequiresAuthComponent;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @see RequiresAuthComponent
 * @since 1.7.7
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@com.github.yingzhuo.carnival.restful.security.annotation.Requires(RequiresAuthComponent.class)
public @interface Requires {

    // SpEL
    public String value();

}
